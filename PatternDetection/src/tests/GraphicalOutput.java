package tests;

import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.EvaluationObject;
import patternDetection.PatternExtractor;
import patternDetection.SimpleTokenStream;
import patternDetection.EvaluationObject.EvaluationSettings;
import ui.TokenStreamDisplayer;
import behaviorClassification.RawTimeSeriesTable;

public class GraphicalOutput {
	//TODO: remove?
//	public static void showTokensAndPatterns(SimpleTokenStream sts, RawTimeSeriesTable table,
//			EvaluationSettings settings, String[] quan){
//		double[] partition = sts.getPartition() == null ? new double[0] : sts.getPartition();
//		
//		TokenStreamDisplayer tsd = table == null?
//				new TokenStreamDisplayer(sts.getQuant("SO4")) :
//				new TokenStreamDisplayer(
//				sts.getQuant("SO4"),
//				new double[][]{
//					table.getTimes(),
//					table.getCol("SO4")
//				},
//				"SO4", 
//				partition
//				);
//
//		JFrame win = new JFrame();
//		win.add(tsd);
//		win.pack();
//		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		win.setVisible(true);		
//
//		ArrayList<EvaluationObject> patterns = settings == null?
//				PatternExtractor.defaultExtract(sts) : 
//				new PatternExtractor(settings).extract(sts);
//						
//		for (EvaluationObject eo : patterns)
//			System.out.println(eo);
//	}
}
