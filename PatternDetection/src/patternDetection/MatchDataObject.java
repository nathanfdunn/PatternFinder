package patternDetection;

import java.util.ArrayList;

public class MatchDataObject {

	private Pattern p;		//Pattern this object tries to match
	private TokenStream ts;

	private ArrayList<Token> precursors;
	private ArrayList<Token> successors;
	public DifferenceTable lags;
	
	private ArrayList<Match> matches;
	private ArrayList<Match> antiMatches;
	private ArrayList<Match> indMatches;
	
	public MatchDataObject(Pattern p, TokenStream ts){
		this.p = p;
		this.ts = ts;
		precursors = ts.filter(p.pre);
		successors = ts.filter(p.suc);

		lags = new DifferenceTable(precursors, successors, ts.length());
		matches 	= new ArrayList<Match>();
		antiMatches = new ArrayList<Match>();
		indMatches 	= new ArrayList<Match>();

		findMatches();
	}
	
	private void findMatches(){
		for (int i=0; i<precursors.size(); i++){
			Match match;
			Token pre = precursors.get(i);
			if (lags.isDeterminate(i, p.time)){
				Token possibleSuccessor = lags.between(i, p.time);
				if (possibleSuccessor == null)
					match = Match.createAntiMatch(pre);
				else
					match = Match.createMatch(pre, possibleSuccessor);
			}else{
				match = Match.createIndMatch(pre);
			}
			this.addMatch(match);
		}
	}
	
	private void addMatch( Match m ){
		if (m.type == MatchType.MATCH)
			matches.add(m);
		else if (m.type == MatchType.ANTI)
			antiMatches.add(m);
		else// if (m.type == MatchType.IND)
			indMatches.add(m);
	}
	
	
	
	//Getters
	public Pattern getPattern() {
		return p;
	}

	public TokenStream getTokenStream() {
		return ts;
	}

	public ArrayList<Token> getPrecursors() {
		return precursors;
	}

	public ArrayList<Token> getSuccessors() {
		return successors;
	}

	public DifferenceTable getLags() {
		return lags;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

	public ArrayList<Match> getAntiMatches() {
		return antiMatches;
	}

	public ArrayList<Match> getIndMatches() {
		return indMatches;
	}

}