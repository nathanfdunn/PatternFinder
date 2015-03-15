package patternDetection;

import java.util.ArrayList;
import java.util.Collections;


public class PatternExtractor {

	/**
	 * 
	 * @param ts
	 * @return
	 */
	public static ArrayList<EvaluationObject> bruteForce(TokenStream ts){
		return bruteForce(ts, 100);
	}
	
	
	public static ArrayList<EvaluationObject> bruteForce(TokenStream ts, int numPatterns){
		ArrayList<Clause> clausePool = allClauses(ts);
		
		ArrayList<EvaluationObject> out = new ArrayList<EvaluationObject>();
		
		for (Clause pre : clausePool)
			for (Clause suc : clausePool){
				if (pre != suc){
					Interval t = PatternCompleter.completeTime(ts, pre, suc);
					if (t!=null){
						Pattern p = new Pattern(pre, suc, t);
						out.add(PatternEvaluator.evaluate(ts, p));
						if (out.size() > numPatterns){
							evictLowest(out);
						}
					}
				}
			}
		Collections.sort(out, Collections.reverseOrder());
		return out;
	}
	
	private static void evictLowest(ArrayList<EvaluationObject> list) {
		if (list.size() == 0)
			return;
		boolean success = list.remove( Collections.min(list) );
		if (!success)
			throw new Error("Something went wrong");
	}

	public static ArrayList<Clause> allClauses(TokenStream ts){
		ArrayList<String> quantities = ts.quantities();
		Behavior[] behaviors = Behavior.class.getEnumConstants();
		ArrayList<Clause> out = new ArrayList<Clause>();
		
		for (String quantID : quantities)
			for (Behavior b : behaviors)
				out.add( new Clause(quantID, b));
		
		return out;
	}
	
}






