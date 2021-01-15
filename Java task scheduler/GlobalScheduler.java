package scheduler;

import java.util.LinkedList;
import java.util.Scanner;

public class GlobalScheduler 
{

	int proctime;
	int lasttask;
	boolean completed;
	
	LinkedList<Task> readylist;
	LinkedList<Task> finishedlist;
	
	Queue usertasks;
	Queue kerneltasks;
	
	StringBuilder output;

	public GlobalScheduler()
	{
		readylist = new LinkedList<Task>();
		finishedlist = new LinkedList<Task>();
		
		usertasks = new Userqueue(this);
		kerneltasks = new Kernelqueue(this);
		
		completed = false;
		
		output = new StringBuilder();
		
		proctime = 0;
	}
	
	public void addToOutput(char id)
	{
		output.append(id);
	}
	
	public boolean tick()
	{
		if(proctime==0) return true;
		else {
			if(proctime > readylist.getLast().start && !kerneltasks.active && !usertasks.active && kerneltasks.isEmpty() && 
			   usertasks.isEmpty()) return false;
	
			kerneltasks.tick();
			usertasks.tick();
			
			completed = false;
			return true;
		}
	}
	
	public void run() 
	{
		Scanner sc = new Scanner(System.in);
		String line;
		
		while(sc.hasNextLine())
		{
			line = sc.nextLine();

			if(!line.equals(""))
			{
				String[] args = line.split(",");
				
				Task newtask = new Task(args[0].charAt(0), 
										Integer.parseInt(args[1]), 
										Integer.parseInt(args[2]), 
										Integer.parseInt(args[3]), 
										this);
				
				if(readylist.isEmpty()) readylist.add(newtask);
				else 
				{
					int index = 0;
					while( readylist.size() > index && readylist.get(index).start <= newtask.start ) index++;
					readylist.add(index, newtask);
				}
				
			}
			
		}
		sc.close();
		
		while(tick())
		{
			for(Task t: readylist) 
			{
				if(t.start==proctime)
				{
					if(t.kernel==1) kerneltasks.addTask(t);
					else usertasks.addTask(t);;
				}
			}
			proctime++;
		}
		
		System.out.println(output.toString());
		
		for(int i = 0; i < readylist.size(); i++)
		{
			Task t = readylist.get(i);
			for(Task t2: finishedlist)
			{
				if(t2.id==t.id) System.out.print(t.id+":"+t.age);
			}
			if(i!=readylist.size()-1) System.out.print(",");
		}
	}

}
