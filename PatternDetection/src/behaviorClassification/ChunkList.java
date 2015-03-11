package behaviorClassification;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Provides a class to store chunks of data
 * @author nathandunn
 *
 */
public class ChunkList {



	private HashMap<String, ArrayList<Chunk>> chunkList;
	private String[] quantities;
	private int numChunks;			
	private double[] times;			//TODO remove?

	public ChunkList(RawDataTable table, int numChunks){
		this(table, table.timeSpan()/numChunks);
	}
	
	public ChunkList(RawDataTable table, double chunkWidth){
		chunkList = new HashMap<String, ArrayList<Chunk>>();
		double[] times = table.getTimes();
		double start = times[0];
		double end = times[times.length-1];
		double[] partition = Partitioner.seq(start, end, chunkWidth);
	
		int[][] inds = Partitioner.partitionTimes(times, partition);
		double[][] timeChunks = Partitioner.indexVals(inds, times);
		numChunks = inds.length;
		this.times = table.getTimes();
		
		quantities = table.getNonTimeHeaders();
		for (String s : quantities){
			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
			double[][] vals = Partitioner.indexVals(inds, table.getCol(s));
			for (int i=0; i<vals.length; i++){
				chunks.add(new Chunk(vals[i], timeChunks[i], 
						partition[i], partition[i+1]));
			}
			chunkList.put(s, chunks);
		}
	}
	
	public ArrayList<Chunk> getChunks(String quantity){
		return chunkList.get(quantity);
	}
	
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
