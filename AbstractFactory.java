/**
 * Abstract Factory for get our Parser Factory. In the future if we want to
 * create any other types of factories we can add a method here.
 *
 */
public abstract class AbstractFactory {
	
	/**
	 * Returns a specific parser based off of the input string.
	 * @param name
	 * @return either a MovieParser or RatingsParser
	 */
	abstract Parser getParser(String name);

}
