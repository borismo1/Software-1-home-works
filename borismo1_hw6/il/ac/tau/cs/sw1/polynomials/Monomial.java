package il.ac.tau.cs.sw1.polynomials;

import java.util.Arrays;

/**
 * Represents a multiplication of variables in a-z with an integral coefficient
 */
public class Monomial {

	/**
	 * @post this.getCoefficient() == coefficient
	 * @post for every v, 'a'<=v<='z': getDegree(v) == 0
	 */
	
	private int coeffiient;
	
	private int[] Degree;
	
	
	public Monomial(int coefficient) {
		this.coeffiient = coefficient;
		this.Degree = new int[26];
	}

	/**
	 * @return the coefficient of this monomial
	 */
	public int getCoefficient() {
		return this.coeffiient;
	}

	/**
	 * @post getCoefficient() == coefficient
	 */
	public void setCoefficient(int coefficient) {
		this.coeffiient = coefficient;
	}

	/**
	 * @return the degree of variable in this monomial
	 */
	public int getDegree(char variable) {
		return this.Degree[(int) variable -97];
	}
	
	public int[] Degree_array(){
		return Arrays.copyOf(this.Degree, this.Degree.length);
	}
	

	/**
	 * @pre 'a'<=variable<='z'
	 * @pre degree >= 0
	 * @post getDegree(variable) = degree
	 */
	public void setDegree(char variable, int degree) {
		this.Degree[(int) variable - 97] = degree;
	}

	/**
	 * @pre other!= null
	 * @return false iff there exists v, 'a'<=v<='z', getDegree(v) !=
	 *         other.getDegree(v), and true otherwise
	 */
	public boolean hasSameDegrees(Monomial other) {
		if(Arrays.equals(this.Degree, other.Degree)){
			return true;
		}
		else{return false;}
	}

	/**
	 * Returns a "safe" copy of this monomial, i.e., if the copy is changed,
	 * this will not change and vice versa
	 */
	public Monomial getCopy() {
		Monomial copy = new Monomial(this.getCoefficient());
		copy.Degree = Arrays.copyOf(this.Degree,this.Degree.length);
		return copy;
	}
	/**
	 * @pre this.active_vars() == other.active_vars()
	 * @param other
	 * @return normal sum of this + other in a new Monomial
	 */
	public Monomial noraml_sum(Monomial other){
		Monomial output = new Monomial(this.coeffiient + other.coeffiient);
		output.Degree = Arrays.copyOf(this.Degree,this.Degree.length);
		return output;
	}

	
	public int compute_monomial(int[] input){
		int sum =1;
		for(int i=0;i<26;i++){
			if(this.Degree[i] != 0 && input[i] != 0){
				sum = (int) (sum*(Math.pow(input[i], this.Degree[i])));
			}
		}
		return sum * this.coeffiient;
	}
	
	
	public int[] active_vars(){
		int[] output = new int[this.num_of_active_vars()];
		int counter =0;
		for(int i=0;i<26;i++){
			if(this.Degree[i] != 0){
				output[counter] = i;
				counter++;
			}
		}
		return output;
	}
	
	public boolean is_same_vars(Monomial other){
		if(Arrays.equals(this.active_vars(),other.active_vars())){
			return true;
		}
		return false;
	}
	
	public int num_of_active_vars(){
		int output =0;
		for(int i=0;i<26;i++){
			if (this.Degree[i] != 0){
				output++;
			}
		}
		return output;
	}
	
	/**
	 * Returns a string representation of this monomial, of the form 13b^2x^3z
	 * I.e., the coefficient is first (if not 1), then every variable with
	 * degree>0, followed by ^ and its degree
	 */
	public String toString() {
		String output = new String();
		for(int i =0 ;i< 26;i++){
			if(this.Degree[i] != 0){
				if(this.Degree[i] == 1){
					output = output + Character.toString((char) (i + 97));
				}
				else{
					output = output + Character.toString((char) (i + 97)) + "^" + this.Degree[i];
				}
			}
		}
		output = Integer.toString(this.coeffiient) + output;
		return output;
	}
}
