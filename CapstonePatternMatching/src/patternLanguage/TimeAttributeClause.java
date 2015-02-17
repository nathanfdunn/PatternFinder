package patternLanguage;



public class TimeAttributeClause {
	
	private Interval startInter;
	private Interval endInter;
	private Interval medInter;
	private Interval durInter;
	
	
	public boolean matches(TimeAttribute timeAttr){
		if (timeAttr == null)
			return false;
		
		if (startInter != null && !startInter.contains(timeAttr.getStart()))
			return false;
		
		if (endInter != null && !endInter.contains(timeAttr.getEnd()))
			return false;
		
		if (medInter != null && !medInter.contains(timeAttr.getMedian()))
			return false;
		
		if (durInter != null && !durInter.contains(timeAttr.getDuration()))
			return false;
		
		return true;
	}
	
	
	
	public TimeAttributeClause(){}
	
	public Interval getStartInter() {return startInter;}
	public void setStartInter(Interval startInter) {this.startInter = startInter;}
	
	public Interval getEndInter() {return endInter;}
	public void setEndInter(Interval endInter) {this.endInter = endInter;}

	public Interval getMedInter() {return medInter;}
	public void setMedInter(Interval medInter) {this.medInter = medInter;}

	public Interval getDurInter() {return durInter;}
	public void setDurInter(Interval durInter) {this.durInter = durInter;}
	
}
