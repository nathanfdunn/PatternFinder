package junk;

import java.util.ArrayList;

public class DataTable implements IDataTable {

	
	private String[] colHeaders;
	private ArrayList<ArrayList<Double>> entries;
//	private double[][] entries;
	private int numCols;
	private int numRows;
//	private int maxRows;
	
	public DataTable(String[] headers){
		this.colHeaders = headers;
		this.numCols = headers.length;
	}
	
	/*public DataTable(int numCols, int maxRows, String[] headers){
		this.numCols = numCols;
		this.maxRows = maxRows;
		//this.numRows = numRows;
		this.colHeaders = headers;
		this.numCols = headers.length;
	}*/
	
	
	@Override
	public String[] getColNames() {
		return colHeaders;
	}
	
	@Override
	public double[] getCol(String colHeader) {
		//int colInd = headerInd(colHeader);		//TODO
		//if (colInd == -1)
		//return getCol( colInd );
		return getCol( headerInd(colHeader) );
	}
	
	@Override
	public double[] getCol(int colInd) {
		double[] out = new double[numRows];
		for (int i=0; i<numRows; i++){
			out[i] = entries.get(colInd).get(i);
		}
		return out;
	}
	
	@Override
	public double[] getRow(int rowInd) {
		double[] out = new double[numCols];
		for (int i=0; i<numCols; i++){
			out[i] = entries.get(i).get(rowInd);
		}
		return out;
	}

	private int headerInd(String header){
		for (int i=0; i<colHeaders.length; i++){
			if (header.equals(colHeaders[i]))
				return i;
		}
		throw new Error("Not a valid column header");
	}

	@Override
	public void insertRow(double[] data, int rowNum) {
		if (data.length != numCols)
			throw new Error("Row is of incorrect length");
		for (int i=0; i<data.length; i++){
			
		}
	}

	@Override
	public void deleteRow(int rowNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertCol(double[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertCol(double[] data, String header) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}