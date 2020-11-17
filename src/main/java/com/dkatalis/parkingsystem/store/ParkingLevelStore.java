package com.dkatalis.parkingsystem.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.dkatalis.parkingsystem.pojo.IParkingStrategy;
import com.dkatalis.parkingsystem.pojo.NearestFirstParkingStrategy;
import com.dkatalis.parkingsystem.pojo.Vehicle;
import com.dkatalis.parkingsystem.utils.Constants;

/**
 * This class is a singleton class to manage the data of parking system
 * 
 * @author Dinesh L
 * @param <T>
 */
public class ParkingLevelStore<T extends Vehicle> implements IParkingLevelStore<T> {
	// For Multilevel Parking lot - 0 -> Ground floor 1 -> First Floor etc
	private AtomicInteger level = new AtomicInteger(0);
	private AtomicInteger capacity = new AtomicInteger();
	private AtomicInteger availability = new AtomicInteger();
	// Allocation Strategy for parking
	private IParkingStrategy parkingStrategy;
	// this is per level - slot - vehicle
	private Map<Integer, Optional<T>> slotVehicleMap;

	@SuppressWarnings("rawtypes")
	private static ParkingLevelStore instance = null;

	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> ParkingLevelStore<T> getInstance(int level, int capacity,
			IParkingStrategy parkingStrategy) {
		if (instance == null) {
			synchronized (ParkingLevelStore.class) {
				if (instance == null) {
					instance = new ParkingLevelStore<T>(level, capacity, parkingStrategy);
				}
			}
		}
		return instance;
	}

	private ParkingLevelStore(int level, int capacity, IParkingStrategy parkingStrategy) {
		this.level.set(level);
		this.capacity.set(capacity);
		this.availability.set(capacity);
		this.parkingStrategy = parkingStrategy;
		if (parkingStrategy == null)
			parkingStrategy = new NearestFirstParkingStrategy();
		slotVehicleMap = new ConcurrentHashMap<>();
		for (int i = 1; i <= capacity; i++) {
			slotVehicleMap.put(i, Optional.empty());
			parkingStrategy.add(i);
		}
	}

	@Override
	public int parkCar(T vehicle) {
		int availableSlot;
		if (availability.get() == 0) {
			return Constants.NOT_AVAILABLE;
		} else {
			availableSlot = parkingStrategy.getSlot();
			if (slotVehicleMap.containsValue(Optional.of(vehicle)))
				return Constants.VEHICLE_ALREADY_EXIST;

			slotVehicleMap.put(availableSlot, Optional.of(vehicle));
			availability.decrementAndGet();
			parkingStrategy.removeSlot(availableSlot);
		}
		return availableSlot;
	}

	@Override
	public boolean leaveCar(int slotNumber) {
		if (!slotVehicleMap.get(slotNumber).isPresent()) // Slot already empty
			return false;
		availability.incrementAndGet();
		parkingStrategy.add(slotNumber);
		slotVehicleMap.put(slotNumber, Optional.empty());
		return true;
	}

	@Override
	public List<String> getStatus() {
		List<String> statusList = new ArrayList<>();
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if (vehicle.isPresent()) {
				statusList.add(i + "\t\t" + vehicle.get().getRegistrationNo() + "\t\t" + vehicle.get().getColor());
			}
		}
		return statusList;
	}

	public int getAvailableSlotsCount() {
		return availability.get();
	}

	@Override
	public int getSlotNoFromRegistrationNo(String registrationNo) {
		int result = Constants.NOT_FOUND;
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<T> vehicle = slotVehicleMap.get(i);
			if (vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getRegistrationNo())) {
				result = i;
				break;
			}
		}
		return result;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public void doCleanUp() {
		this.level = new AtomicInteger();
		this.capacity = new AtomicInteger();
		this.availability = new AtomicInteger();
		this.parkingStrategy = null;
		slotVehicleMap = null;
		instance = null;
	}
}
