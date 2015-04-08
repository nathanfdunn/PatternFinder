package patternDetection;

import java.util.ArrayList;

import tests.Objs;
import behaviorClassification.Chunk;
import behaviorClassification.ChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;

public class Tokenizer {
	
 	public static final Tokenizer DEFAULT_TOKENIZER = Objs.createTokenizer();// Objs.tokenizer;
	
	
	private ModelClassifier model;

	public Tokenizer(ModelClassifier model) {
		this.model = model;
	}
	
	
	public ModelClassifier getModel() {
		return model;
	}

	
	public static SimpleTokenStream defaultTokenize(String fileName, int numChunks){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		return DEFAULT_TOKENIZER.tokenize(table, numChunks);
	}
	
	public static SimpleTokenStream defaultTokenize(String fileName, double chunkWidth){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		return DEFAULT_TOKENIZER.tokenize(table, chunkWidth);
	}
	
	
	public static SimpleTokenStream defaultTokenize(String fileName, double chunkWidth,
			double start, double end){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		return DEFAULT_TOKENIZER.tokenize(table, chunkWidth, start, end);
	}
	
	public static SimpleTokenStream defaultTokenize(String fileName, int numChunks,
			double start, double end){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		return DEFAULT_TOKENIZER.tokenize(table, numChunks, start, end);
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
