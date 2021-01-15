package scheduler;

public class Userqueue extends Queue
{
	
	int clock;
	
	public Userqueue(GlobalScheduler man)
	{
		super(man);
		clock = 0;
	}

	@Override
	public void tick() 
	{
		
		if( manager.completed || manager.kerneltasks.active ) for(Task t: tasks) t.age++;
		
		if(manager.completed || manager.kerneltasks.active)
		{
			active = false;
			if(current!=null)
			{
				tasks.addLast(current);
				current = null;
			}
			return;
		}
		
		if(!isEmpty() && (clock==2 || current==null))
		{
			if(current!=null) tasks.addLast(current);
			current = tasks.removeFirst();
			clock = 0;
		}
		
		if(current!=null)
		{
			if(clock==2) clock = 0;
			current.run();
			clock++;
			
			if(current.isDone())
			{
				current.age = manager.proctime - current.start - current.done;
				if(isEmpty()) active = false;
				manager.finishedlist.addLast(current);
				current = null;
			}
		}
		
		for(Task t: tasks) t.age++;
	}

	@Override
	public void addTask(Task newt)
	{
		tasks.add(newt);
	}
	
	@Override
	public boolean isEmpty()
	{
		return tasks.isEmpty();
	}
}
