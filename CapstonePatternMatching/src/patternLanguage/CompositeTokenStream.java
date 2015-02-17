package patternLanguage;

import java.util.ArrayList;

public class CompositeTokenStream {
	private GeographicAttribute geo;
	private String coreName;
	private ArrayList<CompositeToken> compositeTokens;
	
	public CompositeTokenStream(){
		compositeTokens = new ArrayList<CompositeToken>();
	}
	
	
	public void addToken(CompositeToken token){
		compositeTokens.add(token);
	}
	
	
	public ArrayList<CompositeToken> getTokens(){
		return compositeTokens;
	}
	
	public String toString(){
		String out="";
		for (CompositeToken token : compositeTokens){
			out+=token.toString() + "\n";
		}
		return out;
	}
}
