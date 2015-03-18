package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Provides a class to store chunks of data grouped by the quantity that
 *  is represented
 * @author nathandunn
 *
 */
public class ChunkList {

	private HashMap<String, ArrayList<Chunk>> chunkList;
	private String[] quantities;
	private int numChunks;			
	private double[] partition;

	/**
	 * Breaks the information in the table into numChunks equi-time-spaced
	 *  chunks
	 * @param table
	 * @param numChunks
	 */
	public ChunkList(RawTimeSeriesTable table, int numChunks){
		this(table, table.timeSpan()/numChunks);
	}
	
	/**
	 * Breaks the information in the table into Chunks whose time spans
	 *  are all (with the possible exception of the last chunk) equal
	 *  to chunkWidth time units
	 * @param table
	 * @param chunkWidth
	 */
	public ChunkList(RawTimeSeriesTable table, double chunkWidth){
		chunkList = new HashMap<String, ArrayList<Chunk>>();
		double[] times = table.getTimes();
		double start = table.getFirstTime();
		double end = table.getLastTime();
		//double[] partition 
		partition = Partitioner.seq(start, end, chunkWidth);
	
		int[][] inds = Partitioner.partitionTimes(times, partition);
		double[][] timeChunks = Partitioner.indexVals(inds, times);
		numChunks = inds.length;
		
		quantities = table.getNonTimeHeaders();
		for (String quant : quantities){
			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
			double[][] vals = Partitioner.indexVals(inds, table.getCol(quant));
			for (int i=0; i<vals.length; i++){
				chunks.add(new Chunk(vals[i], timeChunks[i], 
						partition[i], partition[i+1]));
			}
			chunkList.put(quant, chunks);
		}
	}
	
	public double[] getPartition() {
		return partition;
	}

	/**
	 * Returns a list of Chunks associated with the specified quantity 
	 *  (SO4, etc.)
	 * @param quant
	 * @return
	 */
	public ArrayList<Chunk> getChunks(String quant){
		return chunkList.get(quant);
	}
	
	/**
	 * @return an array of Strings describing the quantities whose values
	 *  are stored in the chunks
	 */
	public String[] getQuantities() {
		return quantities;
	}
	
	public int getNumChunks() {
		return numChunks;
	}
	
	public String toString(){
		String out = "";
		for (String s : quantities){
			out += s + "\n";
			out += this.getChunks(s);
		}
		return out;
	}

}
