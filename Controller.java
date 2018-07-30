import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


/**
 * This is the Controller class for the 3-tier architecture design pattern.
 * This is where calculations of our data will occur.
 * 
 *
 */
public class Controller {

	Data data;
	Data data1;
	HashMap<Integer, User> userData;
	HashMap<String, String> movieData;
	HashMap<String, String> movieDataIdTitle;

	public Controller(String filename, String filename1) {
		data = new Data(filename);
		data1 = new Data(filename1);
		userData = data.getUserData();
		movieData = data1.getMovieData();
		movieDataIdTitle = data1.getMovieDataIdTitle();
	}

	/**
	 * This is where we calculate the Pearson Correlation between a given user and
	 * every other user.
	 * @param userid
	 * @return correlationmap - a map of all users to their Pearson correlations.
	 */
	public HashMap<Integer, Double> calculatePearson(int userid) {
		HashMap<Integer, Double> correlationmap = new HashMap<Integer, Double>();
		for (int id : userData.keySet()) {
			if (id == userid) {
				continue;
			}
			double correlation = calculateCorrelation(userid, id);
			correlationmap.put(id, correlation);
		}
		User u = userData.get(userid);
		u.setCorrelations(correlationmap);
		return correlationmap;
	}

	/**
	 * This is the helper method for the calculatePearson method. It calculates
	 * a single Pearson Correlation.
	 * @param userid1
	 * @param userid2
	 * @return
	 */
	private double calculateCorrelation(int userid1, int userid2) {
		double numerator = 0;
		double denom1 = 0;
		double denom2 = 0;
		User u1 = userData.get(userid1);
		User u2 = userData.get(userid2);
		HashMap<String, Double> ratings1 = u1.getMovieRatings();
		HashMap<String, Double> ratings2 = u2.getMovieRatings();
		for (String movie : ratings1.keySet()) {
			if (ratings2.containsKey(movie)) {
				double d1 = ratings1.get(movie);
				d1 -= u1.getMeanRating();
				double d2 = ratings2.get(movie);
				d2 -= u2.getMeanRating();
				double comp = d1 * d2;
				numerator += comp;
				denom1 += (d1) * (d1);
				denom2 += (d2) * (d2);
			}
		}
		if (denom1 == 0 || denom2 == 0) {
			return 0.00;
		}
		double realdenom = Math.sqrt(denom1) * Math.sqrt(denom2);
		double correlation = numerator / realdenom;
		return correlation;
	}

