package oprehf;

import java.util.ArrayList;

public class Handler 
{
	/// Tasks, resources and graphnodes
	ArrayList<Task> tasks = new ArrayList<Task>();
	ArrayList<Resource> resources = new ArrayList<Resource>();
	ArrayList<Edge> graph = new ArrayList<Edge>();
	Edge lastedge = null;

	/// Add task to handler
	public void add(Task t)
	{
		tasks.add(t);
	}
	
	/// Build resource list from string
	public void build()
	{
		int min = 1;
		/// Create resources
		for(Task t: tasks) for(Integer i: t.instructions) 
		{
			/// Get the maximum of numbers
			if(i>min) min = 5;
		}
		/// Add resources to list
		for(int i = 0; i < min; i++) resources.add(new Resource());
	}

	/// Remove last edge from graph
	public void removelast()
	{
		graph.remove(lastedge);
		lastedge = null;
	}
	
	/// Run the handler, execute instructions solve circular dependency problems
	public void run()
	{
		/// Process instructions
		boolean ended = false;
		while(!ended)
		{
			/// If there are instructions, iterate over tasks
			for(int i = 0; i < tasks.size(); i++)
			{
				Task t = tasks.get(i);
				/// If it has an instruction to run
				if(t.hasnext())
				{
					/// If it's not zero,try allocating the resources
					if(t.next() != 0)
					{
						if(t.next() > 0) 
						{
							if(!t.isblocked())
							{
								
								/// Insert into graph
								createedge(graph, t, resources.get(Math.abs(t.next()) -1));

								
								/// Test if we created a circle, if yes, remove last edge
								boolean hascircle = checkgraph();
								if(hascircle) 
								{
									/// Remove problematic edge and unblock stuck task
									removelast();
									t.unblock();
									System.out.println("T"+(tasks.indexOf(t)+1)+","+(t.ip+1)+",R"+Math.abs(t.next()));
								}
								if(!t.isblocked()) t.ip++;
							}
						}
						else
						{
							if(!t.isblocked())
							{
								/// Remove edge from graph
								removeedge(graph, t, resources.get(Math.abs(t.next()) -1));
								t.ip++;
							}
						}
					}
					// If it's noop just step over it
					else 
					{
						if(!t.isblocked()) t.ip++;
					}
				}
			}
		
			
			for(Task t: tasks) 
			{
				/// If the task is done
				if(!t.hasnext())
				{
					/// Free all edges contaning this task
					for(int i = 0; i < graph.size(); i++) 
					{
						/// If it's a simple waiting remove the edge
						if(graph.get(i).back==t) graph.remove(i);
						/// If it's an allocation, unblock waiting tasks
						else if(graph.get(i).front==t)
						{
							removeedge(graph, t, graph.get(i).back);
						}
					
					}
				}
			}
			/// Check if therer are instructiosn to run*/
			ended = true;
			for(Task t: tasks) if(t.hasnext()) ended = false;
		}	
	}
		
	/// Remove edge from graph
	private void removeedge(ArrayList<Edge> graph, Node t, Node resource) 
	{
		/// Remove allocation edge from graph
		for(int i = 0; i < graph.size(); i++) if(graph.get(i).front==t && graph.get(i).back==resource) graph.remove(i);
		/// Remove task dependencies
		for(int i = 0; i < graph.size(); i++) 
		{
			if(graph.get(i).front==resource) 
			{
				/// If there are tasks waiting
				if( !((Resource) resource).waitlist.isEmpty())
				{
					/// Try adding resources to oldest waiting task
					if( ((Resource) resource).waitlist.remove(0) == ((Task)graph.get(i).back))
					{
						/// Create edge to give resource to the first waiting task
						createedge(graph, graph.get(i).back, resource);
						/// Advance instruction pointer to avoid edge doubling and infinite lööps
						Task last = ((Task) graph.get(i).back);
						last.ip++;
					}
				}
				/// Unblock task and remove dependencie
				graph.get(i).back.unblock();
				graph.remove(i);
			}
		}
	}

	/// Add edge to graph
	public void createedge(ArrayList<Edge> graph, Node t, Node r)
	{
		/// Signals if the resource is taken
		boolean used = false;
		/// Check for resource usage
		for(Edge e: graph) 
		{
			/// If it's found, return false
			if(e.getback() == r) used = true;
		}
		// If the resource is not used, create edge
		if(!used) 
		{
			/// Create new edge and add to last one ts
			/// avoiding circles possible
			Edge newedge = new Edge(r, t);
			lastedge = newedge;
			
			//if(resources.contains(newedge.back)) System.out.println("R"+(resources.indexOf(newedge.back)+1)+" - T"+(tasks.indexOf(newedge.front)+1));
			//else if(tasks.contains(newedge.back)) System.out.println("T"+(tasks.indexOf(newedge.back)+1)+" - R"+(resources.indexOf(newedge.front)+1));
			graph.add(newedge);
		}
		else 
		{
			/// New edge and add to last one
			Edge newedge = new Edge(t, r);
			lastedge = newedge;
			t.block();
			
			//if(resources.contains(newedge.back)) System.out.println("R"+(resources.indexOf(newedge.back)+1)+" - T"+(tasks.indexOf(newedge.front)+1));
			//else if(tasks.contains(newedge.back)) System.out.println("T"+(tasks.indexOf(newedge.back)+1)+" - R"+(resources.indexOf(newedge.front)+1));
			/// Add resource waiting and waitingfor attribute
			if( !((Resource) r).waitlist.contains((Task) t)) ((Resource) r).waitlist.add((Task) t); 
			graph.add(newedge);
		}
		
	}
	
	/// Check for circles in a graph
	public boolean checkgraph()
	{
		/// Check all edges
		for(Edge e: graph)
		{
			/// Create traveller
			Edge first = e;
			Edge traveller = e;
			boolean ended = false;
			
			/// Travel until branches end, or circle is founds
			while(!ended)
			{
				/// Check all edges
				for(Edge c: graph)
				{
					/// If continuation found, go on
					if(traveller.front==c.back)
					{
						/// Check if c is the starting verte
						if(c==first) return true;
						
						traveller = c;
						ended = false;
					}
					/// If no continuation found, quit
					else ended = true;
				}
			}
			
		}
		
		return false;
	}
}
	
