package patternLanguage;

public class TimeClause {
	private Interval interval;
	
	public boolean matches(double x){
		return interval.contains(x);
	}
}
