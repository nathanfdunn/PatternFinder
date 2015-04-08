package tests;

import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import patternDetection.Tokenizer;
import behaviorClassification.KnnModel;
import behaviorClassification.ModelClassifier;
import behaviorClassification.Serializer;

/**
 * Used to get a whole bunch of objects that are regularly used
 * @author nathandunn
 *
 */
public class Objs {

	public static final int DEFAULT_EXTRACT = 100;
	public static final String SERIAL_PATH = "../../SerializedObjects/";
	
	public static final String DATA_PATH = "../../DataSets_R/";
	
	
	public static final Tokenizer tokenizer = createTokenizer();
	
	public static final Tokenizer createTokenizer(){
		Tokenizer out = 
		new Tokenizer((ModelClassifier)Serializer
				.deserialize("GISP2_ModelClassifier"));
		KnnModel knn = out.getModel().getModel();
//		knn.setGamma(0.5);
//		knn.setK(9);
//		knn.setMaxWeight(Double.POSITIVE_INFINITY);
		return out;
	}
	
//	public static final Tokenizer tokenizer = new Tokenizer((ModelClassifier)Serializer
//			.deserialize("GISP2_ModelClassifier_2"));
	
//	public static Tokenizer getTokenizer(){
//		return new Tokenizer((ModelClassifier)Serializer
//				.deserialize("GISP2_ModelClassifier"));
//	}
	
	public static void setEvalSettings(double alpha, double beta, double padding){
		EvaluationObject.EvaluationSettings.DEFAULT = EvaluationSettings.getEvaluationSettings(alpha, beta, padding);
				//new EvaluationSettings(alpha, beta, padding);
	}
	
}
