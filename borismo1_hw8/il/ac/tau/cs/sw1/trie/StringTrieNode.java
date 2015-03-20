package il.ac.tau.cs.sw1.trie;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StringTrieNode<V> implements TrieNode<String, V> {

	// the list of child nodes
	private List<TrieNode<String, V>> nexts = new ArrayList<>();

	// the set of values for the key whose path ends at this node
	private Set<V> values = new LinkedHashSet<>();

	// Number of keys saved at this node
	private int numKeys;

	/**
	 * Initializes the fields of this TrieNode
	 */
	public StringTrieNode() {
		// We create 26 place-holders in the list of child nodes, for each
		// letter in a-z.
		for (int i = 0; i < 26; i++) {
			nexts.add(null);
		}
	}

	@Override
	public void addSuffix(String suffix, V value) {
		suffix.toLowerCase();
		if(suffix.length() == 0){
			this.values.add(value);
			this.numKeys++;
			return;
		}
		if(this.nexts.get((int) suffix.charAt(0) - 97) != null){
			this.numKeys++;
			this.nexts.get((int) suffix.charAt(0) -97).addSuffix(suffix.substring(1),value);
		}
		else{
			this.numKeys++;
			this.nexts.set(((int) suffix.charAt(0) -97),new StringTrieNode<V>());
			this.nexts.get((int) suffix.charAt(0) -97).addSuffix(suffix.substring(1),value);
		}
	}

	@Override
	public Set<V> getValues() {
		return this.values;
	}

	@Override
	public int getNumKeys() {
		return this.numKeys;
	}

	@Override
	public TrieNode<String, V> getNext(String suffix) {
		suffix.toLowerCase();
		if(suffix.length() == 0){
			return this;
		}
		else if(suffix.length() == 1){
			return this.nexts.get((int) suffix.charAt(0) -97);
		}
		else{
			return this.nexts.get((int) suffix.charAt(0) -97).getNext(suffix.substring(1));
		}
		
	}

	@Override
	public List<TrieNode<String, V>> getNexts() {
		return this.nexts;
	}
	
	//will it work ?
	
	
	public Set<StringTrieNode<V>> get_offspring(Set<StringTrieNode<V>> rec_temp){
		rec_temp.add(this);
		for(StringTrieNode<V> i: this.get_childern()){
			i.get_offspring(rec_temp);
		}
		return rec_temp;
	}

	public boolean is_leaf(){
		for(TrieNode<String, V> i: this.nexts){
			if(i != null){
				return false;
			}
		}
		return true;
	}
	
	//return null trie node if not found
	public StringTrieNode<V> find_node(String input){
		input.toLowerCase();
		if(input.length() == 0){
			return this;
		}
		if(this.nexts.get((int) input.charAt(0) -97) == null){
			return new StringTrieNode<>();
		}
		else{
			return ((StringTrieNode<V>) this.nexts.get((int) input.charAt(0) -97)).find_node(input.substring(1));
		}
		}
	
	private Set<StringTrieNode<V>> get_childern(){
		Set<StringTrieNode<V>> childern = new HashSet<>();
		for(int i =0;i<26;i++){
			if(this.nexts.get(i) != null){
				childern.add((StringTrieNode<V>) this.nexts.get(i));
			}
		}
		return childern;
	}
	
	
	
}
