package hazi;

import java.util.ArrayList;

/// User class representing the user, stores the users ratings
/// his recommendations and the list of every book the user could posess to make
/// calculations easier
public class User 
{
	/// The average rating of books the user gave and their count
	double avg;
	int book_cnt;
	
	/// List of books and recommendations
	ArrayList<Score> scores = new ArrayList<Score>();
	ArrayList<Score> recommendations = new ArrayList<Score>();
	ArrayList<Score> bookslist = new ArrayList<Score>();
	
	/// Created the user with the given ID and how many books there are
	/// @param _id - ID of the user created
	/// @param cnt - a count of how many books there are
	public User(int _id, int cnt)
	{
		book_cnt = cnt;
		/// Create as many empty ratins as there are books
		/// This is needed to make getting the rating of a book a simple
		/// Indexing operation instead of an expensive search
		for(int i = 0; i <= book_cnt; i++)
		{
			scores.add(new Score(i, 0, 0));
		}
	}
	
	/// Add score to user
	/// @param sc - the score with the books information
	public void add(Score sc)
	{
		scores.get(sc.bookID).score = sc.score;
		bookslist.add(sc);
	}
	
	/// Returns if the user has a rating to the book provided through a
	/// @param idx - the index of the book being queried
	public boolean hasBook(int idx) 
	{
		return scores.get(idx).score > 0;
	}
	
	/// Calculate average of a user based on his recommendations
	public double get_average()
	{
		for(Score s: bookslist) avg += (double)s.score;
		
		avg = Math.round(100.0 * avg * (1.0 / (double)bookslist.size())) * (1.0/100.0);
		
		return avg;
	}
	
	/// Get the needed books score, even if it's empty
	/// @param idx - index of the book being queried
	public Score get_book(int idx)
	{
		return scores.get(idx);
	}

	/// Calculate the correlation between this user and the one provided
	/// through arguments. The calculation is done by using the Paerson
	/// Method.
	/// @param other_user - the user compared to THIS
	public double correlation(User other_user) 
	{
		/// Avarage difference between books and the quadratic values
		/// under the division line in the Paerson equiation
		double average_book_d = 0;
		double pearson_a = 0, pearson_b = 0;
		
		/// Comppare every book the users have
		for(int book_index = 0; book_index < book_cnt; book_index++)
		{
			/// Get difference between books the users have
			double myscore = scores.get(book_index).score;
			double other_score = other_user.scores.get(book_index).score;
			
			/// Update the Paerson coefficients
			if(myscore > 0 && other_score > 0)
			{
				double d1 = (myscore - avg);
				double d2 = (other_score - other_user.avg);
				
				average_book_d += d1 * d2;
				
				pearson_a += d1 * d1;
				pearson_b += d2 * d2;
			}
		}
		
		if ((pearson_a * pearson_b) <= 0) return 0.0;
		double res = average_book_d * (1.0 / Math.sqrt(pearson_b*pearson_a));
		
		return res;
	}
	
}
