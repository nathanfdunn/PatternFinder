package behaviorClassification;

import java.io.Serializable;

/**
 * A Chunk that stores the qualitative behavior of the time sub-series
 *  contained within
 * @author nathandunn
 *
 */
public class ClassifiedChunk implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private Chunk rawChunk;				//The Chunk whose behavior is classified
	private String classification;		//The qualitative behavior of the quantity
										// stored in rawChunk	

	public ClassifiedChunk(Chunk rawChunk, String classification) {
		this.rawChunk = rawChunk;
		this.classification = classification;
	}

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
