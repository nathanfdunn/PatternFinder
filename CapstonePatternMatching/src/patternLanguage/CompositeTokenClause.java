package patternLanguage;


/**
 * 
 * @author nathandunn
 *
 */
public class CompositeTokenClause {
	private QuantityClause qc;
	private TimeAttributeClause tac;

	
	public CompositeTokenClause( QuantityClause qtc, TimeAttributeClause tac){
		this.qc = qtc;
		this.tac = tac;
	}
	
	
	public CompositeTokenClause( QuantityClause qtc ){
		this.qc = qtc;
	}
	
	public QuantityClause getQC(){
		return qc;
	}
	
	/**
	 * Requires that the token matches in terms of time
	 * @param compTok
	 * @return
	 */
	public boolean matches(CompositeToken compTok){
	/*	if (tac != null && !tac.matches(compTok.getTimeAttr())){
			return false;
		}*/
		
		if (qc == null){
			return true;
		}
			
		for (QuantityToken qt : compTok.getQuantityTokens()){
			if (qc.matches(qt)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the possibleSuccessor matches the string in
	 *  conjunction with the pattern and precursor match
	 * @param pattern
	 * @param precursor
	 * @param possibleSuccessor
	 * @return
	 */
	public boolean matchesSuccessor(BinaryPatternString pattern, 
			CompositeToken precursor, CompositeToken possibleSuccessor){
		
		Interval timeWindow = Interval.intervalSum(
				precursor.getTimeAttr().getStartEndInter(),
				pattern.getTimeWindow()
				);
		
		if (!timeWindow.contains(
				possibleSuccessor.getTimeAttr().getStartEndInter())){
			return false;
		}
		
		return matches(possibleSuccessor);

	}
	
	
	public String toString(){
		return qc.toString();
	}

}
