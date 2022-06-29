package textproc;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class GeneralWordCounter implements TextProcessor {
	private Set<String> stop;
	private Map<String, Integer> word;

	public GeneralWordCounter(Set<String> stop) {
		this.stop = stop;
		word = new TreeMap<>();
	}

	/**
	* Anropas när ett ord lästs in.
	* Metoden ska uppdatera statistiken därefter.
	*/
	public void process(String w) {
		if (!stop.contains(w)) {
			word.put(w, (word.getOrDefault(w, 0)) + 1);
		}
	}
	
	/**
	* Anropas när samtliga ord i sekvensen lästs in.
	* Metoden ska skriva ut en sammanställning av statistiken.
	*/
	public void report() {
		// for(String w : word.keySet()) {
		// int count = word.get(w);

		// if(count > 200) {
		// System.out.println(w + ": " + word.get(w));
		// }
		Set<Map.Entry<String, Integer>> wordSet = word.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		// System.out.println(wordList);
		wordList.sort(new WordCountComparator());

		for (int i = 0; i < 25; i++) {
			System.out.println(wordList.get(i));
		}

	}
	
	public List<Map.Entry<String, Integer>> getWordList() {
		return new ArrayList<>(word.entrySet());
	}
}
