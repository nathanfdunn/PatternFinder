package tests;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.PatternExtractor;
import patternDetection.SimpleTokenStream;
import ui.TokenStreamDisplayer;
import behaviorClassification.CsvToTable;
import behaviorClassification.MyMath;
import behaviorClassification.RawTimeSeriesTable;

public class TamboraTest {

	private static String so4file = "/Users/nathandunn/Desktop/tamboraTable.csv";
//	private static String d18Ofile = "/Users/nathandunn/Desktop/nfdunn_US ITASE_2000_1.csv";
	private static String d18Ofile = "/Users/nathandunn/Desktop/tambora_d18O_smooth.csv";

	private static final double START = 1650;
	private static final double END = 2000;
	private static final int NUM_CHUNKS = 20;
	
	public static void main(String[] args) {
		
		poorPerformance();
		Pnt.pause();
		stableIons();
		Pnt.pause();
		mergedSeries();
		
//		poorPerformance();

//		double start = 1650;
//		double end = 2000;
//		
////		Object[] things = getStreamAndTable(file, start, end, 10.0);
//		
////		Object[] things = getStreamAndTable("/Users/nathandunn/Desktop/nfdunn_US ITASE_2000_1.csv",start,end,10.0);
//		
//		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
//		SimpleTokenStream sts = (SimpleTokenStream)things[1];
//		
//		EvaluationSettings settings = new EvaluationSettings(2,0.25,1);
//		
//		ItaseSeasonal.showTokensAndPatterns(sts, table, settings);
	}
	
	public static void mergedSeries(){
		double start = START;
		double end = END;
		int n = NUM_CHUNKS;
		
		Object[] things = getStreamAndTable(so4file, start, end, n);
		RawTimeSeriesTable so4Table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream so4Sts = (SimpleTokenStream)things[1];
		so4Sts = so4Sts.subStream(new String[]{"SO4"});

		things = getStreamAndTable(d18Ofile, start, end, n);
		RawTimeSeriesTable d18OTable = (RawTimeSeriesTable)things[0];
		SimpleTokenStream d18OSts = (SimpleTokenStream)things[1];
		d18OSts = d18OSts.subStream(new String[]{"d18O"});
//		Pnt.pnt(d18OSts);
//		int i=1/0;
		
		TokenStreamDisplayer so4Displayer = new TokenStreamDisplayer(so4Sts, so4Table, "SO4");
		TokenStreamDisplayer d18ODisplayer = new TokenStreamDisplayer(d18OSts, d18OTable, "d18O");
		
		JFrame win = new JFrame();
		win.setLayout(new GridLayout(2, 1));
		win.add(so4Displayer);
		win.add(d18ODisplayer);

//		win.setLayout(new BorderLayout());
//		win.add(so4Displayer, BorderLayout.NORTH);
//		win.add(d18ODisplayer, BorderLayout.SOUTH);
		win.pack();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);
//		
//		SimpleTokenStream mergedStream = SimpleTokenStream.mergeStreams(so4Sts, d18OSts);
//		ResultsDemo.displayPatterns(mergedStream);
		
	}
	
//	public static TokenStreamDisplayer getDisplayer(RawTimeSeriesTable table,
//			SimpleTokenStream sts){
//		
//	}
	
	private static void stableIons(){
		Pnt.pnt("Stable Ions");
		double start = 1717.545 - 2;
		double end = 1917.545;
		int nChunks = NUM_CHUNKS;
		
		Object[] things = getStreamAndTable(d18Ofile, start, end, nChunks);
		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream sts = (SimpleTokenStream)things[1];
		
		double[] d18O = table.getCol("d18O");
		double offset = -MyMath.getMin(d18O) + 1;
		for (int i=0; i<d18O.length;i++){
			d18O[i]+=offset;
		}
		
		new TokenStreamDisplayer(sts, table, "d18O").display();
		//ItaseSeasonal.showTokensAndPatterns(sts, table, null);
		SimpleTokenStream subStream = sts.subStream(new String[]{"d18O"});
		Pnt.pnt(subStream);
		ResultsDemo.displayPatterns(subStream);
	}

	private static void poorPerformance(){
		Pnt.pnt("Tambora");
		double start = 1717.545 - 2;
		double end = 1917.545;
		
		Object[] things = getStreamAndTable(so4file, start, end, 10.0);
		RawTimeSeriesTable table = (RawTimeSeriesTable)things[0];
		SimpleTokenStream sts = (SimpleTokenStream)things[1];
		
		ItaseSeasonal.showTokensAndPatterns(sts, table, null);
	}
	
	public static Object[] getStreamAndTable(String fileName, double start,
			double end, int numChunks){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		table = table.subTable(start, end);
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, numChunks, start, end);
		return new Object[]{table, sts};
	}

	public static Object[] getStreamAndTable(String fileName, double start,
			double end, double chunkWidth){
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		
////		Pnt.pnt(table);
////		Pnt.pnt(table.getTimeInd());
//		Pnt.pnt(start);
//		Pnt.pnt(end);

		table = table.subTable(start, end);
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, chunkWidth, start, end);
		return new Object[]{table, sts};
	}
	
	
	public static void showTokensAndPatterns(SimpleTokenStream sts, RawTimeSeriesTable table,
			EvaluationSettings settings){
		double[] partition = sts.getPartition() == null ? new double[0] : sts.getPartition();
		
		TokenStreamDisplayer tsd = table == null?
				new TokenStreamDisplayer(sts.getQuant("SO4")) :
				new TokenStreamDisplayer(
				sts.getQuant("SO4"),
				new double[][]{
					table.getTimes(),
					table.getCol("SO4")
				},
				"SO4",
				partition
				);

		JFrame win = new JFrame();
		win.add(tsd);
		win.pack();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);		

		ArrayList<EvaluationObject> patterns = settings == null?
				PatternExtractor.defaultExtract(sts) : 
				new PatternExtractor(settings).extract(sts);
						
		for (EvaluationObject eo : patterns)
			System.out.println(eo);
	}
	
	

}
