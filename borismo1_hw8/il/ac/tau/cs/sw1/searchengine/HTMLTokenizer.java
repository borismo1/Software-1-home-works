package il.ac.tau.cs.sw1.searchengine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * Retrieves and tokenizes a URL into words
 */
public class HTMLTokenizer {

	/**
	 * Breaks the HTML text given by the URL into tokens.
	 * 
	 * @param strURL
	 *            - The URL of the page we wish to parse
	 * @return a collection of words which appear in the document. each token is
	 *         considered to be a single word
	 */
	public static Collection<String> getTokens(String strURL) {
		try {
			result.clear();
			URL url = new URL(strURL);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream()));

			HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {

				public void handleText(char[] data, int pos) {
					StringTokenizer st = new StringTokenizer(
							new String(data));
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						token = token.replaceAll("^\\P{Alnum}+", "")
								.replaceAll("\\P{Alnum}+$", "")
								.trim();
						if (!token.isEmpty())
							result.add(token);
					}
				}
			};
			new ParserDelegator().parse(reader, callback, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static ArrayList<String> result = new ArrayList<String>();
}
