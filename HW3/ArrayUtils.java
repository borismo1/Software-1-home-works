import java.util.Arrays;


public class ArrayUtils {

	public static int[] shiftArrayToTheRight(int[] array){
		int[] output = new int[array.length];
		for(int i =0; i < array.length ; i++){
			output[i] = array[(array.length - 1 + i) % array.length];
		}
		return output;
	}
	
	public static String[] fillStringArray (String[] strs, int[] repeats){
		String[] output = new String[int_array_sum(repeats)];
		int counter =0;
		for(int i = 0 ;i< strs.length;i++){
			for(String j : clone_string(strs[i], repeats[i])){
				output[counter] = j;
				counter++;
			}
		}
		return output;
	}

	public static int[][] transposeMatrix(int[][] matrix){
		int[][] output = new int[matrix[1].length][matrix.length];
		for(int i = 0 ; i < matrix.length;i++){
			for(int j =0;j < matrix[1].length;j++){
				output[j][i] = matrix[i][j]; 
			}
		}
		return output;
	}
	
	public static int[][] splitArrayByNum(int[] input, int number){
		int size = subarrays_num(input, number);
		int[][] output = new int[size][];
		int[][] array_usful_range = array_Range_detector(input, number);
		for(int i=0; i < size; i++){
			output[i] = Arrays.copyOfRange(input, array_usful_range[i][0], array_usful_range[i][1]);
			}
		return output;
		}
	
	
	
	
	
	
	public static int[][] array_Range_detector(int[] input,int number){
		int[][] usfull_range = new int[subarrays_num(input, number)][2];
		int counter = 0;
		for(int i=0;i< input.length;i++){
			if(input[i] != number){
				usfull_range[counter][0] = i;
				if(i == input.length -1){
					usfull_range[counter][1] = i + 1;
					counter++;
					break;
				}
				for(int j = i;j< input.length;j++){
					if(input[j] == number){
						usfull_range[counter][1] = j;
						counter++;
						i = j;
						break;
					}
					if(j == input.length - 1){
						usfull_range[counter][1] = j + 1;
						i = j;
						break;
					}
				}
			}
		}
		return usfull_range;
	}
	
	
	
	
	
	
	public static int subarrays_num(int[] input,int number){
		int output;
		if(input[0] != number){output = 1;}
		else{output = 0;}
		for (int i=0;i < input.length;i++){
			if(input[i] == number && i != input.length -1){
				if(input[i+1] !=number){
					output++;
				}
			}
		}
		return output;
	}
	
	
	public static String[] clone_string(String str,int repets){
		String[] output = new String[repets];
		for (int i = repets ;i > 0;i--){
			output[i -1 ] = str;
		}
		return output;
	}
	
	public static int int_array_sum(int[] repeats){
		int sum = 0;
		for(int i:repeats){
			sum = sum + i;
		}
		return sum;
	}
}
