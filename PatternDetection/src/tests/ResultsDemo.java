package tests;

import gui.CompositeTokenStreamDisplayer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import patternDetection.Behavior;
import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.PatternExtractor;
import patternDetection.SimpleToken;
import patternDetection.SimpleTokenStream;

public class ResultsDemo {

	public static void main(String[] args) {
//	Objs.setEvalSettings(2, 0.2, 2);

		TamboraTest.main(null);
		Pnt.pause();
//		Objs.setEvalSettings(0.5, 0, 0.2);		//Lagged seasonal
//		Object o = Objs.tokenizer;
//		Pnt.pnt(o);
////		randomPatterns();
//*
//		Objs.setEvalSettings(0.5, 0, 0.1);
//ClassifierTrainingTest.demoManual();
//		Pnt.pause();
		
//		Gisp2Test.main(null);
//		Pnt.pause();

		ItaseSeasonal.main(null);
		Pnt.pause();
		Pnt.pnt();
		Pnt.pause();
		Gisp2Test.main(null);
		Pnt.pause();
		ItaseSeasonal.longItase();
		//*/
	}
	
	public static void displayPatterns(SimpleTokenStream sts, EvaluationSettings settings){
		ArrayList<EvaluationObject> patterns = new PatternExtractor(settings).extract(sts);
		Pnt.pntArr(patterns);
	}
	public static void displayPatterns(SimpleTokenStream sts){
		ArrayList<EvaluationObject> patterns = new PatternExtractor().extract(sts);
		Pnt.pntArr(patterns);
	}
	
	public static void randomPatterns(){
		String[] quants = new String[]{
			"NO3", "K", "SO4", "Ca"	
		};
		int len = 100;
		SimpleTokenStream sts = new SimpleTokenStream();
		for (String q : quants){
			ArrayList<SimpleToken> list = new ArrayList<SimpleToken>();
			for (int i=0; i<len; i++){
				list.add(randToken(q, i));
			}
			sts.add(q, list);
		}
		
		new CompositeTokenStreamDisplayer(sts).display();
		displayPatterns(sts);
	}
	
	private static SimpleToken randToken(String quant, int i){
		int ind = new Random().nextInt(5);
		return new SimpleToken(quant, Behavior.getKnownBehaviors()[ind], i);
	}

}
