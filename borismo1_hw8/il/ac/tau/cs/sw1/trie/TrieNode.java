package il.ac.tau.cs.sw1.trie;

import java.util.List;
import java.util.Set;

/**
 * Represents a node in a generic trie, where K is the type of keys that are
 * saved in the trie, and V is the type of values that can be saved for each
 * key.
 */
public interface TrieNode<K, V> {

	/**
	 * Adds the key suffix to this trie node, with the given value, by
	 * recursively adding the suffix of this suffix to the relevant child node.
	 * 
	 * @pre suffix is a valid suffix for this trie (implementation-dependent)
	 * @post if the key ends in this trie node, the input value is added to the
	 *       set of values returned by getValues().
	 */
	void addSuffix(K suffix, V value);


	/**
	 * Return the number of keys that end in this node (with repetitions)
	 */
	int getNumKeys();

	/**
	 * Returns the set of values for keys that end in this node
	 */
	Set<V> getValues();

	/**
	 * Returns the child trie node such that the input suffix is saved in it or
	 * in its descendant
	 * 
	 * @pre suffix is a valid suffix for this trie (implementation-dependent)
	 * @post if no key with such a suffix exists in the trie, or if suffix ends
	 *       in the current node, return null; otherwise return the relevant
	 *       child node
	 */
	TrieNode<K, V> getNext(K suffix);

	/**
	 * Returns a list with all the child trie nodes of this node. The list may
	 * contain null values.
	 * 
	 * @post $ret != null
	 */
	List<TrieNode<K, V>> getNexts();

}
