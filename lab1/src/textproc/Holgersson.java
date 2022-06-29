package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
		long t0 = System.nanoTime();
		Scanner s2 = new Scanner(new File("undantagsord.txt"));
		s2.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		Set<String> stop = new HashSet<>();

		while (s2.hasNext()) {
			stop.add(s2.next());
		}
		
		ArrayList<TextProcessor> tp = new ArrayList<TextProcessor>();
		tp.add(new SingleWordCounter("nils"));
		tp.add(new SingleWordCounter("norge"));
		tp.add(new MultiWordCounter(REGIONS));
		tp.add(new GeneralWordCounter(stop));

		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			for (TextProcessor p : tp) {
				p.process(word);
			}
		}

		for (TextProcessor p : tp) {
			p.report();
		}

		long t1 = System.nanoTime();
		System.out.println("tid: " + (t1 - t0) / 1000000.0 + " ms");
		// Median: 732.5476 ms
		// Median med tree: 965.9834 ms (lite längre tid, antagligen för att regioner sorteras?)
	}
}