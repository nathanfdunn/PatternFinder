package tests;

import ui.InputSimulator;
import ui.ManualInputReadRecord;
import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.FeatureExtractor;
import behaviorClassification.ModelClassifier;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.Serializer;
import behaviorClassification.SimplifiedFeatureExtractor;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserChunkClassifier;

public class ClassifierTrainingTest {

	public static void main(String[] args){
//		FeatureExtractor fe = new StandardFeatureExtractor();
		FeatureExtractor fe = new SimplifiedFeatureExtractor();
		ModelClassifier model = new ModelClassifier(classifyAutomatically(), fe);
		overwriteModel(model);
	}
	
	public static void demoManual(){
		Pnt.pnt("Classification Test:");

		FeatureExtractor fe = new StandardFeatureExtractor();
		UserChunkClassifier ucc = new UserChunkClassifier(
				new ManualInputReadRecord("testRecord.txt")
				);
		ModelClassifier model = new ModelClassifier(ucc.classify(getGispData()), fe);
		writeModel(model, "testModel");
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
				new ManualInputReadRecord() ).classify( getGispData() );
	}
	
	private static ClassifiedChunkList classifyAutomatically(){
		return new UserChunkClassifier( 
				new InputSimulator() ).classify( getGispData() );
	}

	private static void writeModel(ModelClassifier model, String fileName){
		Serializer.serialize(model, fileName);
	}
	
	private static void overwriteModel( ModelClassifier classifier ){
//		Serializer.serialize(classifier, "GISP2_ModelClassifier");
		Serializer.serialize(classifier, "GISP2_ModelClassifier_2");
	}
	
//	public static ModelClassifier
	
}
