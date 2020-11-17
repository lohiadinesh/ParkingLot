/**
 * 
 */
package com.dkatalis.parkingsystem.processor;

import com.dkatalis.parkingsystem.utils.Constants;
import com.dkatalis.parkingsystem.exception.ErrorCode;
import com.dkatalis.parkingsystem.exception.ParkingException;
import com.dkatalis.parkingsystem.pojo.Car;
import com.dkatalis.parkingsystem.service.IAbstractService;
import com.dkatalis.parkingsystem.service.IParkingService;

/**
 * 
 * @author Dinesh L
 */
public class RequestProcessor implements IProcessor
{
	private IParkingService parkingService;
	
	public void setParkingService(IParkingService parkingService) throws ParkingException
	{
		this.parkingService = parkingService;
	}
	
	@Override
	public void process(String input) throws ParkingException
	{
		int level = 1;
		String[] inputs = input.split(" ");
		String key = inputs[0];
		switch (key)
		{
			case Constants.CREATE_PARKING_LOT:
				try
				{
					int capacity = Integer.parseInt(inputs[1]);
					parkingService.createParkingLot(level, capacity);
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
				}
				break;
			case Constants.PARK:
				parkingService.park(level, new Car(inputs[1], inputs[2]));
				break;
			case Constants.LEAVE:
				try
				{
					int slotNumber = Integer.parseInt(inputs[1]);
					parkingService.unPark(level, slotNumber);
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(
							ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
				}
				break;
			case Constants.STATUS:
				parkingService.getStatus(level);
				break;
			case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
				parkingService.getSlotNoFromRegistrationNo(level, inputs[1]);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void initService(IAbstractService service)
	{
		this.parkingService = (IParkingService) service;
	}
}
