package behaviorClassification;

import java.util.ArrayList;


public class MyMath {

	public static int round(double x){
		return (int)Math.round(x);
	}
	public static int[] round(double[] vec){
		int[] out = new int[vec.length];
		for (int i=0; i<vec.length; i++)
			out[i] = round(vec[i]);
		return out;
	}
	
	
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
		return mult( add(data, -mean), 1/std);
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
	
	public static void checkDim(double[] vec1, double[] vec2){
		if (vec1.length != vec2.length)
			throw new Error("Vectors must be of same length");
	}
	
	
	
	
	
	
	public static int[] indsOfLowest(double[] values, int k){
		//TODO handle this better
		//if (values.length == 0) throw new Error("You shouldn't do this");
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
	
	/**
	 * Returns the lowest value in the array
	 * (NaN's are ignored)
	 * @param vals
	 * @return
	 */
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
	
	public static int indOfMin(double[] vals){
		int lowInd = -1;
		double lowest = Double.POSITIVE_INFINITY;
		for (int i=0; i<vals.length; i++){
			if (vals[i] < lowest){
				lowInd = i;
				lowest = vals[i];
			}
		}
		return lowInd;
	}
	
	public static int indOfMax(double[] vals){
		return indOfMin(neg(vals));
	}
	
	
	//Can be replaced with P301 operation
	public static double[][] transpose(double[][] arr){
		//TODO test that it is a square array;
		//@require that array is at least 1xm (or is it mx1?), m >= 0
		int rows = arr.length;
		int cols = arr[0].length;
		double[][] out = new double[cols][rows];
		
		for (int i=0; i<cols; i++)
			for (int j=0; j<rows; j++)
				out[i][j] = arr[j][i];

		return out;

	}
	
	//Calculates the skewness of the sample (3rd standardized moment)
	public static double skewness(double[] data){
		double mean = mean(data);
		double std = std(data);
		double sum = 0;
		for (int i=0; i<data.length; i++)
			sum += Math.pow( data[i] - mean, 3);
		
		return (sum/data.length) / Math.pow(std, 3);
	}
	
	/**
	 * Calculates the maximum z-score of the data
	 * @param data
	 * @return
	 */
	public static double maxZ(double[] data){
		data = scale(data);
		return getMax(data);
	}
	
	/**
	 * Calculates the minimum z-score of the data
	 * @param data
	 * @return
	 */
	public static double minZ(double[] data){
		data = scale(data);
		return getMin(data);
	}
	
	/**
	 * Calculates the coefficient of variation
	 * @param data
	 * @return
	 */
	public static double coefVar(double[] data){
		double mean = mean(data);
		double std = std(data);
		if (mean == 0)
			throw new Error("Coefficient of Variation undefined");
		return std/mean;
	}
	
	/**
	 * Calculates the Pearson product-moment correlation coefficient
	 * @param dataX
	 * @param dataY
	 * @return
	 */
	public static double corrCoef(double[] dataX, double[] dataY){
		checkDim(dataX, dataY);
		double xBar = mean(dataX);
		double yBar = mean(dataY);
		
		double xySum = 0;
		double xxSum = 0;
		double yySum = 0;
		
		for (int i=0; i<dataX.length; i++){
			xySum += (dataX[i] - xBar) * (dataY[i] -yBar);
			xxSum += Math.pow(dataX[i] - xBar, 2);
			yySum += Math.pow(dataY[i] - yBar, 2);
		}
		
		return xySum / Math.sqrt( xxSum * yySum );
	}
	
	/**
	 * Returns the Fisher Transformation of the correlation coefficient
	 * @param r
	 * @return
	 */
	public static double fisherTrans(double r){
		return 0.5 * Math.log( (1+r)/(1-r) );
	}
	
	public static double invFisherTrans(double w){
		double exp2w = Math.exp( 2*w );
		return ( exp2w - 1 )/( exp2w + 1 );
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
