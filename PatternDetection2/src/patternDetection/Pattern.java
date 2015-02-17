package patternDetection;

public class Pattern {
	public Clause pre;
	public Clause suc;
	public Interval time;
	
	
	public Pattern(Clause pre, Clause suc, Interval time) {
		this.pre = pre;
		this.suc = suc;
		this.time = time;
	}
	
	public String toString(){
		return pre + "~" + suc + ":" + time;
	}
	
	
}
