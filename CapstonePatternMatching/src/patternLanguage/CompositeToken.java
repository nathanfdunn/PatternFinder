package patternLanguage;

import java.util.ArrayList;


/**
 * 
 * @author nathandunn
 * An object representing all features of an ice core over a particular
 *  time interval
 */
public class CompositeToken {
	private TimeAttribute timeAttr;
	private ArrayList<QuantityToken> quantityTokens;

	
	public CompositeToken(TimeAttribute time, ArrayList<QuantityToken> qt){
		this.timeAttr = time;
		this.quantityTokens = qt;
	}
	/*
	public void addToken( QuantityToken token){
		quantityTokens.add(token);
	}*/
	
	public ArrayList<QuantityToken> getQuantityTokens() {
		return quantityTokens;
	}

	public void setQuantityTokens(ArrayList<QuantityToken> quantityTokens) {
		this.quantityTokens = quantityTokens;
	}

	public TimeAttribute getTimeAttr() {
		return timeAttr;
	}

	public void setTimeAttr(TimeAttribute timeAttr) {
		this.timeAttr = timeAttr;
	}
	
	
	public String toString(){
		String out = "["+timeAttr.getStart()+" , "+timeAttr.getEnd()+"] ";
		for (QuantityToken qt : quantityTokens)
			out+= "("+qt.toString()+")";		
		return out;
	}
	
}
