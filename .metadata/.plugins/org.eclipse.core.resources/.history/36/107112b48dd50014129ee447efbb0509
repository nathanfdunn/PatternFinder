package patternDetection;

import java.util.ArrayList;


/**
 * 
 * @author nathandunn
 * Provides a class to evaluate the predictive strength of a Pattern
 */
public class PatternEvaluator {

	
	public static EvaluationObject evaluate(TokenStream ts, Pattern p){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo);
	}
	
	public static EvaluationObject evaluate(MatchDataObject mdo){
		return new EvaluationObject(mdo);
	}
	
	public static double evaluatePower(TokenStream ts, Pattern p){
		return evaluate(ts, p).getPower();
	}
	
	
	/**
	 * Uses a binomial model to compute how likely it is that there 
	 *  would be at least one occurrence of the specified Clause
	 *  within an interval of the specified width
	 * @param ts
	 * @param c
	 * @param width		width of the time window of the Pattern string
	 * @return
	 */
	public static double rarity(TokenStream ts, Clause c, int width){
		double frequency = (double)ts.filter(c).size() / ts.length();
		
		return 1 - Math.pow(1-frequency, width+1);
		
	}
	
	public static double rarityVerbose(TokenStream ts, Clause c, int width){
		ArrayList<Token> tokens = ts.filter(c);
		int len1 = ts.length();
		int len2 = tokens.size();
		double freq = (double)len2 / len1;
		double rarity = 1-Math.pow(1-freq, width+1);

		System.out.println("Tokens:"); System.out.println(tokens);
		System.out.println(String.format("Total length:		%d", len1));
		System.out.println(String.format("Filtered length:	%d", len2));
		System.out.println(String.format("Frequency:		%f", freq));
		System.out.println(String.format("Width:			%d", width));
		System.out.println(String.format("Rarity:			%f", rarity));
		return rarity;
	}
	
}
