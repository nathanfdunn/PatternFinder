package patternDetection;

public class Pattern {
	public SimpleClause pre;
	public SimpleClause suc;
	public Interval time;
	
	public static final String PRE_SUC_SEP = "~";
	public static final String SUC_WIN_SEP = ":";
	
	
	public Pattern(SimpleClause pre, SimpleClause suc, Interval time) {
		this.pre = pre;
		this.suc = suc;
		this.time = time;
	}
	
	public String toString(){
		return pre + PRE_SUC_SEP + suc + SUC_WIN_SEP + time;
	}
	
	
}
