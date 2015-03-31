package behaviorClassification;

import java.io.Serializable;

import patternDetection.Behavior;


//TODO: merge with KnnModel
/**
 * 
 * @author nathandunn
 *
 */
public class ModelClassifier implements Serializable {

	private static final long serialVersionUID = 3762260568579015905L;
	
	private KnnModel model;
	private FeatureExtractor fe;
	

	public ModelClassifier(ClassifiedChunkList ccl){
		this(new KnnModel(ccl));
	}
	
	public ModelClassifier(ClassifiedChunkList ccl, FeatureExtractor fe){
		this(new KnnModel(ccl, fe), fe);
	}
	
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
