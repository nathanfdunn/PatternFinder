package behaviorClassification;

public class ModelClassifier {

	private KnnModel model;
	private FeatureExtractor fe;
	
	public ModelClassifier(KnnModel model){
		this.model = model;
		this.fe = new StandardFeatureExtractor();
	}
	
	public String classifyChunk(Chunk chunk){
		double[] featureVec = fe.extractFeatures(chunk);
		return model.classify(featureVec);
	}
	
	
	
}
