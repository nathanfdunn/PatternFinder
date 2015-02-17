package patternLanguage;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		CompositeTokenStream cts = FileTranslator.translateFile();
		
		System.out.println("Parsed TokenStream File:");
		System.out.println(cts);
		System.out.println();

		displayMatches( cts, PatternFactory.getPattern1());
		displayMatches( cts, PatternFactory.getPattern2());
		displayMatches( cts, PatternFactory.getPattern3());
		
	}
	
	public static void displayMatches(CompositeTokenStream cts,
			BinaryPatternString bps){
		/*System.out.println("Token Stream: ");
		System.out.println(cts);*/
		System.out.println("Pattern: "+bps);
		System.out.println("Matches:");
		ArrayList<Match> matches = BinaryMatcher.findMatches(cts, bps);
		for (Match m : matches){
			System.out.println(m);
		}		
		System.out.println();

	}

}
