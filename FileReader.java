import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a standard file reader class that reads in a file and stores
 * its contents
 *
 */
public class FileReader {
	
	private String filename;
	private ArrayList<String> lines;
	
	/**
	 * Constructor
	 * @param file - the file to be read
	 */
	public FileReader(String file) {
		filename = file;
		lines = new ArrayList<String>();
		readFile();
	}
	
	/**
	 * This will read in the entire text file and store each line 
	 * in an ArrayList<String>
	 */
	private void readFile() {
		try {
			File inputFile = new File(filename);
			Scanner instream = new Scanner(inputFile);
			while (instream.hasNextLine()) {
				String line = instream.nextLine();
				lines.add(line);
			}
			instream.close();
		} catch (Exception e) {
			System.out.println("Your file couldn't be found");
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getLines() {
		return lines;
	}
	
	public String getFileName() {
		return filename;
	}

}