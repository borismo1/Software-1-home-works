package il.ac.tau.cs.sw1.polynomials;

import java.util.Arrays;

import jdk.nashorn.internal.ir.LiteralNode.ArrayLiteralNode.ArrayUnit;

public class Polynomial {

	/**
	 * Creates a polynomial with (safe copies of) the given monomials
	 * 
	 * @pre monomials != null
	 * @pre for all i, 0 <= i < monmials.length : monomials[i] != null
	 * @post for all i, 0 <= i < monmials.length : monomials[i].getCoefficient()
	 *       == getMonomial(i).getCoefficient()
	 * @post for all i,v, 0 <= i < monmials.length, 'a'<=v<='z' :
	 *       monomials[i].getDegree(v) == getMonomial(i).getDegree(v)
	 */
	
	private Monomial[] masterlist;
	
	public Polynomial(Monomial[] monomials) {
		this.masterlist = Arrays.copyOf(monomials, monomials.length);
	}

	/**
	 * @return the number of monomials in this polynomial
	 */
	public int getMonomialCount() {
		return this.masterlist.length;
	}

	/**
	 * @pre 0<=index < getMonomialCount()
	 * @return a safe copy of the monomial at the given index
	 */
	public Monomial getMonomial(int index) {
		return this.masterlist[index - 1].getCopy();
	}

	/**
	 * @return a polynomial identical to this Polynomial but with inverse
	 *         coefficient signs
	 */
	public Polynomial inverse(){
		Monomial[] temp = new Monomial[this.masterlist.length];
		for(int i=0 ;i< temp.length;i++){
			temp[i] = this.masterlist[i].getCopy();
			temp[i].setCoefficient(temp[i].getCoefficient() * -1);
		}
		return new Polynomial(temp);
	}

	/**
	 * 
	 * @pre other != null
	 * @post Creates a new Polynomial which is the sum of this polynomial and
	 *       other. E.g., the sum of 13b^2x^3z+15 and -4b^2x^3z is
	 *       13b^2x^3z+15-4b^2x^3z
	 */
	public Polynomial add(Polynomial other) {
		Monomial[] M_this = Arrays.copyOf(this.masterlist, this.masterlist.length);
		Monomial[] M_other = Arrays.copyOf(other.masterlist, other.masterlist.length);
		Monomial[] this_other = new Monomial[M_this.length + M_other.length];
		System.arraycopy(M_this, 0, this_other, 0, M_this.length);
		System.arraycopy(M_other, 0, this_other, M_this.length, M_other.length);
		return new Polynomial(this_other);
	}

	/**
	 * @pre assignment != null
	 * @pre assignmnet.length == 26
	 * @return the result of assigning assignment[0] to a, assignment[1] to b
	 *         etc., and computing the value of this Polynomial
	 */
	public int computeValue(int[] assignment) {
		int output = 0;
		for(int i =0; i< this.masterlist.length;i++){
			output = output + this.masterlist[i].compute_monomial(assignment);
		}
		return output;
	}

	/**
	 * @post Sums together all the monomials of the same degrees in this
	 *       polynomial e.g., 13b^2x^3z+15-4b^2x^3z --> 9b^2x^3z+15
	 */
	public void normalize() {
		Monomial[] normal_pol = new Monomial[this.number_of_uniq_monomials()];
		normal_pol[0] = this.masterlist[0];
		int counter = 1;
		for(int i = 1;i< this.masterlist.length;i++){
			for(int j =0; j < counter;j++){
				if(normal_pol[j].is_same_vars(this.masterlist[i]) && j!= counter){
					normal_pol[j] = normal_pol[j].noraml_sum(this.masterlist[i]);
					break;
				}
				if(i == counter){
					normal_pol[counter] = this.masterlist[i];
					counter++;
					break;
				}
			}
		}
		this.masterlist =normal_pol;
	}
	
	public int number_of_uniq_monomials(){
		int counter =0;
		for(int i=0;i< this.masterlist.length;i++){
			for(int j=i + 1;j<this.masterlist.length;j++){
				if(this.masterlist[i].equals(this.masterlist[j])){
					
					break;
				}
				if(j == this.masterlist.length -1){
					counter++;
				}
			}
		}
		return counter;
	}
	

	/**
	 * Returns a string representation of this polynomial, of the form
	 * 13b^2x^3z+15-4b^2x^3z I.e., each monomial is printed according to
	 * Monomial.toString(), and the monomials are connected by + and - depending
	 * on their coefficient sign
	 */
	public String toString() {
		String output = this.masterlist[0].toString();
		for(int i=1;i<this.masterlist.length;i++){
			if(Integer.signum(this.masterlist[i].getCoefficient()) == 1){
				output = output  + '+' + this.masterlist[i].toString();
			}
			else{
				output = output  + this.masterlist[i].toString();
					}
		}
		return output;
	}
}
