package il.ac.tau.cs.sw1.searchengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

/**
 * <code>SearchEngine</code> builds an index of words given a list of predefined
 * URLs. It then will run searches based on user's input.
 */
public class SearchEngine {

	/**
	 * a list of URLs we wish to index. feel free to add/remove URLs from this
	 * list.
	 */
	static private String[] strURLs = {
			"http://en.wikipedia.org/wiki/Java_(programming_language)",
			"http://en.wikipedia.org/wiki/Java",
			"http://docs.oracle.com/javase/8/docs/technotes/guides/collections/reference.html",
			"http://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html",
			"http://en.wikipedia.org/wiki/United_States",
			"http://en.wikipedia.org/wiki/World_War_II",
			"http://en.wikipedia.org/wiki/World_War_I"
	};

	private WordIndex indexer; // reference to a WordIndex instance

	/**
	 * Create a new SearchEngine
	 * 
	 * @param index
	 *            - An instance implementing the {@link WordIndex} interface.
	 */
	public SearchEngine(WordIndex index) {
		this.indexer = index;
	}

	/**
	 * Populate the index and run searches
	 */
	public void run() {
		indexURLs(indexer);
		doSearches(indexer);
	}

	private static void indexURLs(WordIndex indexer) {
		for (String strURL : strURLs) {
			System.out.print("Indexing \"" + strURL + "\" ... ");
			Collection<String> tokens = HTMLTokenizer.getTokens(strURL);
			indexer.index(tokens, strURL);
			System.out.println("found " + tokens.size() + " tokens.");
		}
	}

	private static void doSearches(WordIndex indexer) {

		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(System.in));
			String str = null;
			do {
				System.out.print("> ");
				str = in.readLine().trim();
				if (str.equals("(exit)")) {
					break;
				}

				List<String> results = null;
				if (str.endsWith("*")) {
					System.out
							.println("Searching for pages containing the prefix \""
									+ str + "\"...");
					// looking for pages containing the given prefix
					results = indexer.searchPrefix(str.substring(0,
							str.lastIndexOf('*')));
				} else {
					System.out
							.println("Searching for pages containing the word \""
									+ str + "\"...");
					// looking for pages containing the exact word
					results = indexer.searchExact(str);
				}
				if (results == null || results.size() == 0) {
					System.out
							.println("\tUnable to find relevant pages.");
				} else {
					for (int i = 0; i < results.size(); ++i) {
						System.out.println(i + 1 + ".\t"
								+ results.get(i));
					}
				}

			} while (true);

		} catch (IOException e) {
		}

		System.out.println("Bye!");
	}

}
