/**
 * This is our rating class.
 * 
 *
 */
public class Rating {
	
	private String movieId;
	private double rating;
	private int userId;
	
	public Rating(String movie, double review, int person) {
		movieId = movie;
		rating = review;
		userId = person;
	}
	
	public String getMovieId() {
		return movieId;
	}
	
	public double getRating() {
		return rating;
	}
	
	public int getUserId() {
		return userId;
	}
}
