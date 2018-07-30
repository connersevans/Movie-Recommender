/**
 * This is the Movie class.
 *
 */
public class Movie {
	
	private String idNo;
	private double numberofratings;
	private double totalrating;
	private double meanrating;
	
	public Movie(String id) {
		idNo = id;
		numberofratings = 0;
		totalrating = 0;
		meanrating = 0;
	}
	
	public String getidNo() {
		return idNo;
	}
	
	public double getNumberOfRatings() {
		return numberofratings;
	}
	
	public double getTotalRating() {
		return totalrating;
	}
	
	public double getMeanRating() {
		return meanrating;
	}
	
	public void updateMeanRating(double rating) {
		numberofratings++;
		totalrating += rating;
		meanrating = totalrating / numberofratings;
	}
}
