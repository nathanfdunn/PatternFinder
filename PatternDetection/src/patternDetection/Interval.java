package patternDetection;

public class Interval {
	public int low;
	public int high;
	
	public static final String L_BRACK = "[";
	public static final String R_BRACK = "]";
	public static final String STA_END_SEP = ",";
	
	
	public Interval(int low, int high) {
		this.low = low;
		this.high = high;
	}

	public boolean contains(int val){
		return low <= val && val <= high;
	}

	public int getWidth(){
		return high - low;
	}
	
	public String toString(){
		return L_BRACK + low + STA_END_SEP + high + R_BRACK;
	}
}
