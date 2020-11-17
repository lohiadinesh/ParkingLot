package com.dkatalis.parkingsystem.store;

import java.util.List;

import com.dkatalis.parkingsystem.pojo.Vehicle;

/**
 * @author Dinesh L
 *
 */
public interface IParkingStore<T extends Vehicle>
{
	public int parkCar(int level, T vehicle);
	
	public boolean leaveCar(int level, int slotNumber);
	
	public List<String> getStatus(int level);
	
	public int getSlotNoFromRegistrationNo(int level, String registrationNo);
	
	public int getAvailableSlotsCount(int level);
	
	public void doCleanup();
}
