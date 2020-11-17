/**
 * 
 */
package com.dkatalis.parkingsystem.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dkatalis.parkingsystem.pojo.IParkingStrategy;
import com.dkatalis.parkingsystem.pojo.NearestFirstParkingStrategy;
import com.dkatalis.parkingsystem.pojo.Vehicle;

/**
 * This class is a singleton class to manage the data of parking system
 * 
 * @author Dinesh L
 * @param <T>
 */
public class ParkingStore<T extends Vehicle> implements IParkingStore<T> {
	private Map<Integer, IParkingLevelStore<T>> levelParkingMap;

	@SuppressWarnings("rawtypes")
	private static ParkingStore instance = null;

	@SuppressWarnings("unchecked")
	public static <T extends Vehicle> ParkingStore<T> getInstance(List<Integer> parkingLevels,
			List<Integer> capacityList, List<IParkingStrategy> parkingStrategies) {
		// Make sure the each of the lists are of equal size
		if (instance == null) {
			synchronized (ParkingStore.class) {
				if (instance == null) {
					instance = new ParkingStore<T>(parkingLevels, capacityList, parkingStrategies);
				}
			}
		}
		return instance;
	}

	private ParkingStore(List<Integer> parkingLevels, List<Integer> capacityList,
			List<IParkingStrategy> parkingStrategies) {
		if (levelParkingMap == null)
			levelParkingMap = new HashMap<>();
		for (int i = 0; i < parkingLevels.size(); i++) {
			levelParkingMap.put(parkingLevels.get(i), ParkingLevelStore.getInstance(parkingLevels.get(i),
					capacityList.get(i), new NearestFirstParkingStrategy()));

		}
	}

	@Override
	public int parkCar(int level, T vehicle) {
		return levelParkingMap.get(level).parkCar(vehicle);
	}

	@Override
	public boolean leaveCar(int level, int slotNumber) {
		return levelParkingMap.get(level).leaveCar(slotNumber);
	}

	@Override
	public List<String> getStatus(int level) {
		return levelParkingMap.get(level).getStatus();
	}

	public int getAvailableSlotsCount(int level) {
		return levelParkingMap.get(level).getAvailableSlotsCount();
	}

	@Override
	public int getSlotNoFromRegistrationNo(int level, String registrationNo) {
		return levelParkingMap.get(level).getSlotNoFromRegistrationNo(registrationNo);
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void doCleanup() {
		for (IParkingLevelStore<T> levelDataManager : levelParkingMap.values()) {
			levelDataManager.doCleanUp();
		}
		levelParkingMap = null;
		instance = null;
	}
}
