package patternDetection;

public class Interval {
	public int low;
	public int high;
	

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
		return String.format("[%d, %d]",low, high);
	}
}
