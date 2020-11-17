/**
 * 
 */
package com.dkatalis.parkingsystem.pojo;

/**
 * @author Dinesh L
 *
 */
public interface IParkingStrategy
{
	public void add(int i);
	
	public int getSlot();
	
	public void removeSlot(int slot);
}
