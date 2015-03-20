package il.ac.tau.cs.sw1.searchengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MyWordIndex implements WordIndex {
	
	private Map<String,Collection<String>> index = new HashMap<String,Collection<String>>();
	
	

	@Override
	public void index(Collection<String> words, String strURL) {
		this.index.put(strURL, tolower_and_clean(HTMLTokenizer.getTokens(strURL)));
	}

	@Override
	public List<String> searchPrefix(String prefix) {
		Map<Double,String> page_rank = new TreeMap<>(Collections.reverseOrder());
		for(String i:this.index.keySet()){
			if(this.matching_prefix_word_count(this.index.get(i), prefix) != 0){
				page_rank.put(prefix_wight(i, prefix), i);
			}
		}
		return new ArrayList<String>(page_rank.values());
	}

	@Override
	public List<String> searchExact(String exact) {
		Map<Double,String> page_rank = new TreeMap<>(Collections.reverseOrder());
		for(String i:index.keySet()){
			if(this.index.get(i).contains(exact)){
				double temp = number_of_matches(this.index.get(i), exact) / this.index.get(i).size();
				page_rank.put(temp, i);
			}
		}
		return new ArrayList<>(page_rank.values());
	}
	
	public double prefix_wight(String site,String prefix){
		double output;
		output = this.matching_prefix_word_count(this.index.get(site), prefix);
		return (output / this.index.get(site).size());
	}
	
	public double matching_prefix_word_count(Collection<String> list,String prefix){
		double output =0;
		List<String> input = (List<String>) list;
		for(int i=0;i< list.size();i++){
			if(input.get(i).startsWith(prefix)){
				output++;
			}
		}
		return output;
	}
	
	private static Collection<String> tolower_and_clean(Collection<String> input){
		Collection<String> output = new ArrayList<>();
		for(String i:input){
			output.add(i.toLowerCase());
		}
		output.removeAll(Collections.singletonList(null));
		output.removeAll(Collections.singletonList(""));
		return output;
	}

	private static double number_of_matches(Collection<String> input,String word){
		int i=0;
		for(String j:input){
			if(j.equals(word)){
				i++;
			}
		}
		return i;
	}
}
