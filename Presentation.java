import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Presentation {

	public static void main(String[] args) throws IOException {
		Logger singleton = new Logger("out.txt");
		Controller controller = new Controller("ratings.dat", "movies.dat");
		HashMap<String, String> movieData = controller.getMovieData();
		HashMap<Integer, User> userData = controller.getUserData();
		Scanner instream = new Scanner(System.in);
		boolean finished = false;
		while (finished == false) {
			System.out.println("Please type the number 1 for option one or the number"
					+ " 2 for option two.");
			System.out.println("1) Input a movie name and a user id to "
					+ "get a user's predicted preference for the given movie.");
			System.out.println("2) Input a user id and the number of recommendations"
					+ " you would like for that user.");
			System.out.println("3) Quit");
			String choice = instream.nextLine();
			if (choice.equals("1")) {
				System.out.println("Please enter a movie");
				String movie = instream.nextLine();
				boolean found = false;
				while (found == false) {
					for (String s : movieData.keySet()) {
						if (s.equals(movie)) {
							found = true;
							break;
						}
					}
					if (found == false) {
						System.out.println("We can't find that movie. "
								+ "Please input a valid movie name or one"
								+ " that is in our system.");
						movie = instream.nextLine();
					}
				}
				System.out.println("Please enter a user id");
				int id = Integer.parseInt(instream.nextLine().trim());
				boolean found_user = false;
				while (found_user == false) {
					if (userData.containsKey(id)) {
						found_user = true;
						break;
					}
					if (found_user == false) {
						System.out.println("We can't find that user in our system. "
								+ "Please enter a valid user that we have on file.");
						id = Integer.parseInt(instream.nextLine().trim());
					}
				}
				double prediction = controller.calculatePrediction(id, movie);
				singleton.appendExpectedPreference(prediction, id, movie);
				System.out.println(" ");
			}
			else if (choice.equals("2")) {
				System.out.println("Please enter a user id");
				int idNo = Integer.parseInt(instream.nextLine().trim());
				boolean found_user2 = false;
				while (found_user2 == false) {
					if (userData.containsKey(idNo)) {
						found_user2 = true;
						break;
					}
					if (found_user2 == false) {
						System.out.println("We can't find that user in our system. "
								+ "Please enter a valid user that we have on file.");
						idNo = Integer.parseInt(instream.nextLine().trim());
					}
				}
				System.out.println("Please enter the number of recommendations"
						+ " you'd like to get for this user");
				int number = Integer.parseInt(instream.nextLine().trim());
				ArrayList<String> recommendations = controller.getTopMovies(idNo, number);
				singleton.appendTopRecsToFile(recommendations, idNo);
				System.out.println(" ");
			}
			else if (choice.equals("3")) {
				singleton.closeFile();
				instream.close();
				System.out.println("Goodbye");
				finished = true;
			}
		}
	}
}
