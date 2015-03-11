package behaviorClassification;

import java.io.Serializable;

public class ClassifiedChunk implements Serializable {

	
	private static final long serialVersionUID = 2L;
	
	private Chunk rawChunk;
	private String classification;
	//private double[] featureVec;
	
	

	public ClassifiedChunk(Chunk rawChunk, String classification) {
		super();
		this.rawChunk = rawChunk;
		this.classification = classification;
	}

	
//	public ClassifiedChunk(Chunk rawChunk, double[] featureVec, String classification) {
//		super();
//		this.rawChunk = rawChunk;
//		this.featureVec = featureVec;
//		this.classification = classification;
//	}
	
	
//	public Chunk getRawChunk() {
//		return rawChunk;
//	}
	public String getClassification() {
		return classification;
	}

	public Chunk getRawChunk(){
		return rawChunk;
	}

	public String toString(){
		return classification + "\n" + rawChunk;
	}

	
	
	
}
