package tests;

import java.awt.GridLayout;

import javax.swing.JFrame;

import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;
import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.Interval;
import patternDetection.MatchDataObject;
import patternDetection.Pattern;
import patternDetection.SimpleClause;
import patternDetection.SimpleTokenStream;
import ui.TokenStreamDisplayer;

public class MatchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleClause pre = new SimpleClause("Cl", Behavior.FLA);
		SimpleClause suc = new SimpleClause("Ca", Behavior.SPI);
		Interval t = new Interval(27, 27);
		Pattern p = new Pattern(pre, suc, t);
		
		String[] quants = new String[]{
				"Ca","Cl"//,"NO3",//"Na"
		};
		
		RawTimeSeriesTable table = CsvToTable.readCsv(Objs.DATA_PATH+"nfdunn_GISP2_formatted.csv");
		SimpleTokenStream sts = Objs.tokenizer.tokenize(table, 30);

		sts = sts.subStream( quants );
		
		MatchDataObject mdo = new MatchDataObject(p, sts);
		
		EvaluationObject eo = new EvaluationObject(mdo);
		
		Pnt.pnt(mdo);
		Pnt.pnt(eo);
		
		
//		JFrame win = new JFrame();
//		win.setLayout(new GridLayout(quants.length, 1));
//		for (String q : quants){
//			TokenStreamDisplayer tsd = new TokenStreamDisplayer(sts, table, q);
//			win.add(tsd);
//		}
//		win.pack();
//		win.setVisible(true);
	}

}
