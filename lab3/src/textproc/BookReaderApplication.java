package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BookReaderApplication {

	public static void main(String[] args) throws FileNotFoundException  {
		
		Scanner s2 = new Scanner(new File("undantagsord.txt"));
		s2.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); 

		Set<String> stop = new HashSet<>();

		while (s2.hasNext()) {
			stop.add(s2.next());
		}
		
		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); 
		
		GeneralWordCounter count = new GeneralWordCounter(stop);
		
		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			count.process(word);
		}
		
		BookReaderController control = new BookReaderController(count);
	}

}
