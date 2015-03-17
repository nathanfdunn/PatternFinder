package patternDetection;

import java.util.ArrayList;
import java.util.HashMap;



public class TokenStream {
	public HashMap<String, TokenList> stream;
	
	public TokenStream(){
		stream = new HashMap<String, TokenList>();
	}
	
	public void add( String quantID, ArrayList<Token> tokens ){
		stream.put(quantID, new TokenList(tokens));
	}
	
	public ArrayList<String> quantities(){
		return new ArrayList<String>(stream.keySet());
	}
	
	public ArrayList<Token> filter(Behavior b, String quantID){
		return stream.get(quantID).filter(b);
	}
	
	public ArrayList<Token> filter(Clause c){
		return stream.get(c.quantID).filter(c.behavior);
	}
	
	public ArrayList<Token> getQuant(String quantID){
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
				Token temp = getQuant(q).get(i);
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
	
	public class TokenList {
		public ArrayList<Token> tokens;
		
		public TokenList(ArrayList<Token> tokens) {
			this.tokens = tokens;
		}

		public ArrayList<Token> filter(Behavior b){
			ArrayList<Token> out = new ArrayList<Token>();
			for (Token t : tokens){
				if (t.behavior == b) out.add(t); 
			}
			//System.out.println("Reminder: fix filter");		TODO
			return out;
		}
		
		
		
		
	}
}