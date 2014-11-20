import java.util.Arrays;


public class StringUtils {
	
	public static String reverseString(String source){
		char[] temp = new char[source.length()];
		String output;
		for(int i=0 ;i< source.length();i++){
			temp[i] = source.charAt(source.length() - i - 1);
		}
		output = new String(temp);
		return output;
	}
	
	public static String sortStringWords(String str){
		String[] temp = str.split(" ");
		String output = new String();
		Arrays.sort(temp);
		for(String i:temp){
			output = output + i + " ";
		}
		return output;
	}
	
	public static boolean isStringArraySorted(String[] strs, int n){
		for(int i =0;i < strs.length;i++){
			strs[i] = strs[i].toLowerCase();
		}
		for(int j =0;j < strs.length -1;j++){
			if(two_words_compare(strs[j], strs[j+1], n) == false){return false;}
		}
		return true;
	}

	public static String[] convertStringsToAcronyms(String[] input) {
		String[] output = new String[input.length];
		for(int i=0; i< input.length;i++){
			if (input[i] != null){
				if(input[i].length() == 0){
					output[i] = "";
					continue;}
				output[i] = convert_one_String(input[i]);
			}
		}
		return output;
	}
	
	
	public static int[] stringHistogram(String a){
		int[] hist = new int[26];
		for(int i=0; i< a.length() ; i++){
			if((int) a.charAt(i) == 32){continue;}
			hist[(int) a.charAt(i) - 97] = hist[(int) a.charAt(i) - 97] + 1;
		}
		return hist;
	}
	
	public static boolean areAnagrams(String a, String b){
		int[] hist1 = stringHistogram(a.toLowerCase());
		int[] hist2 = stringHistogram(b.toLowerCase());
		for(int i =0;i < hist1.length;i++){
			if(hist1[i] != hist2[i]){return false;}
		}
		return true;
	}
	
	
	
	public static String convert_one_String(String str){
		String output = "";
		String[] temp = str.split(" ");
		if((int) str.charAt(0) > 90){
			return output;
		}
		for(String i:temp){
			if((int) i.charAt(0) > 90){continue;}
			output = output + i.charAt(0);
		}
		return output;
	}
	
	
	public static boolean two_words_compare(String str1,String str2,int n){
		for(int i=0;i<n;i++){
			if((int) str1.charAt(i) > (int) str2.charAt(i)){return false;}
			if((int) str1.charAt(i) < (int) str2.charAt(i)){return true;}
		} 
		return true;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(areAnagrams("Radar","Tartar")  );
	}
}
