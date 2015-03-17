package patternDetection;

import java.util.ArrayList;

public class DifferenceTable {

	public ArrayList<Token> precursors;
	public ArrayList<Token> successors;
	public int streamLen;			//TODO remove field	//maybe not
	private int[][] diff;
	
	
	public DifferenceTable(ArrayList<Token> precursors, ArrayList<Token> successors,
			int length){
		this.precursors = precursors;
		this.successors = successors;
		this.diff = new int[precursors.size()][successors.size()];
		this.streamLen = length;
		populateTable();
		
	}
	
	private void populateTable(){
		for (int i = 0; i < precursors.size(); i++)
			for (int j = 0; j < successors.size(); j++)
				diff[i][j] = successors.get(j).time - precursors.get(i).time;
	}
	
	
	//TODO rename
	/**
	 * Returns the first Successor whose lag is within the range of the
	 *  specified Precursor
	 * @param precursorIndex
	 * @param interval
	 * @return
	 */
	public Token between(int precursorIndex, Interval interval){
		int[] lags = diff[precursorIndex];
		for (int i=0; i<lags.length; i++){
			if (interval.contains(lags[i]))
				return successors.get(i);
		}
		return null;
	}
	
	//Checks if the values are applicable, given the length of the stream
	public boolean isDeterminate(int precursorIndex, Interval interval){
		int preTime = precursors.get(precursorIndex).time;
		return preTime + interval.high <= streamLen;
	}
	
	/**
	 * The probability that a successor would occur within a time period
	 *  of the specified width due to chance
	 * @param intervalWidth
	 * @return
	 */
	public double calculateRarity(int intervalWidth){
		//TODO take into account Null tokens
		double p = (double)successors.size() / streamLen;
		return 1 - Math.pow( 1-p, intervalWidth + 1);
	}
	
	/*
	public String toString(){
		//TODO
	}
	*/
}
