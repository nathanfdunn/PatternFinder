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


	public ChunkList(RawDataTable table, int numChunks){
		this(table, table.timeSpan()/numChunks);
	}
	
	public ChunkList(RawDataTable table, double chunkWidth){
		double[] times = table.getTimes();
		double start = times[0];
		double end = times[times.length-1];
		double[] partition = Partitioner.seq(start, end, chunkWidth);
	
		int[][] inds = Partitioner.partitionTimes(times, partition);
		double[][] timeChunks = Partitioner.indexVals(inds, times);
		numChunks = inds.length;
		
		quantities = table.getHeaders();
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
	
	public int getNumChunks() {
		return numChunks;
	}

}
