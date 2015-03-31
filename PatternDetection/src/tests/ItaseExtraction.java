package tests;

import gui.TokenStreamDisplayer;

import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.Behavior;
import patternDetection.SimpleClause;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.Pattern;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;
import patternDetection.Tokenizer;
import behaviorClassification.CsvToTable;
import behaviorClassification.KnnModel;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.Serializer;

/**
 * Tests Pattern Extraction on seasonal data
 * @author nathandunn
 *
 */
public class ItaseExtraction {

	public static void main(String[] args) {

		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
		Tokenizer tokenizer = new Tokenizer(new ModelClassifier(model));
		
		RawTimeSeriesTable itaseData = CsvToTable.
				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered.csv");
//				readCsv("../../DataSets_R/US_ITASE-00-3_2013_filtered_truncated.csv");
				
		SimpleTokenStream ts = tokenizer.tokenize(itaseData, 0.5);	//half a year chunk width
		
		ts = perfectSeasonal();
//		Pnt.pnt(ts);
//		Pnt.pnt(ts.length());

		TokenStreamDisplayer tsd = new TokenStreamDisplayer(
				ts.getQuant(ts.quantities().get(0)),
				new double[][]{
					itaseData.getTimes(),
					itaseData.getCol(0)
				},
				"SO4", 
//				ts.getPartition()
				new double[0]
				);

//		JFrame win = new JFrame();
//		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		win.add(tsd);
//		win.pack();
//		win.setVisible(true);

//		int crash = 1/0;
		
		
//		System.out.println(ts);
		ArrayList<EvaluationObject> patterns = PatternExtractor.defaultExtract(ts);
		
		Pnt.pnt("good");

		for (EvaluationObject eo : patterns)
			System.out.println(eo);
		
		SimpleClause pre = new SimpleClause("SO4", Behavior.INC);
		SimpleClause suc = new SimpleClause("SO4", Behavior.DEC);
		Pattern p = new Pattern(pre, suc, new Interval(1,1));
		Pattern p2 = new Pattern(suc, pre, new Interval(1,1));
		
		
		System.out.println(EvaluationObject.evaluatePower(ts, p));
		System.out.println(EvaluationObject.evaluatePower(ts, p2));
		
		
		
	}
	
	private static SimpleTokenStream perfectSeasonal(){
		SimpleTokenStream out = new SimpleTokenStream();
		ArrayList<SimpleToken> sulfate = new ArrayList<SimpleToken>();
		//double[] partition = new double[226];
		for (int i=0; i<226; i++){
			SimpleToken t = new SimpleToken("SO4", Behavior.UNK, i);
			t.behavior = i%2==0? Behavior.INC: Behavior.DEC;
			sulfate.add(t);
		}
		out.add("SO4", sulfate);
		return out;
	}
	
	
	
	
	
	

}
