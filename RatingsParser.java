
	import java.util.ArrayList;
	import java.util.HashMap;

	/**
	 * This is our RatingsParser class.
	 * 
	 *
	 */
	public class RatingsParser implements Parser {

		private ArrayList<Rating> ratings;
		private HashMap<Integer, User> users;

		public RatingsParser() {
			ratings = new ArrayList<Rating>();
			users = new HashMap<Integer, User>();
		}

		@Override
		public void parse(ArrayList<String> lines) {
			for (String s : lines) {
				
				String str[] = s.split("::");
				
				String movie = str[1];
				double review = Double.parseDouble(str[2]);
				int userid = Integer.parseInt(str[0]);
				
				Rating rating = new Rating(movie, review, userid);
				ratings.add(rating);
				
				if (users.containsKey(userid)) {
					User user = users.get(userid);
					users.remove(userid);
					int newreviewcount = user.getReviewCount();
					newreviewcount++;
					user.setReviewCount(newreviewcount);
					double newtotalcount = user.getTotalCount() + review;
					user.setTotalCount(newtotalcount);
					double newmean = newtotalcount / newreviewcount;
					user.setMeanRating(newmean);
					Double rating1 = review;
					HashMap<String, Double> newmap = user.getMovieRatings();
					newmap.put(movie, rating1);
					user.setMovieRatings(newmap);
					users.put(userid, user);
				}
				else {
					User user = new User();
					int reviewcount = 1;
					user.setReviewCount(reviewcount);
					double totalcount = review;
					user.setTotalCount(totalcount);
					Double total = review * 1.0;
					user.setMeanRating(total);
					HashMap<String, Double> map = new HashMap<String, Double>();
					Double newinteger = review;
					map.put(movie, newinteger);
					user.setMovieRatings(map);
					users.put(userid, user);
				}
					
			}
		}
		
		/**
		 * Returns ratings that we have parsed.
		 * @return ratings
		 */
		public ArrayList<Rating> getRatings() {
			return ratings;
		}
		
		/**
		 * Returns HashMap of users 
		 * @return
		 */
		public HashMap<Integer, User> getUsers() {
			return users;
		}
	}

