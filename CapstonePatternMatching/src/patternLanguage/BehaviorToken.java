package patternLanguage;

/**
 * 
 * @author nathandunn
 * Provides an object to summarize the trend or behavior of a quantity
 *  over the time scale of a token
 */
public abstract class BehaviorToken {
	public abstract BehaviorType getType();
	
	public abstract String toString();
}


