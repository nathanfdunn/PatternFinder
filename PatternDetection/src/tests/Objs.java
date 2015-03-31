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

	public static final Tokenizer toke = new Tokenizer((ModelClassifier)Serializer
			.deserialize("GISP2_ModelClassifier"));
	
	public static Tokenizer getTokenizer(){
		return new Tokenizer((ModelClassifier)Serializer
				.deserialize("GISP2_ModelClassifier"));
	}
	
	
	
}
