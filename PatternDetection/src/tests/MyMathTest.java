package tests;

import behaviorClassification.MyMath;

public class MyMathTest {

	public static void main(String[] args) {
		
		double[] zero = new double[]{0,0};
		double[] vec = new double[] {3,4};
		Pnt.pnt(MyMath.distance(zero, vec));
		Pnt.pnt(MyMath.distance(MyMath.add(vec, vec), vec));
		Pnt.pnt(MyMath.distance(MyMath.mult(vec, 2), vec));
		Pnt.pnt(MyMath.distance(MyMath.mult(vec, 3), zero));
	}
	
	
	public static boolean doubEq(double d1, double d2){
		final double epsilon = 0.00000001;
		return Math.abs(d1-d2) < epsilon;
	}
}
