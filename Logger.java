import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This is our Logger Singleton Class. It prohibits the creation of multiple
 * Logger objects.
 *
 */
public class Logger {
	public static Logger instance;
	private static String filename;
	private PrintWriter writer;
	
	protected Logger(String file) throws IOException {
		writer = new PrintWriter(new FileWriter(file, true));
		instance = this;
		filename = file;
	}
	
	/**
	 * Gets an instance of the Logger class. If there's already a Logger object
	 * in existence, it just returns that Logger via its instance variable.
	 * @return
	 * @throws IOException 
	 */
	public static Logger getInstance() throws IOException {
		if (instance == null) {
			instance = new Logger(filename);
		}
		return instance;
	}
	
	/**
	 * Appends the top recommendations given to a user to a .txt file.
	 * @param recommendations
	 * @param userID
	 * @throws IOException
	 */
	public void appendTopRecsToFile(ArrayList<String> recommendations, int userID) throws IOException {
		writer.append("Top Recommendations for user: " + userID + ": " + '\n');
		int i = 1;
		for (String s : recommendations) {
			writer.append(i + ") " + s + '\n');
			i++;
		}
		writer.append('\n');
	}
	
	/**
	 * Appends a user's expected preference for a movie to a .txt file.
	 * @param preference
	 * @param id
	 * @param movie
	 * @throws IOException
	 */
	public void appendExpectedPreference(double preference, int id, String movie) throws IOException {
		writer.append("User: " + id + "'s expected preference for: " + movie
				+ ": " + preference + '\n' + '\n');
	}
	
	/**
	 * Closes the PrintWriter
	 */
	public void closeFile() {
		writer.close();
	}
}

