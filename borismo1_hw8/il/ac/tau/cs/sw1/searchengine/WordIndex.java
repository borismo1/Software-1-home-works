package il.ac.tau.cs.sw1.searchengine;

import java.util.Collection;
import java.util.List;

/**
 * A word index allows for a quick search over a large collection of web pages.
 */
public interface WordIndex {

	/**
	 * Add the words originating in the specifies URL.
	 * 
	 * @param words
	 *            - collection of words to add to the index
	 * @param strURL
	 *            - the URL of the page containing the words
	 */
	void index(Collection<String> words, String strURL);

	/**
	 * Search for pages containing a given word prefix in the index
	 * 
	 * @param prefix
	 *            - the word prefix to search
	 * @return A list of pages containing words with the given prefix. The pages are ordered
	 *         according to the relative importance of the prefix within them.
	 */
	List<String> searchPrefix(String prefix);
	
	/**
	 * Search for pages containing a given word in the index
	 * 
	 * @param exact
	 *            - the word to search
	 * @return A list of pages containing the word. The pages are ordered
	 *         according to the relative importance of the word within them.
	 */
	List<String> searchExact(String exact);

}