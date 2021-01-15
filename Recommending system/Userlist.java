package hazi;

import java.util.ArrayList;

/// User list, basically holds the users in an arraylist
/// able to calculate averages of a certain user or give back the
/// users themselves
public class Userlist 
{
	/// Count of users stored in the lust
	int cnt;
	/// Arraylist of users
	ArrayList<User> user_list = new ArrayList<User>();
	
	// Initializes the count of users
	public Userlist(int _cnt)
	{
		cnt = _cnt;
	}
	
	/// Returns the needed user based on it's ID
	/// @param index - ID of user being queried
	public User get_user(int index) 
	{
		/// If the user isn't added to the list yet, create him
		if(index > user_list.size()-1)
		{
			User us = new User(index, cnt);
			user_list.add(us);
			return us;
		}
		/// Or just return the user simply
		else return user_list.get(index);
	}
	
	/// Calculate averages for every user
	public void get_averages()
	{
		for(User u: user_list) u.get_average();
	}

	// Returns the list of users
	public ArrayList<User> getList()
	{
		return user_list;
	}
}
