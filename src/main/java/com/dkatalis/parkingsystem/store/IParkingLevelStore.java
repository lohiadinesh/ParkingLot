/**
 * 
 */
package com.dkatalis.parkingsystem.store;

import java.util.List;

import com.dkatalis.parkingsystem.pojo.Vehicle;

/**
 * @author Dinesh L
 *
 */
public interface IParkingLevelStore<T extends Vehicle>
{
	public int parkCar(T vehicle);
	
	public boolean leaveCar(int slotNumber);
	
	public List<String> getStatus();
	
	public int getSlotNoFromRegistrationNo(String registrationNo);
	
	public int getAvailableSlotsCount();
	
	public void doCleanUp();
}
