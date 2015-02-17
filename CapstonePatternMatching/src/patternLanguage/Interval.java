package patternLanguage;


public class Interval {
	private double begin;
	private double end;
	
	
	public Interval(double begin, double end){
		if (end < begin){
			throw new Error("End of interval must be larger than start.");
		}
		this.begin = begin;
		this.end = end;
	}
	
	public double getBegin(){return begin;}
	public double getEnd(){return end;}
	
	public double getWidth(){
		return end-begin;
	}
	
	/**
	 * Tests whether the specified value is contained in the interval
	 *  (inclusive)
	 * @param x
	 * @return
	 */
	public boolean contains(double x){
		return begin <= x && x <= end;
	}
	
	/**
	 * Tests whether the specified Interval is contained in this one
	 * @param i
	 * @return
	 */
	public boolean contains( Interval i ){
		return begin <= i.getBegin() && i.getEnd() <= end;
	}
	
	//public boolean overlaps( Interval i)
	
	/**
	 * Combines to Intervals
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static Interval intervalSum(Interval i1, Interval i2){
		double start = i1.getBegin() + i2.getBegin();
		double end = i1.getEnd() + i2.getEnd();
		return new Interval(start, end);
	}
	
	/**
	 * Combines an Interval and a scalar
	 * @param i
	 * @param val
	 * @return
	 */
	public static Interval intervalSumScalar(Interval i, double val){
		return new Interval(i.getBegin() + val, i.getEnd() + val);
	}
	
	public String toString(){
		return "["+begin+","+end+"]";
	}
}
