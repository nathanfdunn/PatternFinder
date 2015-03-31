package tests;

import patternDetection.Tokenizer;
import behaviorClassification.ModelClassifier;
import behaviorClassification.Serializer;

/**
 * Used to get a whole bunch of objects that are regularly used
 * @author nathandunn
 *
 */
public class Objs {

	public static final String SERIAL_PATH = "../../SerializedObjects/";
	
	public static final String DATA_PATH = "../../DataSets_R/";
	
	
	public static final Tokenizer tokenizer = new Tokenizer((ModelClassifier)Serializer
			.deserialize("GISP2_ModelClassifier"));
	
//	public static Tokenizer getTokenizer(){
//		return new Tokenizer((ModelClassifier)Serializer
//				.deserialize("GISP2_ModelClassifier"));
//	}
	
	
	
}
