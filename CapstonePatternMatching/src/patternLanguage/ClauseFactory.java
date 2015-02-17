package patternLanguage;


/**
 * 
 * @author nathandunn
 * Provides static methods for creating some of the more common clause types
 */
public class ClauseFactory {

	public static CompositeTokenClause createSpikeClause(String name){
		BehaviorClause bc = new ExtremeBehaviorClause(1);
		return new CompositeTokenClause(new QuantityClause(name, bc));
	}

	public static CompositeTokenClause createDipClause(String name){
		BehaviorClause bc = new ExtremeBehaviorClause(-1);
		return new CompositeTokenClause(new QuantityClause(name, bc));
	}

	public static CompositeTokenClause createSteadyIncClause(String name){
		BehaviorClause bc = new SteadyBehaviorClause(1);
		return new CompositeTokenClause(new QuantityClause(name, bc));
	}

	public static CompositeTokenClause createSteadyDecClause(String name){
		BehaviorClause bc = new SteadyBehaviorClause(-1);
		return new CompositeTokenClause(new QuantityClause(name, bc));
	}
	
	public static CompositeTokenClause createFlatClause(String name){
		BehaviorClause bc = new FlatBehaviorClause();
		return new CompositeTokenClause(new QuantityClause(name, bc));
	}
	

	
}
