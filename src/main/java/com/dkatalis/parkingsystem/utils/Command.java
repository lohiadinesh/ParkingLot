package com.dkatalis.parkingsystem.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dinesh L
 *
 */
public class Command {
	private static volatile Map<String, Integer> commands = new HashMap<String, Integer>();

	static {
		commands.put(Constants.CREATE_PARKING_LOT, 1);
		commands.put(Constants.PARK, 2);
		commands.put(Constants.LEAVE, 1);
		commands.put(Constants.STATUS, 0);
		commands.put(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
		commands.put(Constants.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
	}

	/**
	 * @return the commandsParameterMap
	 */
	public static Map<String, Integer> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commandsParameterMap to set
	 */
	public static void addCommand(String command, int parameterCount) {
		commands.put(command, parameterCount);
	}

}
