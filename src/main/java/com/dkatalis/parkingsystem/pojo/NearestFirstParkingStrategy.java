/**
 * 
 */
package com.dkatalis.parkingsystem.pojo;

import java.util.TreeSet;

/**
 * @author Dinesh L
 *
 */
public class NearestFirstParkingStrategy implements IParkingStrategy
{
	private TreeSet<Integer> freeSlots;
	
	public NearestFirstParkingStrategy()
	{
		freeSlots = new TreeSet<Integer>();
	}
	
	@Override
	public void add(int i)
	{
		freeSlots.add(i);
	}
	
	@Override
	public int getSlot()
	{
		return freeSlots.first();
	}
	
	@Override
	public void removeSlot(int availableSlot)
	{
		freeSlots.remove(availableSlot);
	}
}
