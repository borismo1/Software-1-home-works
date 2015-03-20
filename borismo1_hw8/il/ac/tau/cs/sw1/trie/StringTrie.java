package il.ac.tau.cs.sw1.trie;

import java.util.HashSet;
import java.util.Set;

public class StringTrie<V> implements Trie<String, V> {
	
	private StringTrieNode<V> trie_root;
	
	 public StringTrie() {
		this.trie_root = new StringTrieNode<>();
	}
	

	@Override
	public boolean addKey(String key, V value) {
		if(key == null){
			return false;
		}
		this.trie_root.addSuffix(key, value);
		return true;
	}

	@Override
	public Set<V> searchByPrefix(String prefix) {
		Set<V> output = new HashSet<V>();
		Set<StringTrieNode<V>> rec_temp = new HashSet<>();
		for(StringTrieNode<V> i:this.trie_root.find_node(prefix).get_offspring(rec_temp)){
			output.addAll(i.getValues());
		}
		return output;
	}
	
	
	
	
	
}
