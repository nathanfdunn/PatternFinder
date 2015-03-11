package behaviorClassification;

import java.util.ArrayList;
import java.util.Random;

public class FeatureTable extends DataTable {
	
	private String[] classifications;
	
	public FeatureTable(double[][] entries, String[] headers, String[] classifications){
		super(entries, headers);
		this.classifications = classifications;
		if (this.getNumRows() != classifications.length)
			throw new Error("Incorrect number of classifications");
	}
	
//	//TODO: remove?
//	public FeatureTable(double[][] entries, String[] headers){
//		this(entries, headers, null);
//	}
	
	
	
	
//	public FeatureTable(double[][] entries) {
//		this(entries, null);
//	}
	


	public FeatureTable(ClassifiedChunkList list, FeatureExtractor fe){
		super( new double[ fe.numFeatures() ][ list.getNumChunks() ],
				fe.featureNames() );

		ArrayList<String> classifications = new ArrayList<String>();
		int ind = 0;
		for (String s : list.getQuantities()){
			for (ClassifiedChunk cc : list.getClassifiedChunks(s)){
				this.setRow( fe.extractFeatures(cc.getRawChunk()), ind);
				classifications.add( cc.getClassification() );
				ind++;
			}
		}
		this.classifications = 
				classifications.toArray(new String[classifications.size()]);
	}
	
	
//	public FeatureTable appendTables(FeatureTable ft1, FeatureTable ft2){
//		//check headers are equal
//	}
	

//	public void setClassifications(String[] classifications) {
//		this.classifications = classifications;
//	}
	
	public String[] getClassifications() {
		return classifications;
	}

	//Returns a vector of means for each data column
	public double[] getMeanVec(){
		double[] out = new double[super.getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.mean(entries[i]);
		return out;
	}
	
	//Returns a vector of standard deviations for each data column
	public double[] getStdVec(){
		double[] out = new double[getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.std(entries[i]);
		return out;
	}
	
	//Creates a version of this table where each column is normalized
	public FeatureTable getScaledTable(){
		double[][] newEntries = new double[getNumCols()][getNumRows()];
		for (int i=0; i<getNumCols(); i++)
			newEntries[i] = MyMath.scale(this.entries[i]);
		
		return new FeatureTable(newEntries, this.getHeaders(), this.classifications);
	}
	
	protected void swapRows( int ind1, int ind2 ){
		String temp = classifications[ind1];
		classifications[ind1] = classifications[ind2];
		classifications[ind2] = temp;
		super.swapRows(ind1, ind2);
	}
	
	/**
	 * Randomly shuffles the rows in the table 
	 *  (all permutations equally likely)
	 */
	public void shuffle(){
		Random rng = new Random();
		int n = this.getNumRows();
		for (int i=0; i<n; i++){
			int j = i + rng.nextInt(n-i + 1);
			this.swapRows(i, j);
		}
	}

	
}
