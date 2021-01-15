package oprehf;

import java.util.ArrayList;

public class Task extends Node
{
	
	/// Store instruction strings
	ArrayList<Integer> instructions;
	/// Instruction pointer
	int ip = 0;
	
	/// Creates task with insturctions
	/// @param ins - instructions from which the task consists of
	public Task(String[] ins)
	{
		/// Instatntiate obj
		instructions = new ArrayList<Integer>();
		/// load instructions to list
		boolean first = true;
		for( String s : ins)
		{
			if(!first) 
			{
				/// Check if it's noop
				if(s.charAt(0) != '+' && s.charAt(0) != '-') instructions.add(0);
				/// If it's not, add the index of resource it's trying to allocate
				else
				{
					/// allocate resource
					if(s.charAt(0) == '+') instructions.add(Character.getNumericValue(s.charAt(2)));
					else instructions.add(-1*Character.getNumericValue(s.charAt(2)));
				}
			}
			else first = false;
		}
		/// set blocked flag
		blocked = false;
	}
	
	
	/// Returns if there's a next instruction
	public boolean hasnext()
	{
		return !(ip >= instructions.size());
	}
	
	/// Returns next unstruction
	public int next()
	{
		return instructions.get(ip);
	}
}
