package patternLanguage;


public class PatternFactory {

	
	public static BinaryPatternString getPattern1(){
		return new BinaryPatternString(
				ClauseFactory.createSpikeClause("SO4"),
				ClauseFactory.createSteadyDecClause("K"),
				new Interval(0,400)
				);
		
	}
	
	public static BinaryPatternString getPattern2(){
		return new BinaryPatternString(
				ClauseFactory.createSpikeClause("SO4"),
				ClauseFactory.createFlatClause("K"),
				new Interval(301,305)
				);
				
	}
	
	public static BinaryPatternString getPattern3(){
		return new BinaryPatternString(
				ClauseFactory.createSteadyDecClause("NA"),
				ClauseFactory.createSpikeClause("K"),
				new Interval(200,300)
				);
				
	}
	
	
}
