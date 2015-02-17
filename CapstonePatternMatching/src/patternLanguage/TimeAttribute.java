package patternLanguage;


/**
 * 
 * @author nathandunn
 * Provides a description of the time interval where a token was taken from
 */
public class TimeAttribute {
	//All values given in years since start of record
	// (may eventually change this to use Calendar class)
	private double start;
	private double end;
	private Double medianTime;
	
	public TimeAttribute(double start, double end, Double median){
		this.start=start;
		this.end=end;
		this.medianTime=median;
	}
	
	public double getStart(){return start;}
	public double getEnd(){return end;}
	public Double getMedian(){return medianTime;}

	
	public Interval getStartEndInter(){
		return new Interval(start, end);
	}
	
	//The number of years the token spans
	public double getDuration(){return start-end;}

}
