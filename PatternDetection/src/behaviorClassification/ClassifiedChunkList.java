package behaviorClassification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Provides a class to store chunks of data
 * @author nathandunn
 *
 */
public class ClassifiedChunkList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, ArrayList<ClassifiedChunk>> chunkList;
	private ArrayList<String> quantities;
	//private String[] quantities;
//	private int numChunks;				//TODO Remove?
	
	
	public ClassifiedChunkList(){
		chunkList = new HashMap<String, ArrayList<ClassifiedChunk>>();
		quantities = new ArrayList<String>();
	}
	
	public void addList(String header, ArrayList<ClassifiedChunk> list){
		chunkList.put(header, list);
		quantities.add(header);
	}

	public String[] getQuantities(){
		return quantities.toArray( new String[quantities.size()] );
	}
	
//	public ClassifiedChunkList(RawDataTable table, int numChunks){
//		this(table, table.timeSpan()/numChunks);
//	}
//	
//	public ClassifiedChunkList(RawDataTable table, double chunkWidth){
//		double[] times = table.getTimes();
//		double start = times[0];
//		double end = times[times.length-1];
//		double[] partition = Partitioner.seq(start, end, chunkWidth);
//	
//		int[][] inds = Partitioner.partitionTimes(times, partition);
//		double[][] timeChunks = Partitioner.indexVals(inds, times);
//		numChunks = inds.length;
//		
//		quantities = table.getNonTimeHeaders();
//		for (String s : quantities){
//			ArrayList<Chunk> chunks = new ArrayList<Chunk>();
//			double[][] vals = Partitioner.indexVals(inds, table.getCol(s));
//			for (int i=0; i<vals.length; i++){
//				chunks.add(new Chunk(vals[i], timeChunks[i], 
//						partition[i], partition[i+1]));
//			}
//			chunkList.put(s, chunks);
//		}
//	}
	
	public ArrayList<ClassifiedChunk> getClassifiedChunks(String header){
		return chunkList.get(header);
	}
	
	public int getNumChunks() {
		int sum = 0;
		for (String s : quantities){
			sum += getClassifiedChunks(s).size();
		}
		return sum;
	}
	
	public String toString(){
		String out = "";
		for (String s : quantities){
			out += s + "\n";
			out += this.getClassifiedChunks(s);
			out += "\n";
		}
		return out;
	}

}