package behaviorClassification;

public class Classifier {

	private KnnModel model;
	
	public Classifier(KnnModel model){
		this.model = model;
	}
	
	public String classifyChunk(Chunk chunk){
		double[] featureVec = FeatureExtractor.extractFeatures(chunk);
		return model.classify(featureVec);
	}
	
	
	
}