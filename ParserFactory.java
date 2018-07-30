/**
 * This is a Concrete Factory - ParserFactory class extending AbstractFactory.
 * 
 *
 */
public class ParserFactory extends AbstractFactory {

	@Override
	Parser getParser(String name) {
		if (name.equalsIgnoreCase("ratings.dat")) {
			return new RatingsParser();
		} else if (name.equalsIgnoreCase("movies.dat")) {
			return new MovieParser();
		} else {
			System.out.println("NOT RETURNING ANY PARSER");
			return null;
		}
	}
}