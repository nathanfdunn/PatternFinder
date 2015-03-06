package behaviorClassification;

import java.util.ArrayList;


public class MyMath {

	public static double mean(double[] data){
		if (data.length <= 0)
			throw new Error("Can't take mean of 0 values");
		double sum=0;
		for (double val : data)
			sum+=val;
		
		return sum/data.length;
	}
	
	public static double variance(double[] data){
		if (data.length <= 1)
			throw new Error("Need at least 2 values for variance");
		double sum = 0;
		double mu = mean(data);
		for (double val : data)
			sum += Math.pow(val-mu, 2);
		
		return sum/(data.length - 1);
	}
	
	public static double std(double[] data){
		return Math.sqrt(variance(data));
	}
	
	//Returns an array of the z-scores of the data
	public static double[] scale(double[] data){
		double mean = mean(data);
		double std = std(data);
		return mult(add(data, -mean), 1/std);
	}

	//TODO explain
	//Returns an array of z-scores with respect to meanVec and stdVec
	public static double[] scale(double[] vec, double[] meanVec, 
			double[] stdVec){
		return mult( sub(vec, meanVec), recip(stdVec)) ;
	}
	
	public static double[] add(double[] data, double a){
		double[] out = new double[data.length];
		for (int i=0; i<data.length; i++)
			out[i] = data[i] + a;
		return out;
	}
	
	public static double[] mult(double[] data, double a){
		double[] out = new double[data.length];
		for (int i=0; i<data.length; i++)
			out[i] = data[i] * a;
		return out;
	}
	
	public static double[] add(double[] vec1, double[] vec2){
		checkDim(vec1, vec2);
		double[] out = new double[vec1.length];
		for (int i=0; i<vec1.length; i++)
			out[i] = vec1[i] + vec2[i];
		return out;
	}
	
	public static double[] sub(double[] vec1, double[] vec2){
		return add(vec1, neg(vec2));
	}
	
	public static double[] mult(double[] vec1, double[] vec2){
		checkDim(vec1, vec2);
		double[] out = new double[vec1.length];
		for (int i=0; i<vec1.length; i++)
			out[i] = vec1[i] * vec2[i];
		return out;
	}
	
	public static double[] recip(double[] vec){
		double[] out = new double[vec.length];
		for (int i=0; i<vec.length; i++)
			out[i] = 1/vec[i];
		return out;
	}
	
	public static double[] neg(double[] vec){
		return mult(vec, -1);
	}
	
	public static double distance(double[] vec1, double[] vec2){
		checkDim(vec1, vec2);
		double sum = 0;
		for (int i=0; i<vec1.length; i++){
			sum+=Math.pow(vec1[i] - vec2[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	private static void checkDim(double[] vec1, double[] vec2){
		if (vec1.length != vec2.length)
			throw new Error("Vectors must be of same length");
	}
	
	
	
	
	
	
	public static int[] indsOfLowest(double[] values, int k){
		//TODO handle this better
		if (values.length == 0) throw new Error("You shouldn't do this");
		if (k >= values.length){
			int[] out = new int[values.length];
			for (int i=0; i<values.length; i++) out[i]=i;
			return out;
		}
		
		ArrayList<Integer> inds = new ArrayList<Integer>();
		
		for (int i=0; i<k; i++){
			double lowest = Double.POSITIVE_INFINITY;
			int lowInd = -1;
			for (int j=0; j<values.length; j++){
				if (!inds.contains(j)){
					if (values[j] < lowest){
						lowInd = j;
						lowest = values[j];
					}
				}
			}
			inds.add(lowInd);
		}
		
		int[] out = new int[k];
		for (int i=0; i<k; i++){
			out[i] = inds.get(i);
		}
		
		return out;
	}
	
	
	public static double getMin(double[] vals){
		//int lowInd = -1;
		double lowest = Double.POSITIVE_INFINITY;
		for (int i=0; i<vals.length; i++){
			if (vals[i] < lowest){
				//lowInd = i;
				lowest = vals[i];
			}
		}
		return lowest;
	}
	
	public static double getMax(double[] vals){
		return -getMin(neg(vals));
	}
	

/*	public static Pair<Double, Integer> getMin(double[] vals){
		int lowInd = -1;
		double lowest = Double.POSITIVE_INFINITY;
		for (int i=0; i<vals.length; i++){
			if (vals[i] < lowest){
				lowInd = i;
				lowest = vals[i];
			}
		}
		return MyMath.Pair<Double, Integer>(lowest, lowInd);
	}
	
	
	
	public static class Pair<T, S>{
		public T obj1;
		public S obj2;
		public Pair(T obj1, S obj2) {
			super();
			this.obj1 = obj1;
			this.obj2 = obj2;
		}
		
		public void blah(){new Pair<Integer, Double>(4,5.0);}
	}
	*/
}