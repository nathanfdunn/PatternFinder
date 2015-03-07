package behaviorClassification;

public class ModelClassifier {

	private KnnModel model;
	
	public ModelClassifier(KnnModel model){
		this.model = model;
	}
	
	public String classifyChunk(Chunk chunk){
		double[] featureVec = FeatureExtractor.extractFeatures(chunk);
		return model.classify(featureVec);
	}
	
	
	
}
