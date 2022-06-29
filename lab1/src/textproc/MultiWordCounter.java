package textproc;

import java.util.Map;
import java.util.TreeMap;

public class MultiWordCounter implements TextProcessor {
	private Map<String, Integer> ls;

	public MultiWordCounter(String[] landskap) {
		ls = new TreeMap<>();

		for (String w : landskap) {
			ls.put(w, 0);
		}
	}

	public void process(String w) {
		if (ls.containsKey(w)) {
			ls.put(w, ls.get(w) + 1);
		}

	}

	/**
	 * Anropas när samtliga ord i sekvensen lästs in. Metoden ska skriva ut en
	 * sammanställning av statistiken.
	 */
	public void report() {
		for (String w : ls.keySet()) {
			System.out.println(w + ": " + ls.get(w));
		}
	}
}
