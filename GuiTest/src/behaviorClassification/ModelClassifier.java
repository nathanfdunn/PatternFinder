package behaviorClassification;

import patternDetection.Behavior;

/**
 * 
 * @author nathandunn
 *
 */
public class ModelClassifier {

	private KnnModel model;
	private FeatureExtractor fe;
	

	public ModelClassifier(KnnModel model){
		this(model, new StandardFeatureExtractor());
	}
	
	public ModelClassifier(KnnModel model, FeatureExtractor fe) {
		this.model = model;
		this.fe = fe;
	}



	public Behavior classifyChunkBehavior(Chunk chunk){
		return Behavior.translate(classifyChunkString(chunk));
	}
	
	public String classifyChunkString(Chunk chunk){
		chunk = Chunk.cleanChunk(chunk);
		if (FeatureExtractor.isFeaturizable(chunk)){
			double[] featureVec = fe.extractFeatures(chunk);
			return model.classify(featureVec);
		}else{
			return Behavior.UNK.toString();
		}
	}
}
