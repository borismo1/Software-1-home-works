package il.ac.tau.cs.sw1.polynomials;

import il.ac.tau.cs.sw1.util.ErrorHelper;

public class TestPolynomials {
	private static final int VALUE = 10140;

	public static void main(String[] args) {
		// 13b^2x^3z
		Monomial m1 = new Monomial(13);
		m1.setDegree('b', 2);
		m1.setDegree('x', 3);
		m1.setDegree('z', 1);
		System.out.println("m1 = " + m1);

		// 15
		Monomial m2 = new Monomial(15);
		System.out.println("m2 = " + m2);

		// -4b^2x^3z
		Monomial m3 = new Monomial(-4);
		m3.setDegree('b', 2);
		m3.setDegree('x', 3);
		m3.setDegree('z', 1);
		System.out.println("m3 = " + m3);
		
		// 2b^2d^2f^2h^2
		Monomial m4 = new Monomial(2);
		m4.setDegree('b', 2);
		m4.setDegree('d', 2);
		m4.setDegree('f', 2);
		m4.setDegree('h', 2);
		System.out.println("m4 = " + m4);
		ErrorHelper.assertUnrecoverable(!m1.hasSameDegrees(m2), m1
				+ " does not have the same degrees as " + m2);
		ErrorHelper.assertUnrecoverable(m1.hasSameDegrees(m3), m1
				+ " has the same degrees as " + m3);
		ErrorHelper.assertUnrecoverable(m3.hasSameDegrees(m1), m3
				+ " does not have the same degrees as " + m1);

		Polynomial p = new Polynomial(new Monomial[] { m1, m2 });
		Polynomial p2 = new Polynomial(new Monomial[] { m3 });
		Polynomial p3 = p.add(p2);
		System.out.println("p = " + p); // should be 13b^2x^3z+15
		System.out.println("p2= " + p2); // should be -4b^2x^3z
		System.out.println("p3= " + p3); // should be 13b^2x^3z+15-4b^2x^3z up
											// to reordering the monomials
		System.out.println("-p3= " + p3.inverse()); // should be
													// -13b^2x^3z-15+4b^2x^3z up
													// to reordering the
													// monomials
		int[] assignment = { 0, 1, 6, 4, 3, 0, 0, 2, 3, 5, 2, 6, 3,
				8, 7, 0, 0, 4, 2, 6, 0, 4, 1, 5, 1, 9 };
		ErrorHelper.assertUnrecoverable(
				p3.computeValue(assignment) == VALUE,
				"p3 value for b=1, x=5 and z=9  should be " + VALUE);
		System.out.println("p3 value = "
				+ p3.computeValue(assignment));

		p3.normalize();
		System.out.println("p3= " + p3); // should be 9b^2x^3z+15 up to
											// reordering the monomials
		ErrorHelper.assertUnrecoverable(
				p3.computeValue(assignment) == VALUE,
				"after normalization, p3 value for b=1, x=5 and z=9 should still be "
						+ VALUE);
	}

}
