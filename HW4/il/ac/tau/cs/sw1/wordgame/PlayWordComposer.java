package il.ac.tau.cs.sw1.wordgame;

import il.ac.tau.cs.sw1.util.ErrorHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlayWordComposer {

	//returns the words that begins with "firstLetter" contains "secondLetter" and "thirdLetter" overlaps not allowed.  
	
	public static void printWords(String[][] vocabularyByLetter,String firstLetter, String secondLetter, String thirdLetter){
		char char1 = firstLetter.charAt(0);
		char char2 = secondLetter.charAt(0);
		char char3 = thirdLetter.charAt(0);
		int counter =0;
		for(String i: vocabularyByLetter[(int) char1 - 97]){
			if(i.indexOf(char2) != -1 && i.indexOf(char3,i.indexOf(char2)+1) != -1){
				System.out.println(i);
				counter++;
			}
		}
		System.out.println("found " + counter + " words");
	}

	
	// gets user input in case only the file path was provided (uses toy method ErrorHelper provided for this HW)
	
	public static String[] user_input(){
		Scanner new_input = new Scanner(System.in);
		System.out.println("Enter 3 letters or \"exit\" ");
		String[] letters = new_input.nextLine().split(" ");
		if(letters[0].equals("exit")){System.exit(1);}
		if(letters.length != 3){
			ErrorHelper.assertRecoverable(letters.length != 3, "Expecting 3 letters");
			user_input();
		}
		for(String i:letters){
			if(i.length() > 1){
				ErrorHelper.assertRecoverable(i.length() < 1, "Expected a letter but got:" + i);
				user_input();
			}
			if((int) i.charAt(0) < 97 || (int) i.charAt(0) > 122){
				ErrorHelper.assertRecoverable( (int) i.charAt(0) > 96 && (int) i.charAt(0) < 123, "Whats Wrong with you ??? are you \"special\" ? enter 3 LETTERS already not numbers not words LEETTEERRSSSS !!!!");
				user_input();
			}
		}
		new_input.close();
		return letters;
	}
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		String letter1;
		String letter2;
		String letter3;
		if(args.length != 1){
			ErrorHelper.assertUnrecoverable(args.length == 4," Invalid input, please try again ");
			ErrorHelper.assertUnrecoverable((int) args[1].charAt(0) > 96 && (int) args[1].charAt(0) < 123," Invalid input, please try again ");
			ErrorHelper.assertUnrecoverable((int) args[2].charAt(0) > 96 && (int) args[2].charAt(0) < 123," Invalid input, please try again ");
			ErrorHelper.assertUnrecoverable((int) args[3].charAt(0) > 96 && (int) args[3].charAt(0) < 123," Invalid input, please try again ");
			letter1 = args[1];
			letter2 = args[2];
			letter3 = args[3];
		}
		else{
			String[] new_input = user_input();
			letter1 = new_input[0];
			letter2 = new_input[1];
			letter3 = new_input[2];
		}
		Scanner temp = new Scanner(new File(args[0]));
		String[] test1 = Vocabulary.scanVocabulary(temp);
		String[][] test2 = Vocabulary.getVocabularyByLetter(test1);
		printWords(test2, letter1, letter2, letter3);
	}
}
