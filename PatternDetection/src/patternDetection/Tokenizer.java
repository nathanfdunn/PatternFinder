package patternDetection;

import behaviorClassification.ChunkList;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;

import java.util.ArrayList;

import behaviorClassification.Chunk;

public class Tokenizer {
	
	private ModelClassifier model;

	public Tokenizer(ModelClassifier model) {
		this.model = model;
	}
	
	
	private ArrayList<SimpleToken> tokenize(ArrayList<Chunk> chunks, String quant){
		ArrayList<SimpleToken> out = new ArrayList<SimpleToken>();
		for (int i=0; i<chunks.size(); i++){
			Chunk chunk = chunks.get(i);
			Behavior b = model.classifyChunkBehavior(chunk);
//			SimpleToken t = new SimpleToken(quant, b, i);	//TODO change back
			SimpleToken t = new SimpleToken(quant, b, i, chunk);

			out.add(t);
		}
		return out;
	}
	
	public SimpleTokenStream tokenize(ChunkList chunkList){
		SimpleTokenStream out = new SimpleTokenStream(chunkList.getPartition());
		for (String quant : chunkList.getQuantities())
			out.add(quant, tokenize(chunkList.getChunks(quant), quant));
		return out;
	}
	
//	TODO at some point will have to specify chunkWidth or numChunks
	
	public SimpleTokenStream tokenize(RawTimeSeriesTable table, double chunkWidth){
		ChunkList cl = new ChunkList(table, chunkWidth);
		return tokenize(cl);
	}
	
	public SimpleTokenStream tokenize(RawTimeSeriesTable table, double chunkWidth, 
			double start, double end){
		ChunkList cl = new ChunkList(table, chunkWidth, start, end);
		return tokenize(cl);
	}
	
	public SimpleTokenStream tokenize(RawTimeSeriesTable table, int numChunks){
		double chunkWidth = table.timeSpan()/numChunks;
		return tokenize(table, chunkWidth);
	}
	
	public SimpleTokenStream tokenize(RawTimeSeriesTable table, int numChunks, 
			double start, double end){
		double chunkWidth = table.timeSpan()/numChunks;
		return tokenize(table, chunkWidth, start, end);		
	}
	
	
	
//	
//	/**
//	 * Tokenizes the contents of the specified csv file
//	 * @param fileName
//	 * @return
//	 */
//	public TokenStream tokenize(String fileName){
//		
//	}

}
