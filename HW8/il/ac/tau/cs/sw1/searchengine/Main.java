package il.ac.tau.cs.sw1.searchengine;

public class Main {

	public static void main(String[] args) {
		SearchEngine searchEngine = new SearchEngine(
				new MyWordIndex());
		searchEngine.run();

	}

}
