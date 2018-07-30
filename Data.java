import java.util.HashMap;

/**
 * Data class for 3-tier architecture
 * 
 *
 */
public class Data {
	
	ParserFactory factory;
	FileReader reader;
	MovieParser movieParser;
	RatingsParser ratingsParser;
	
	public Data(String filename) {
		factory = new ParserFactory();
		reader = new FileReader(filename);
	}
	
	/**
	 * This gets all of the user data from a parser
	 * @return p.getUsers()
	 */
	public HashMap<Integer, User> getUserData() {
		RatingsParser p = (RatingsParser) factory.getParser(reader.getFileName());
		p.parse(reader.getLines());
		return p.getUsers();
	}
	
	/**
	 * This gets all of the movie data from a movie parser.
	 * @return p.getMovies()
	 */
	public HashMap<String, String> getMovieData() {
		MovieParser p = (MovieParser) factory.getParser(reader.getFileName());
		p.parse(reader.getLines());
		return p.getMovies();
	}
	
	/**
	 * This gets all of the movie data, but with the movie's id mapped to its title.
	 * This is useful for when we want our program to print out the actual movie titles
	 * and not identification numbers.
	 * @return p.getMoviesIdTitle();
	 */
	public HashMap<String, String> getMovieDataIdTitle() {
		MovieParser p = (MovieParser) factory.getParser(reader.getFileName());
		p.parseReverseOrder(reader.getLines());
		return p.getMoviesIdTitle();
	}
}
