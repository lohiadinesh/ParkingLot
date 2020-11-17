/**
 * 
 */
package com.dkatalis.parkingsystem.processor;

import com.dkatalis.parkingsystem.utils.Command;
import com.dkatalis.parkingsystem.exception.ParkingException;
import com.dkatalis.parkingsystem.service.IAbstractService;

/**
 * @author Dinesh L
 *
 */
public interface IProcessor
{
	public void initService(IAbstractService service);
	
	public void process(String action) throws ParkingException;
	
	public default boolean validate(String inputString)
	{
		// Split the input string to validate command and input value
		boolean valid = true;
		try
		{
			String[] inputs = inputString.split(" ");
			int params = Command.getCommands().get(inputs[0]);
			switch (inputs.length)
			{
				case 1:
					if (params != 0) // e.g status -> inputs = 1
						valid = false;
					break;
				case 2:
					if (params != 1) // create_parking_lot 6 -> inputs = 2
						valid = false;
					break;
				case 3:
					if (params != 2) // park KA-01-P-333 White -> inputs = 3
						valid = false;
					break;
				default:
					valid = false;
			}
		}
		catch (Exception e)
		{
			valid = false;
		}
		return valid;
	}
}
