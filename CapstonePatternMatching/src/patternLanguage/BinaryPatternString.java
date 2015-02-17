package patternLanguage;

/**
 * 
 * @author nathandunn
 * Provides an object to represent a string in the pattern language
 *  that is composed of two base clauses and a time window
 */
public class BinaryPatternString {
	private CompositeTokenClause precursor;
	private CompositeTokenClause successor;
	private Interval timeWindow;
	
	public BinaryPatternString(CompositeTokenClause precursor,
			CompositeTokenClause successor, Interval timeWindow){
		this.precursor = precursor;
		this.successor = successor;
		this.timeWindow = timeWindow;
	
	}

	public CompositeTokenClause getPrecursor() {
		return precursor;
	}


	public CompositeTokenClause getSuccessor() {
		return successor;
	}


	public Interval getTimeWindow() {
		return timeWindow;
	}
	
	public String toString(){
		return precursor + " ~ " + successor + " : " + timeWindow;
	}
}
