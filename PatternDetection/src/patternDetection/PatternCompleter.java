package patternDetection;

import java.util.ArrayList;

import patternDetection.EvaluationObject.EvaluationSettings;

/**
 * 
 * @author nathandunn
 *	Finds the pattern that best describes the given data given an incomplete pattern
 */
public class PatternCompleter {
	
	public static Interval completeTime( TokenStream ts, Clause pre, Clause suc ){
		return completeTime(ts, pre, suc, EvaluationSettings.DEFAULT);
	}

	public static Interval completeTime( TokenStream ts, Clause pre, Clause suc, EvaluationSettings settings ){
		Interval out = null;
		double maxPower = -1;
		double curPower = -1;
		//TODO if pre==suc, don't allow [0,0]
		for (int end = 0; end < ts.length(); end++){
			for (int start = 0; start <= end; start++){
				Interval temp = new Interval(start, end);
				Pattern p = new Pattern(pre, suc, temp);
				curPower = EvaluationObject.evaluatePower(ts, p, settings);
				if (curPower > maxPower){
					maxPower = curPower;
					out = temp;
				}
			}
		}
		return out;
	}
	
	public static Clause completeSuccessor( TokenStream ts, Clause pre, Interval time ){
		return completeSuccessor( ts, pre, time, EvaluationSettings.DEFAULT );
	}
	
	public static Clause completeSuccessor( TokenStream ts, Clause pre, Interval time, EvaluationSettings settings){
		ArrayList<String> quantities = ts.quantities();
		Behavior[] behaviors = Behavior.class.getEnumConstants();

		Clause out = null;
		double maxPower = -1;
		double curPower = -1;
		
		for (String quantID : quantities){
			for (Behavior b : behaviors){
				Clause suc = new Clause(quantID, b);
				Pattern p = new Pattern(pre, suc, time);
				curPower = EvaluationObject.evaluatePower(ts, p, settings);
				if (curPower > maxPower){
					maxPower = curPower;
					out = suc;
				}
			}
		}
		
		return out;
	}
	
	
	public Clause completePrecursor( TokenStream ts, Clause suc, Interval time, EvaluationSettings settings ){
		ArrayList<String> quantities = ts.quantities();
		Behavior[] behaviors = Behavior.getKnownBehaviors();

		Clause out = null;
		double maxPower = -1;
		double curPower = -1;
		
		for (String quantID : quantities){
			for (Behavior b : behaviors){
				Clause pre = new Clause(quantID, b);
				Pattern p = new Pattern(pre, suc, time);
				curPower = EvaluationObject.evaluatePower(ts, p, settings);
				if (curPower > maxPower){
					maxPower = curPower;
					out = pre;
				}
			}
		}
		
		return out;
	}
	
	public Clause completePrecursor( TokenStream ts, Clause suc, Interval time ){
		return completePrecursor( ts, suc, time, EvaluationSettings.DEFAULT );
	}
	
	
}
