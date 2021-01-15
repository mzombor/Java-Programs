package hazi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/// Main class, collects the data, initializes the user list
/// Runs loops processing the data and prints the result to STDOUT
public class Main 
{

	public static void main(String[] args) 
	{
		/// Create the lust of users and initialize values
		Userlist users = null;
		int usercount = 0, bookcount = 0;
		Scanner sc = null;
		sc = new Scanner(System.in);
		/// Read the first line containing meta information
		String line = sc.nextLine();
		
		/// Get the needed sizes by parsing the first line
		int datacount = Integer.parseInt(line.split("\t")[0]);
		usercount = Integer.parseInt(line.split("\t")[1]);
		bookcount = Integer.parseInt(line.split("\t")[2]);
		
		/// Create the userlist
		users = new Userlist(bookcount);
		
		/// Fill up the user lust line by line
		while(sc.hasNext()) 
		{
		
			line = sc.nextLine();
			String[] data = line.split("\t");
			
			/// Add the needed score to the needed user
			int idx = Integer.parseInt(data[0]);
			users.get_user(idx).add (new Score(Integer.parseInt(data[1]), Integer.parseInt(data[2]), 0) );
		
		}
		sc.close();		
	
		/// Get average ratings for the users
		users.get_averages();
		
		/// Getting the predicted recommendation vaues for each and every user wiehged by their similarity
		for(User user: users.user_list)
		{
			for(int book_id = 0; book_id < bookcount; book_id++) 
			{
				double predicted_score = 0;
				
				if(!user.hasBook(book_id)) 
				{
					for(User other_user: users.user_list)
					{
						if(other_user.hasBook(book_id))
						{
							/// Calculate the correlation between the users 
							double d = (other_user.get_book(book_id).score - other_user.avg);
							double corr = user.correlation(other_user) * d;
							predicted_score += corr;
						}
					}
				}
				/// Add the predicted value to the recommended list of a user
				user.recommendations.add(new Score(book_id, 0, predicted_score*0.1 + user.avg));
			
			}
		}
				
		/// Get back the top 10 recommended books for every user
		for(User user: users.user_list)
		{
			for(int i = 0; i < 10; i++)
			{
				Score max = user.recommendations.get(0);
				for(Score score: user.recommendations)
				{
					if(score.recommended > max.recommended)
					{
						max = score;
					}
				}
				/// Remove it to make the max value updated
				user.recommendations.remove(max);
				
				/// Print results
				System.out.print(max.bookID);
				if(i<9)
				{
					System.out.print("\t");
				}
				else System.out.print("\n");
			}
		}
	}
}
