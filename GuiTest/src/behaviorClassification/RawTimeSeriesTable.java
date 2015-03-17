package behaviorClassification;

import java.util.ArrayList;

/**
 * Holds data representing a table of raw time-series values
 * @author nathandunn
 *
 */
public class RawTimeSeriesTable extends DataTable{


	private static final long serialVersionUID = 6L;
	private int timeInd;			//Index of the time data
	
	//private double 
	
//	public RawDataTable(double[][] entries) {
//		super(entries);
//	}
	
	//Headers required
	public RawTimeSeriesTable(double[][] entries, String[] headers, int timeInd) {
		super(entries, headers);
		this.timeInd = timeInd;
	}
	
	
	/**
	 * @return the amount of time this record spans
	 */
	public double timeSpan(){
		double out = getLastTime() - getFirstTime();
		//TODO fix this
		if (out < 0)
			throw new Error("Times are reversed");
		return out;
	}
	
	//TODO make more sophisticated
	public double getFirstTime(){
		return getTimes()[0];
	}
	
	public double getLastTime(){
		double[] times = getTimes();
		return times[times.length-1];
	}

	public double[] getTimes(){
		return entries[timeInd];
	}
	
	public int getTimeInd(){
		return timeInd;
	}
	
	public String getTimeHeader(){
		return this.headers[timeInd];
	}
	
	public String[] getNonTimeHeaders(){
		String timeHeader = getTimeHeader();
		ArrayList<String> out = new ArrayList<String>();
		for (String s : this.headers)
			if ( !s.equals(timeHeader) )
				out.add(s);

		return out.toArray(new String[out.size()]);
	}
	
	
	
//	public RawDataTable subTable(String[] desiredQuantities){
//		double[][] newEntries = new double[desiredQuantities.length][];
//		//for ()
//	}
	
	
}
