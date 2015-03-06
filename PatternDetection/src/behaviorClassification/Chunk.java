package behaviorClassification;

public class Chunk {

	private double[] vals;
	private double[] times;
	
	private double start;
	private double end;
	
	
	public Chunk(double[] vals, double[] times, double start, double end) {
		super();
		this.vals = vals;
		this.times = times;
		this.start = start;
		this.end = end;
	}
	
	
	public double[] getVals() {
		return vals;
	}
	public double[] getTimes() {
		return times;
	}
	public double getStart() {
		return start;
	}
	public double getEnd() {
		return end;
	}

}
