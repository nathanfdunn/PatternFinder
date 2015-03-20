package patternDetection;

import java.util.ArrayList;

public class MatchDataObject {

	private Pattern p;		//Pattern this object tries to match
	private TokenStream ts;

	private ArrayList<Token> precursors;
	private ArrayList<Token> successors;
	private MatchDataObject.DifferenceTable lags;
	
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
				Token possibleSuccessor = lags.firstBetween(i, p.time);
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

	

	private static class DifferenceTable {

		private ArrayList<Token> precursors;
		private ArrayList<Token> successors;
		private int streamLen;
		private int[][] diff;


		public DifferenceTable(ArrayList<Token> precursors, ArrayList<Token> successors, int length){
			this.precursors = precursors;
			this.successors = successors;
			this.diff = new int[precursors.size()][successors.size()];
			this.streamLen = length;
			populateTable();

		}

		private void populateTable(){
			for (int i = 0; i < precursors.size(); i++)
				for (int j = 0; j < successors.size(); j++)
					diff[i][j] = successors.get(j).time - precursors.get(i).time;
		}


		//TODO rename
		/**
		 * Returns the first Successor whose lag is within the range of the
		 *  specified Precursor
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		public Token firstBetween(int precursorIndex, Interval interval){
			int[] lags = diff[precursorIndex];
			for (int i=0; i<lags.length; i++){
				if (interval.contains(lags[i]))
					return successors.get(i);
			}
			return null;
		}

		/**
		 * Checks if the values are applicable, given the length of the stream
		 * @param precursorIndex
		 * @param interval
		 * @return
		 */
		public boolean isDeterminate(int precursorIndex, Interval interval){
			int preTime = precursors.get(precursorIndex).time;
			return preTime + interval.high <= streamLen;
		}


//		public ArrayList<Token> getPrecursors() {
//			return precursors;
//		}
//
//		public ArrayList<Token> getSuccessors() {
//			return successors;
//		}
//
//		public int getStreamLen() {
//			return streamLen;
//		}
	}

	
}
