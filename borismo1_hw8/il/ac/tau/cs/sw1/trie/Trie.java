package il.ac.tau.cs.sw1.trie;

import java.util.Set;

/**
 * Represents a generic Trie data structure, where K is the type of keys that
 * are saved in the Trie, and V is the type of values that can be saved for each
 * key.
 */
public interface Trie<K, V> {

	/**
	 * Adds the input key to the data structure with the given value. Iff the
	 * key is invalid for this Trie (implementation-dependent) returns false
	 */
	boolean addKey(K key, V value);

	/**
	 * Searches all the keys in the Trie structure with the given prefix. If
	 * there are no such keys or the prefix is invalid
	 * (implementation-dependent) - an empty Set is returned. Otherwise, all the
	 * values saved for these keys are added to the output value set.
	 *
	 * @post the return value is not null
	 */
	Set<V> searchByPrefix(K prefix);

}
