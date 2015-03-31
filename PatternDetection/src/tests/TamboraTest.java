package tests;

import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.SimpleTokenStream;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

public class TamboraTest {

	private static String file = "/Users/nathandunn/Desktop/tamboraTable.csv";
	public static void main(String[] args) {
		double start = 1650;
		double end = 2000;
		
		Object[] things = getStreamAndTable(file, start, end, 10.0);
		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream sts = (SimpleTokenStream)things[1];
		
		EvaluationSettings settings = new EvaluationSettings(2,0.25,0);
		
		
		ItaseSeasonal.showStuff(sts, table, settings);
	}
	
	
	
	private static void poorPerformance(){
		double start = 1717.545 - 2;
		double end = 1917.545;
		
		Object[] things = getStreamAndTable(file, start, end, 10);
		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream sts = (SimpleTokenStream)things[1];
		
		ItaseSeasonal.showStuff(sts, table, null);
	}
		
	private static Object[] getStreamAndTable(String fileName, double start,
			double end, int numChunks){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		table = table.subTable(start, end);
		SimpleTokenStream sts = Objs.toke.tokenize(table, numChunks, start, end);
		return new Object[]{table, sts};
	}

	private static Object[] getStreamAndTable(String fileName, double start,
			double end, double chunkWidth){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
//		Pnt.pnt(table);
//		Pnt.pnt(table.getTimeInd());
		table = table.subTable(start, end);
		SimpleTokenStream sts = Objs.toke.tokenize(table, chunkWidth, start, end);
		return new Object[]{table, sts};
	
	}

}
