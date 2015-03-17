package behaviorClassification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Provides a class to parse CSV files and format the information
 *  into DataTables
 * @author nathandunn
 */
public class CsvToTable {

	//Value used to indicate missing data in P301
	private static final String NaValue = "-999";
	
	//TODO allow specification of which columns you want
		
	//TODO time reversal to account for increasing vs. decreasing time formats
	
	
	/**
	 * Parses the CSV with the specified fileName and formats data into a
	 *  RawTimeSeriesTable. CSV must be in the format of a table with multiple
	 *  quantity columns and one time column. Each column must have a header.
	 *  
	 *  Note: the time column must be the last column of the table
	 *   (will fix in a later update)
	 *  
	 * @param fileName	the CSV to be parsed
	 * @return			the formatted table
	 */
	public static RawTimeSeriesTable readCsv( String fileName ){
		return readCsv(fileName, -1);		//will use the findTimeInd function
	}
	
	/**
	 * Parses the CSV using the timeInd-th column as the time values
	 * @param fileName
	 * @param timeInd
	 * @return
	 */
	public static RawTimeSeriesTable readCsv(String fileName, int timeInd){
		//TODO account for formats that are "years before x" vs. "years after x"
		
		String[][] contents = readAll(fileName);
		
		String[] headers = parseHeaderLine(contents[0]);
		
		//Table is built in row-major order and then transposed after
		double[][] transposedEntries = new double[contents.length-1][];
		
		for (int i=1; i<contents.length; i++)
			transposedEntries[i-1] = parseLine(contents[i]);

		double[][] entries = MyMath.transpose(transposedEntries);

		if (timeInd == -1)
			timeInd = findTimeInd(entries);
		
		return new RawTimeSeriesTable(entries, headers, timeInd);
	}
	
	/**
	 * Figures out which column is most likely to be the one containing the
	 *  time values
	 * Note: currently assumes it is the last column
	 * @param entries
	 * @return
	 */
	private static int findTimeInd(double[][] entries){
		//TODO: Find which column is "most" sorted
		return entries.length-1;		//Assume it's the last column
	}
	
	//Strips off the quote marks
	private static String[] parseHeaderLine(String[] line){
		String[] out = new String[line.length];
		for (int i=0; i<line.length; i++){
			String s = line[i];
			String header = "";
			for ( char c : s.toCharArray() )
				if ( c!= '"' )
					header += "" + c;
			
			out[i] = header;
		}
		return out;
	}
	
	/**
	 * Parses the Strings into doubles. Yields NaN for a specific entry 
	 *  if NoValue is encountered or if not a numeric format
	 * @param line
	 * @return
	 */
	private static double[] parseLine(String[] line){
		double[] out = new double[line.length];
		try {
			for (int i=0; i<out.length; i++){
				if ( line[i].equals(NaValue) )
					out[i] = Double.NaN;
				else{
					double val;
					try{
						val = Double.parseDouble(line[i]);
					}catch(NumberFormatException e){		
						val = Double.NaN;
					}
					out[i] = val;
				}				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Returns a list of lists representing each comma-delimited entry
	 *  in the file
	 * @param fileName
	 * @return
	 */
	private static String[][] readAll(String fileName){
		ArrayList<String[]> contents = new ArrayList<String[]>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null)
				contents.add(line.split(","));

			br.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return contents.toArray(new String[contents.size()][]);
	}
}
