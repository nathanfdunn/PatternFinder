package behaviorClassification;

import java.io.Serializable;

public class Chunk implements Serializable {

	private static final long serialVersionUID = 3L;
	
	private double[] vals;
	private double[] times;
	
	private double start;
	private double end;
	
	
	public Chunk(double[] vals, double[] times, double start, double end) {
		MyMath.checkDim(vals, times);
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
	
	/**
	 * @return number of time-value pairs in this chunk
	 */
	public int getLength() {
		return vals.length;
	}
	
	public String toString(){
		String out = String.format("(%f, %f)\n", start, end);
		for (int i=0; i<vals.length; i++)
			out += String.format("%f,	%f\n", vals[i],times[i]);
		return out;
	}

}
