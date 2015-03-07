package behaviorClassification;

public class ClassifiedChunk {
	private Chunk rawChunk;
	private String classification;
	private double[] featureVec;
	
	public ClassifiedChunk(Chunk rawChunk, double[] featureVec, String classification) {
		super();
		this.rawChunk = rawChunk;
		this.featureVec = featureVec;
		this.classification = classification;
	}
	
	
//	public Chunk getRawChunk() {
//		return rawChunk;
//	}
	public String getClassification() {
		return classification;
	}





	
	
	
}
