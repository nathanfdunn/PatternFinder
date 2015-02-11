package patternDetection;

//import java.util.ArrayList;

public class PatternMatcher {
	
	public static MatchDataObject findMatches(Pattern p, TokenStream ts){
		return new MatchDataObject(p, ts);
	}
		
}
