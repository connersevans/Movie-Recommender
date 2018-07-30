import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * POJO, a love story.
 * 
 *
 */
public class User {
	
	private int id;
	private double meanRating;
	private HashMap<String, Double> movieratings;
	private HashMap<Integer, Double> correlations;
	ArrayList<Entry<Integer, Double>> neighborhood;
	int reviewcount;
	double totalcount;
	
	public User() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int s) {
		id = s;
	}
	
	public double getMeanRating() {
		return meanRating;
	}
	
	public void setMeanRating(double rating) {
		meanRating = rating;
	}
	
	public HashMap<String, Double> getMovieRatings() {
		return movieratings;
	}
	
	public void setMovieRatings(HashMap<String, Double> ratings) {
		movieratings = ratings;
	}
	
	public HashMap<Integer, Double> getCorrelations() {
		return correlations;
	}
	
	public void setCorrelations(HashMap<Integer, Double> correlation) {
		correlations = correlation;
	}
	
	public ArrayList<Entry<Integer, Double>> getNeighborhood() {
		return neighborhood;
	}
	
	public void setNeighborhood(ArrayList<Entry<Integer, Double>> users) {
		neighborhood = users;
	}
	
	public int getReviewCount() {
		return reviewcount;
	}
	
	public void setReviewCount(int count) {
		reviewcount = count;
	}
	
	public double getTotalCount() {
		return totalcount;
	}
	
	public void setTotalCount(double total) {
		totalcount = total;
	}
}