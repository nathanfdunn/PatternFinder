package tests;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.InputSimulator;
import behaviorClassification.ManualInputReader;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.Serializer;
import behaviorClassification.UserChunkClassifier;

public class ClassifierTrainingTest {

	public static void main(String[] args){
		ModelClassifier model = new ModelClassifier(classifyAutomatically());
		overwriteModel(model);
	}
	
	public static ModelClassifier getStandardModel(){
		return (ModelClassifier)Serializer.deserialize("GISP2_ModelClassifier");
	}
	
	private static ChunkList getGispData(){
		RawTimeSeriesTable gispData = CsvToTable.readCsv("../../DataSets_R/nfdunn_GISP2.csv");
		return new ChunkList(gispData, 100);
	}
	
	private static ClassifiedChunkList classifyManually(){
		return new UserChunkClassifier( 
				new ManualInputReader() ).classify( getGispData() );
	}
	
	private static ClassifiedChunkList classifyAutomatically(){
		return new UserChunkClassifier( 
				new InputSimulator() ).classify( getGispData() );
	}

	
	private static void overwriteModel( ModelClassifier classifier ){
		Serializer.serialize(classifier, "GISP2_ModelClassifier");
	}
	
//	public static ModelClassifier
	
}
