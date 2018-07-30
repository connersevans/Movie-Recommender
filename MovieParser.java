import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is our MovieParser class. It parses a movie file.
 * @author connorfairman
 *
 */
public class MovieParser implements Parser {
	
	HashMap<String, String> movies;
	HashMap<String, String> moviesIdTitle;
	
	public MovieParser() {
		movies = new HashMap<String, String>();
		moviesIdTitle = new HashMap<String, String>();
	}

	@Override
	public void parse(ArrayList<String> lines) {
		for (String s : lines) {
			String t[] = s.split("::");
			String identification = t[0];
			String str = t[1];
			String line[] = str.split("\\(");
			String title = line[0].trim();
			movies.put(title, identification);
		}
	}
	
	/**
	 * This parses a movie file, but instead of storing the movie data as
	 * <title, id>, it stores it in a HashMap as <id, title>.
	 * @param lines
	 */
	public void parseReverseOrder(ArrayList<String> lines) {
		for (String s : lines) {
			String t[] = s.split("::");
			String identification = t[0];
			String str = t[1];
			String line[] = str.split("\\(");
			String title = line[0].trim();
			moviesIdTitle.put(identification, title);
		}
	}
	
	/**
	 * This returns our movies mapped as <title, id>.
	 * @return movies
	 */
	public HashMap<String, String> getMovies() {
		return movies;
	}
	
	/**
	 * This returns our movies mapped as <id, title>.
	 * @return moviesIdTitle
	 */
	public HashMap<String, String> getMoviesIdTitle() {
		return moviesIdTitle;
	}
}
