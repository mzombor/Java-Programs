package oprehf;

public class Node 
{

	/// Blocked flag
	boolean blocked = false;
	
	/// Retusn true if it's blocked
	public boolean isblocked()
	{
		return blocked;
	}
	
	/// Sets blocked to true
	public void block()
	{
		blocked = true;
	}
	
	/// Sets blocked to false
	public void unblock()
	{
		blocked = false;
	}
	
	/// Adds task waitlist of resource
	public void add(Task t)
	{
	}
	
	/// Returns first task from waitlist
	public Task pop()
	{
		return null;
	}
}
