package oprehf;

import java.util.Scanner;

public class Main 
{

	public static void main(String[] args) 
	{
		
		/// Input reader
		Scanner sc = new Scanner(System.in);
		/// Handler for tasks
		Handler h = new Handler();
	
		/// Read input from stdin
		while(sc.hasNext())
		{
			/// get next line
			String line = sc.next();
			/// Split line and create task from it
			String[] ins = line.split(",");
			h.add(new Task(ins));
		}
		
		h.build();
		h.run();
		
		

	}
}
