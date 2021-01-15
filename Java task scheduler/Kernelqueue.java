package scheduler;

public class Kernelqueue extends Queue
{
	
	public Kernelqueue(GlobalScheduler man)
	{
		super(man);
	}

	@Override
	public void addTask(Task newt)
	{
		tasks.add(newt);
	}
	
	@Override
	public void tick() 
	{
		if(current==null)
		{
			if(isEmpty())
			{
				return;
			}
			else
			{
				int min = 0, index = 0;
				Task mint = tasks.getFirst();
				
				for(Task t: tasks)
				{
					if(t.duration < mint.duration ) 
					{
						mint = t;
						min = index;
					}
					index++;
				}
				
				current = tasks.remove(min);
			}
		}

		active = true;
		
		current.run();
		manager.completed = true;
		
		if(current.isDone())
		{
			if(isEmpty()) active = false;
			manager.finishedlist.addLast(current);
			current = null;
		}
		
		if(!isEmpty())
		{
			for(Task t: tasks) t.age++;
		}
	}
	
	@Override
	public boolean isEmpty()
	{
		return tasks.isEmpty();
	}
	
}
