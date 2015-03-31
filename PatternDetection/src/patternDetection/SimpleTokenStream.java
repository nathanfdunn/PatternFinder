package patternDetection;

import java.util.ArrayList;
import java.util.HashMap;



public class SimpleTokenStream {
	public HashMap<String, TokenList> stream;
	private double[] partition;
	
	public SimpleTokenStream(){
		this(null);
	}
	
	
	public double[] getPartition() {
		return partition;
	}


	public SimpleTokenStream(double[] partition){
		stream = new HashMap<String, TokenList>();
		this.partition = partition;
	}
	
	public void add( String quantID, ArrayList<SimpleToken> tokens ){
		stream.put(quantID, new TokenList(tokens));
	}
	
	public ArrayList<String> quantities(){
		return new ArrayList<String>(stream.keySet());
	}
	
	public ArrayList<SimpleToken> filter(Behavior b, String quantID){
		return stream.get(quantID).filter(b);
	}
	
	public ArrayList<SimpleToken> filter(SimpleClause c){
		return stream.get(c.quantID).filter(c.behavior);
	}
	
	public ArrayList<SimpleToken> getQuant(String quantID){
		return stream.get(quantID).tokens;
	}
	
	public String toString(){
		ArrayList<String> quantIDs = quantities();
		int len = length();
		String out = "   ";
		for (String q : quantIDs){
			out += q + " ";
		}
		out += "\n";
		for (int i=0; i<len; i++){
			String layer = "(   ";
			for (String q : quantIDs){
				SimpleToken temp = getQuant(q).get(i);
				layer += temp.behavior.toString() + "   ";
				//out += getQuant(q).get(i);
			}
			layer += ")" + i + "\n";
			out += layer;
			//out += "\n";
		}
		return out;
	}
	
	/**
	 * Returns the number of tokens in the stream
	 * @return
	 */
	public int length(){
		return stream.entrySet().iterator().next().getValue().tokens.size();
	}
	
	public static class TokenList {
		public ArrayList<SimpleToken> tokens;
		
		public TokenList(ArrayList<SimpleToken> tokens) {
			this.tokens = tokens;
		}

		public ArrayList<SimpleToken> filter(Behavior b){
			ArrayList<SimpleToken> out = new ArrayList<SimpleToken>();
			for (SimpleToken t : tokens){
				if (t.behavior == b) out.add(t); 
			}
			//System.out.println("Reminder: fix filter");		TODO
			return out;
		}
		
		
		
		
	}
}
