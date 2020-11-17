/**
 * 
 */
package com.dkatalis.parkingsystem.service;

import java.util.Optional;

import com.dkatalis.parkingsystem.exception.ParkingException;
import com.dkatalis.parkingsystem.pojo.Vehicle;

/**
 * @author Dinesh L
 *
 */
public interface IParkingService extends IAbstractService
{
	/* ---- Actions ----- */
	public void createParkingLot(int level, int capacity) throws ParkingException;
	
	public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;
	
	public void unPark(int level, int slotNumber) throws ParkingException;
	
	public void getStatus(int level) throws ParkingException;
	
	public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException;
	
	public int getSlotNoFromRegistrationNo(int level, String registrationNo) throws ParkingException;
	
	public void doCleanup();
}
