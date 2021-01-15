package scheduler;

import java.util.LinkedList;

public class Queue 
{
	LinkedList<Task> tasks;
	boolean active;
	Task current;
	
	GlobalScheduler manager;
	
	public Queue(GlobalScheduler man)
	{
		tasks = new LinkedList<Task>();
		active = false;
		current = null;
		manager = man;
	}
	
	public boolean isEmpty() 
	{
		return false;
	}
	
	public void addTask(Task newt)
	{}
	
	public void tick()
	{}
	
}