	/**
	 * This method sets a User's neighborhood, which we define to be the 20 users with
	 * the most similar taste to the given user. If we don't have 20 users that are in the
	 * user's neighborhood, we just return a neighborhood with all of the user's neighbors.
	 * @param userid
	 * @param movie
	 * @param check
	 * @return neighborhood
	 */
	public ArrayList<Entry<Integer, Double>> setUserNeighborhood(int userid, String movie, int check) {
		HashMap<Integer, Double> correlations = calculatePearson(userid);
		ArrayList<Entry<Integer, Double>> allneighbors = new ArrayList<Entry<Integer, Double>>(correlations.entrySet());
		Collections.sort(allneighbors, new Comparator<Entry<Integer,Double>> () {
			@Override
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
				if (o1.getValue() > o2.getValue()) {
					return 1;
				}
				else if (o1.getValue() < o2.getValue()) {
					return -1;
				}
				else {
					return 0;
				}
			}

		});
		Collections.reverse(allneighbors);
		ArrayList<Entry<Integer, Double>> neighborhood = new ArrayList<Entry<Integer, Double>>();
		// QUESTION 1: PREDICTION FOR GIVEN ITEM
		if (check == 0) {
			int i = 0;
			while (neighborhood.size() < 20 && i != allneighbors.size()) {
				User temp = userData.get(allneighbors.get(i).getKey());
				Double correlation = allneighbors.get(i).getValue();
				if (correlation == 0) {
					i++;
					continue;
				}
				HashMap<String, Double> movies = temp.getMovieRatings();
				if (movies.containsKey(movie)) {
					neighborhood.add(allneighbors.get(i));
				}
				else {
				// don't add to neighbhorhood
				}
				i++;
			}
		}
		// QUESTION 2: PREDICTION FOR N PREFERENCES
		else {
			int j = 0;
			while (neighborhood.size() < 20 && j != allneighbors.size()) {
				neighborhood.add(allneighbors.get(j));
				j++;
			}
		}
		userData.get(userid).setNeighborhood(neighborhood);
		return neighborhood;
	}

	/**
	 * This method calculates a predicted user's rating for a given movie.
	 * @param userid
	 * @param movie
	 * @return prediction
	 */
	public double calculatePrediction(int userid, String movie) {
		double numerator = 0;
		double denomenator = 0;
		String id = null;
		for (String s : movieData.keySet()) {
			if (s.equals(movie)) {
				id = movieData.get(s);
			}
		}
		if (id == null) {
			System.out.println("invalid movie name");
		}
		setUserNeighborhood(userid, id, 0);
		ArrayList<Entry<Integer, Double>> neighborhood = userData.get(userid).getNeighborhood();

		for (int i = 0; i < neighborhood.size(); i++) {
			User u = userData.get(neighborhood.get(i).getKey());
			double correlation = neighborhood.get(i).getValue();
			HashMap<String, Double> movies = u.getMovieRatings();
			double rating = movies.get(id);
			double meanrating = u.getMeanRating();
			double num = correlation * (rating - meanrating);		
			numerator += num;
			denomenator += Math.abs(num);
		}
		double userrating = userData.get(userid).getMeanRating();
		if (denomenator == 0) {
			System.out.println("There were no neighbors for this user. Therefore, we"
					+ " can't give you the user's predicted preference for this movie.");
			return -1.00;
		}
		double prediction = userrating + (numerator / denomenator);
		System.out.println(prediction);
		return prediction;
	}

	/**
	 * This method gets the top recommended movies for a given user.
	 * @param userid
	 * @param n
	 * @return recommendations
	 */
	public ArrayList<String> getTopMovies(int userid, int n) {
		ArrayList<String> topmovies = new ArrayList<String>();
		HashMap<String, Movie> allmovies = new HashMap<String, Movie>();
		String tempmovie = "movie";
		setUserNeighborhood(userid, tempmovie, 1);
		User u = userData.get(userid);
		ArrayList<Entry<Integer, Double>> neighborhood = u.getNeighborhood();
		//for all neighbors
		for (int i = 0; i < neighborhood.size(); i++) {
			Entry<Integer, Double> neighborentry = neighborhood.get(i);
			User neighbor = userData.get(neighborentry.getKey());
			HashMap<String, Double> neighborsmovies = neighbor.getMovieRatings();
			Set movieset = neighborsmovies.keySet();
			Iterator it = movieset.iterator();
			while (it.hasNext()) {
				String moviename = (String) it.next();
				if (allmovies.containsKey(moviename)) {
					allmovies.get(moviename).updateMeanRating(neighborsmovies.get(moviename));
				}
				else {
					Movie movie = new Movie(moviename);
					movie.updateMeanRating(neighborsmovies.get(moviename));
					allmovies.put(movie.getidNo(), movie);
				}
			}
		}
		ArrayList<Movie> allmovieslist = new ArrayList<Movie>();
		Set allmovieset = allmovies.keySet();
		Iterator it2 = allmovieset.iterator();
		while (it2.hasNext()) {
			String moviename = (String) it2.next();
			Movie amovie  = allmovies.get(moviename);
			allmovieslist.add(amovie);
		}
		Collections.sort(allmovieslist, new Comparator<Movie>() {
			@Override
			public int compare(Movie m1, Movie m2) {
				Double rating1 = m1.getMeanRating();
				Double rating2 = m2.getMeanRating();
				return rating1.compareTo(rating2);
			}
		});
		Collections.reverse(allmovieslist);
		int i = 0;
		while (topmovies.size() < n) {
			Movie amovie = allmovieslist.get(i);
			if (amovie.getNumberOfRatings() >= 1) {
				String name = amovie.getidNo();
				topmovies.add(name);
			}
			i++;
		}
		ArrayList<String> recommendations = new ArrayList<String>();
		for (String s : topmovies) {
			if (movieDataIdTitle.containsKey(s)) {
				recommendations.add(movieDataIdTitle.get(s));
				System.out.println(movieDataIdTitle.get(s));
			}
		}
		return recommendations;
	}

	/**
	 * This is a getter method that gets movie data.
	 * @return movieData
	 */
	public HashMap<String, String> getMovieData() {
		return movieData;
	}

	/**
	 * This is a getter method that gets user data.
	 * @return userData
	 */
	public HashMap<Integer, User> getUserData() {
		return userData;
	}
}
