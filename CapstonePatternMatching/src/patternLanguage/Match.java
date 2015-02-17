package patternLanguage;


/**
 * 
 * @author nathandunn
 * Provides an object to hold tokens in a token stream that matched a pattern
 * Can be used to represent anti-match as well if successor is null
 */
public class Match {
	
	private CompositeToken precursorToken;
	private CompositeToken successorToken;
	
	//An indetminate match object
	public static final Match INDET_MATCH = new Match(null, null);
	
	public static Match createAntiMatch(CompositeToken precursorToken){
		return new Match(precursorToken, null);
	}
	
	public Match(CompositeToken precursorToken, CompositeToken successorToken){
		this.precursorToken = precursorToken;
		this.successorToken = successorToken;
	}
	
	public CompositeToken getPrecursor(){
		return precursorToken;
	}

	public CompositeToken getSuccessor(){
		return successorToken;
	}
	
	public boolean isAntiMatch(){
		return successorToken == null;
	}
	
	public String toString(){
		if (this == INDET_MATCH){
			return "Indeterminate Match";
		}
		String sucStr = successorToken==null? "<none>" : successorToken.toString();
		return "["+precursorToken+", " + sucStr + "]";
	}
	
	
}
