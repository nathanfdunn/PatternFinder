package behaviorClassification;

import java.util.ArrayList;

import tests.Pnt;

/**
 * Holds data representing a table of raw time-series values
 * @author nathandunn
 *
 */
public class RawTimeSeriesTable extends DataTable{


	private static final long serialVersionUID = 6L;
	private int timeInd;			//Index of the time data
	
	private boolean before;			//Indicates if the times represent time
									// before some event (e.g. Years Before 
									// Present vs. Years AD)
	
	public boolean getForwardIndicator(){
		return before;
	}
	
//	public RawDataTable(double[][] entries) {
//		super(entries);
//	}
	
	//Headers required
	//Entries are in column-major order, with earliest times with lower indices
	public RawTimeSeriesTable(double[][] entries, String[] headers, int timeInd) {
		super(entries, headers);
		this.timeInd = timeInd;
		
		double[] times = entries[timeInd];
		System.out.println(times.length);
		System.out.println(times.length);
		System.out.println(times.length);
		System.out.println(times.length);
		
////		Pnt.pntArr(times);
//		Pnt.pnt(times.length);
//		
////		Pnt.pntArr(times);
//		Pnt.pnt(times[0]);
//		Pnt.pnt(times[times.length-1]);
		this.before = times[0] < times[ times.length-1 ];
	}
	
	
	/**
	 * @return the amount of time this record spans
	 */
	public double timeSpan(){
		double out = before?
				getLatestTime() - getEarliestTime():
				getEarliestTime() - getLatestTime();

		if (out < 0)
			throw new Error("Times are reversed");
		return out;
	}
	
	//TODO make more sophisticated
	public double getEarliestTime(){
		return getTimes()[0];
	}
	
	public double getLatestTime(){
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
	
	//TODO refactor
	/**
	 * Return a table whose time entries are only within the specified times
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public RawTimeSeriesTable subTable(double startTime, double endTime){
		ArrayList<Integer> inds = new ArrayList<Integer>();
		double[] times = this.getTimes();

		for (int i=0; i<this.getNumRows(); i++){
			if ( startTime <= times[i] && times[i] < endTime)
				inds.add(i);
		}
		
		double[][] emptyEntries = new double[this.getNumCols()][inds.size()];
		RawTimeSeriesTable out = new RawTimeSeriesTable(emptyEntries, 
				this.getHeaders(), this.timeInd);
		
		for (int i =0; i<inds.size(); i++)
			out.setRow(this.getRow( inds.get(i)), i);
		
		out.before = this.before;
		
		return out;
	}
	
	
//	public RawDataTable subTable(String[] desiredQuantities){
//		double[][] newEntries = new double[desiredQuantities.length][];
//		//for ()
//	}
	
	
}
