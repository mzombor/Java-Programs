package hazi;

/// Class representing a users score of a book
public class Score 
{
	// Id of the rated book the score and if it's recommendid or not 
	public int bookID;
	public int score;
	public double recommended;
	
	/// Initializes the object with the given values .... duh
	/// @param id - ID of the given book
	/// @param _score - the score of the book
	/// @param rec - the rate of how much the book is recommended
	public Score(int id, int _score, double rec)
	{
		bookID = id;
		score = _score;
		recommended = rec;
	}
}
