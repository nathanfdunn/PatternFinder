package patternDetection;

import java.util.ArrayList;
import java.util.Collections;

import patternDetection.EvaluationObject.EvaluationSettings;


public class PatternExtractor {

	private EvaluationSettings settings;
	
	public PatternExtractor(){
		this( new EvaluationSettings() );
	}
	
	public PatternExtractor(EvaluationSettings settings) {
		this.settings = settings;
	}

	public PatternExtractor(double alpha, double beta, int padding){
		this( new EvaluationSettings(alpha, beta, padding) );
	}
	
	
	public static ArrayList<EvaluationObject> defaultExtract(TokenStream ts){
		return new PatternExtractor().extract(ts);
	}
	public static ArrayList<EvaluationObject> defaultExtract(TokenStream ts, int numPatterns){
		return new PatternExtractor().extract(ts, numPatterns);
	}
	

	/**
	 * 
	 * @param ts
	 * @return
	 */
	public ArrayList<EvaluationObject> extract(TokenStream ts){
		return extract(ts, 100);
	}
	
	
	public ArrayList<EvaluationObject> extract(TokenStream ts, int numPatterns){
		ArrayList<Clause> clausePool = allClauses(ts);
		
		ArrayList<EvaluationObject> out = new ArrayList<EvaluationObject>();
		//TODO: check if pre and suc are present before doing time completion
		for (Clause pre : clausePool)
			for (Clause suc : clausePool){
				if (pre != suc || true){	//TODO
					Interval t = PatternCompleter.completeTime(ts, pre, suc);
					if (t!=null){
						Pattern p = new Pattern(pre, suc, t);
						out.add( evaluate(ts, p) );
						if (out.size() > numPatterns){
							evictLowest(out);
						}
					}
				}
			}
		Collections.sort(out, Collections.reverseOrder());
		return out;
	}
	
	
	private EvaluationObject evaluate(TokenStream ts, Pattern p){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo, this.settings);
	}
	
	
	private static void evictLowest(ArrayList<EvaluationObject> list) {
		if (list.size() == 0)
			return;
		boolean success = list.remove( Collections.min(list) );
		if (!success)
			throw new Error("Something went wrong while evicting lowest");
	}

	public static ArrayList<Clause> allClauses(TokenStream ts){
		ArrayList<String> quantities = ts.quantities();
		Behavior[] behaviors = Behavior.getKnownBehaviors();
		ArrayList<Clause> out = new ArrayList<Clause>();
		
		for (String quantID : quantities)
			for (Behavior b : behaviors)
				out.add( new Clause(quantID, b));
		
		return out;
	}
	
}






