package patternLanguage;

import java.util.ArrayList;

/**
 * 
 * @author nathandunn
 * Finds matches to a BinaryPatternString
 */
public class BinaryMatcher {
	
	public static ArrayList<Match> findMatches(CompositeTokenStream tokenStream,
			BinaryPatternString pattern){
		
		ArrayList<Match> out = new ArrayList<Match>();
		int i = 0;
		ArrayList<CompositeToken> tokens = tokenStream.getTokens();
		
		for (i=0; i<tokens.size(); i++){
			CompositeToken token = tokens.get(i);
			if ( pattern.getPrecursor().matches(token) )
				out.add( findSuccessor(i, tokens, pattern, token) );
		}
		return out;
	}
	
	/**
	 * Searches the rest of the token stream for a successor token
	 * 
	 * @param startInd		where in the stream the precursor was found
	 * @param tokens		token stream
	 * @param pattern		pattern to be matched
	 * @param precursor		the precursor that was found
	 * @return	either a match, anti-match, or indeterminate match
	 */
	private static Match findSuccessor(int startInd, ArrayList<CompositeToken> tokens,
			BinaryPatternString pattern, CompositeToken precursor){
		
		double latestPossible = calculateLatestPossible(pattern, precursor);
		int j=startInd;
		
		while (j < tokens.size()){
			CompositeToken possibleSuccessor = tokens.get(j);
			//System.out.println(possibleSuccessor);
			if (possibleSuccessor == null)
				return Match.INDET_MATCH;
			
			/*double time = possibleSuccessor.getTimeAttr().getStart();
			if (time > latestPossible)
				return Match.createAntiMatch(precursor);*/
			
			if (pattern.getSuccessor().matchesSuccessor(pattern, precursor,
					possibleSuccessor)){
				return new Match(precursor, possibleSuccessor);
			}
			
			double time = possibleSuccessor.getTimeAttr().getEnd();
			if (time > latestPossible)
				return Match.createAntiMatch(precursor);
			
			j++;
		}
		return Match.INDET_MATCH;
	}
	
	/**
	 * Calculates the last possible date where a successor event could occurr
	 *  and still match the string
	 * @param pattern
	 * @param precursor
	 * @return
	 */
	private static double calculateLatestPossible( BinaryPatternString pattern,
			CompositeToken precursor){
		return precursor.getTimeAttr().getEnd() + pattern.getTimeWindow().getEnd();
	}
	
}
