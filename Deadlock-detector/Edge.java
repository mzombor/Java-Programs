package oprehf;

public class Edge 
{
	/// Previous and next graph nodes
	Node front = null;
	Node back = null;
		
	/// Parametric constructor
	public Edge(Node b, Node f)
	{
		front = f;
		back = b;
	}
	
	/// Add front node
	public void adfront(Node n)
	{
		front = n;
	}
	
	/// Add backnode
	public void adpback(Node p)
	{
		back = p;
	}
	
	/// Get front node
	public Node getfront()
	{
		return front;
	}

	/// Get back node
	public Node getback()
	{
		return back;
	}
}
