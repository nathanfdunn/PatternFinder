package behaviorClassification;

import java.util.ArrayList;

/**
 * A class to model a table where each field is a feature and each record
 *  corresponds to a featurized chunk. One of the "fields" is the 
 *  classification of the qualitative behavior of the series
 * @author nathandunn
 *
 */
public class FeatureTable extends DataTable {
	
	private static final long serialVersionUID = 7L;
	private String[] classifications;
	
	public FeatureTable(double[][] entries, String[] headers, String[] classifications){
		super(entries, headers);
		this.classifications = classifications;
		if (this.getNumRows() != classifications.length)
			throw new Error("Incorrect number of classifications");
	}
	
	/**
	 * Creates a FeatureTable where each record corresponds to a Chunk in
	 *  the specified ClassifiedChunkList. Uses the specified FeatureExtractor
	 *  to featurize the chunks.
	 * @param list
	 * @param fe
	 */
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
	
	/**
	 * Returns a copy of this table only including the rows whose
	 *  indicies are in the specified array
	 * @param rowInds
	 * @return
	 */
	public FeatureTable subTable(int[] rowInds){
		double[][] transPosedEntries = new double[rowInds.length][];
		String[] classifications = new String[rowInds.length];
		for (int i=0; i<rowInds.length; i++){
			transPosedEntries[i] = this.getRow(rowInds[i]);
			classifications[i] = this.classifications[ rowInds[i] ];
		}
		double[][] entries = MyMath.transpose(transPosedEntries);
		return new FeatureTable(entries, this.headers, classifications);
	}
	
	public FeatureTable copy(){
		int[] inds = new int[this.getNumRows()];
		for (int i=0; i<inds.length; i++)
			inds[i] = i;
		return subTable(inds);
	}
	
	public String[] getClassifications() {
		return classifications;
	}

	/**
	 * 
	 * @return Returns a vector of means for each data column
	 */
	public double[] getMeanVec(){
		double[] out = new double[super.getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.mean(entries[i]);
		return out;
	}
	
	/**
	 * @return Returns a vector of standard deviations for each data column
	 */
	public double[] getStdVec(){
		double[] out = new double[getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = MyMath.std(entries[i]);
		return out;
	}
	
	/**
	 * @return a copy of this table where each column is normalized
	 */
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
	
	
//	/**
//	 * Uniform randomly permutes all rows in table
//	 */
//	public void shuffle(){
//		Random rng = new Random();
//		int n = this.getNumRows();
//		while (n > 1){
//			int k = rng.nextInt(n--);
//			this.swapRows(k,n);
//		}
//	}
	
	public String toString(){
		String[] lines = super.toString().split("\n");
		lines[0] += "	Classification";
		for (int i=0; i<classifications.length; i++)
			lines[i+1] += classifications[i];

		String out = "";
		for (int i=0; i<lines.length; i++)
			out += lines[i] + "\n";
		return out;
	}	
}
