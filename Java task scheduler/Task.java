package scheduler;


public class Task
{
	char id;
	int kernel;
	int start;
	int duration;
	
	int age;
	int done;
	
	GlobalScheduler manager;
	
	public Task(char i, int k, int s, int d, GlobalScheduler man)
	{
		id = i;
		kernel = k;
		start = s;
		duration = d;
		manager = man;
		
		age = 0;
		done = 0;
	}
	
	public char getID()
	{
		return id;
	}

	public void run() 
	{
		done++;
		
		// System.out.print(id);
		
		if(manager.output.length() == 0) manager.addToOutput(id);
		else if(manager.output.charAt(manager.output.length() - 1) != id) manager.addToOutput(id);
	}

	
	public boolean isDone()
	{
		if(duration==done) return true;
		else return false;
	}
}
