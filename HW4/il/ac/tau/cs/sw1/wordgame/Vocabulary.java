package il.ac.tau.cs.sw1.wordgame;

import il.ac.tau.cs.sw1.util.ErrorHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Vocabulary {

	
	//scanVocabulary fills a string array with unique tokens from scanner
	//the output array.length will not exeed 5000
	
	
	public static String[] scanVocabulary(Scanner scanner){
		String[] word_dump = new String[5000];
		String temp;
		int counter =0;
		while (scanner.hasNext()) {
			temp = scanner.next().toLowerCase();
			if (legit_word_test(temp) && counter < 5000  && uniq_item(word_dump, temp)){
				word_dump[counter] = temp;
				counter++;
			}
		}
		return Arrays.copyOfRange(word_dump,0,counter);
	}
	
	//getVocabularyByLetter will split the given string array in to 
	//26 array each with tokens based on the first char
	
	public static String[][] getVocabularyByLetter(String[] vocabulary){
		String[][] output = new String[26][];
		int[] partition_range = get_array_partitions(vocabulary);
		output[0] = Arrays.copyOfRange(vocabulary, 0, partition_range[0]);
		for(int i=1; i< 26;i++){
			output[i] = Arrays.copyOfRange(vocabulary,partition_range[i-1],partition_range[i]);
		}
		return output;
	}

	
	//get_array_partitions returns an array with numbers of words that begin with each letter
	
	public static int[] get_array_partitions(String[] input){
		Arrays.sort(input);
		int[] partition_range = new int[26];
		for(String i:input){
			partition_range[(int) i.charAt(0) - 97]++;
		}
		for(int i=0;i < partition_range.length - 1;i++){
			partition_range[i+1] = partition_range[i+1] + partition_range[i];
		}
		return partition_range;
	}
		
	//legit_word_test returns true if and only if the argument is string composed of English letters (auxiliary method)
	
	public static boolean legit_word_test(String word){
		char[] aux = word.toCharArray();
		for(char i: aux){
			if((int) i < 97 || (int) i > 122){
				return false;
			}
		}
		return true;
	}
		
	//uniq_item will check if the token already exists in the array (auxiliary method) (very ineffective since we are not allowed to use collections)
	
	public static boolean uniq_item(String[] array,String word){
		for(String i:array){
			if(i != null){
				if(i.equals(word)){return false;}}
		}
		return true;
	}
	
	
	//for testing
	
	public static void main(String[] args) throws FileNotFoundException{
		ErrorHelper.assertUnrecoverable(args.length == 1," Invalid input, please try again ");
		Scanner temp = new Scanner(new File(args[0]));
		String[] test = scanVocabulary(temp);
		System.out.println("Read " + test.length + " unique words. \n" );
		System.out.println(Arrays.toString(getVocabularyByLetter(test)[24]));
		temp.close();
	}
	
}

