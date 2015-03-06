package behaviorClassification;

public class DataTable {
	protected double[][] entries;
	protected String[] headers;
	
	
	public DataTable(double[][] entries, String[] headers) {
		super();
		this.entries = entries;
		this.headers = headers;
	}
	
	public DataTable(double[][] entries) {
		super();
		this.entries = entries;
		this.headers = null;
	}
	
	
	public double[] getRow(int rowInd){
		double[] out = new double[getNumCols()];
		for (int i=0; i<out.length; i++)
			out[i] = entries[i][rowInd];
		return out;
	}
	
	public double[][] getEntries() {
		return entries;
	}
	
	public boolean isEmpty(){
		return entries.length==0 || entries[0].length==0;
	}
	
	//@require !this.isEmpty()
	public int getNumRows(){
		return entries[0].length;
	}
	
	public int getNumCols(){
		return entries.length;
	}
	
	
	public String[] getHeaders(){
		if (headers==null) return null;
		String[] out = new String[headers.length];
		for (int i=0; i<out.length; i++)
			out[i] = headers[i];
		
		return out;
	}
	
	
	private int headerInd(String header){
		if (headers == null)
			throw new Error("Table doesn't have headers");
		for (int i=0; i<headers.length; i++){
			if (header.equals(headers[i]))
				return i;
		}
		throw new Error("Not a valid column header");
	}
	
	public double[] getCol(String colHeader) {
		return getCol( headerInd(colHeader) );
	}
	
	public double[] getCol(int colInd) {
		return entries[colInd];
	}
	
	
}