package patternLanguage;

/**
 * 
 * @author nathandunn
 * An object representing all features of one particular quantity over
 *  a particular time interval (the same interval as that of the parent
 *  CompositeToken
 */
public class QuantityToken {
	private String quantityName;
	private BehaviorToken behavior;
	
	public QuantityToken(String name, BehaviorToken behavior){
		this.quantityName =	name;
		this.behavior = behavior;
	}
	
	public String getQuantityName(){
		return quantityName;
	}
	
	public BehaviorToken getBehavior(){
		return behavior;
	}
	
	public String toString(){
		return quantityName + behavior;
	}
	
}
