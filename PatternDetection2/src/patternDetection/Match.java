package patternDetection;

public class Match {
	public Token precursor;
	public Token successor;
	public MatchType type;
	
	
	public Match(Token precursor, Token successor, MatchType type) {
		super();
		this.precursor = precursor;
		this.successor = successor;
		this.type = type;
	}
	
	public static Match createMatch(Token pre, Token suc){
		return new Match(pre, suc, MatchType.MATCH);
	}
	
	public static Match createAntiMatch(Token pre){
		return new Match(pre, null, MatchType.ANTI);
	}
	
	public static Match createIndMatch(Token pre){
		return new Match(pre, null, MatchType.IND);
	}

	
}
