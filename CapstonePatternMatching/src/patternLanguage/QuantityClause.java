package patternLanguage;


public class QuantityClause {
	
	private String quantityName;		//Ca, SO4, etc.
	private BehaviorClause bc;
	
	public QuantityClause(String quantityName, BehaviorClause bc){
		this.quantityName = quantityName;
		this.bc = bc;
	}
	
	
	public boolean matches(QuantityToken qt){
		if (quantityName==null){
			throw new Error("Quanity name must be defined");
		}
		
		if (!quantityName.equals(qt.getQuantityName())){
			return false;
		}
		
		if (bc != null)
			return bc.matches(qt.getBehavior());
		
		return true;
	}
	
	public String toString(){
		return quantityName + bc;
		
	}
}
