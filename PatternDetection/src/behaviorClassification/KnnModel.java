package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;

import tests.KnnTest;


public class KnnModel {
	
//	private DataTable trainingData;
//	private String[] trainingClassifications;
//	private double[] meanVec;
//	private double[] stdVec;
//	
//	private String[] classes;
//	private int k = 10;
//	private double gamma = 0;
	
	public FeatureTable trainingData;
	public String[] trainingClassifications;
	public double[] meanVec;
	public double[] stdVec;
	
	public String[] classes;
	public int k = 10;
	public double gamma = 0;
	public double maxWeight = 10;
	
	public void setK(int k) {
		this.k = k;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
	
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public void train(FeatureTable trainingData, String[] classifications){
		if (trainingData.isEmpty())
			throw new Error("Empty Training Data");
		if (trainingData.getNumRows() != classifications.length)
			throw new Error("Incorrect number of classifications");
		
		this.meanVec = trainingData.getMeanVec();
		this.stdVec = trainingData.getStdVec();
		this.trainingClassifications = classifications;
		this.classes = extractClasses(classifications);
		this.trainingData = trainingData.getScaledTable();
	}
	
	//Returns an array containing only the unique strings in classifications
	private String[] extractClasses(String[] classifications){
		ArrayList<String> unique = new ArrayList<String>();
		for (String s : classifications){
			boolean contained = false;
			for (String s2 : unique)
				if (s.equals(s2)) contained = true;
			if (!contained)
				unique.add(s);
		}
		String[] out = new String[unique.size()];
		for (int i=0; i<out.length; i++)
			out[i] = unique.get(i);
		return out;
	}
	
	public String[] classify(FeatureTable newData){
		if ( trainingData == null ) 
			throw new Error("Model hasn't been trained yet");
		//Also check that the DataTables are compatible
		String[] out = new String[newData.getNumRows()];
		for (int i=0; i<out.length; i++){
			out[i] = classify(newData.getRow(i));
		}
		return out;
	}
	
	public String classify(double[] featureVec){
		double[] scaledVec = scaleFeatureVec(featureVec);
		double[] distances = new double[trainingData.getNumRows()];
		for (int i=0; i<distances.length; i++)
			distances[i] = MyMath.distance(scaledVec, trainingData.getRow(i));
		//KnnTest.pntArr(distances); 			//TODO
		int[] inds = MyMath.indsOfLowest(distances, this.k);
		return vote(scaledVec, inds);
	}
	
	private String vote(double[] featureVec, int[] indsOfClosest){
		HashMap<String, Double> votes = new HashMap<String, Double>();
		for (String s : this.classes)
			votes.put(s, 0.0);
		
		for (int i : indsOfClosest){
			double distance = MyMath.distance(featureVec, trainingData.getRow(i));
			double weight = Math.pow(distance, -this.gamma);
			weight = Math.min(weight, this.maxWeight);
			String key = trainingClassifications[i];
			votes.put(key, votes.get(key) + weight);
		}
		//System.out.println(votes);		//TODO
		//Note: no tiebreaking device
		String keyOfHigh = null;
		double highest = Double.NEGATIVE_INFINITY;
		for (String s : this.classes){
			if (votes.get(s) > highest){
				highest = votes.get(s);
				keyOfHigh = s;
			}
		}
		return keyOfHigh;
	}

	
	
	private double[] scaleFeatureVec(double[] featureVec){
		return MyMath.scale(featureVec, meanVec, stdVec);
	}
	
	
}