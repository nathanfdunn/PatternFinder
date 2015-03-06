package junk;

public interface IDataTable {

	public String[] getColNames();
	
	public double[] getCol(String colHeader);
	
	public double[] getCol(int colNum);
	
	public double[] getRow(int rowNum);
	
	//*
	public void insertRow(double[] data, int rowNum);
	
	public void deleteRow(int rowNum);
	
	public void insertCol(double[] data);
	public void insertCol(double[] data, String header);
	//*/

	
	//public double[][] getAllData();
	
	//public double[] getEntry(String colHeader, int rowNum);
	//public double[] getEntry(int colNum, int rowNum);
}