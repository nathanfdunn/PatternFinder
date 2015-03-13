package mainFeatures;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.DataTable;
import behaviorClassification.FeatureTable;
import behaviorClassification.InputSimulator;
import behaviorClassification.KnnModel;
import behaviorClassification.ManualInputReader;
import behaviorClassification.RawDataTable;
import behaviorClassification.Serializer;
import behaviorClassification.StandardFeatureExtractor;
import behaviorClassification.UserClassifier;

public class TrainClassifier {

	public static void main(String[] args) {
		//RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
		RawDataTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_GISP2.csv");
		//System.out.println("table good");

		//System.out.println(rdt);
		
		ChunkList cl = new ChunkList(rdt, 100);
		System.out.println("chunking good");

		//ClassifiedChunkList ccl = new UserClassifier(new ManualInputReader()).classify(cl);
		ClassifiedChunkList ccl = new UserClassifier(
				new InputSimulator()
				).classify(cl);

		System.out.println("classify good");
		Serializer.serialize(ccl, "GISP2_Chunks");
		
		FeatureTable table = new FeatureTable(ccl, new StandardFeatureExtractor());
		Serializer.writeFile(table.toString(), "GISP2_FeatTable");
		KnnModel model = new KnnModel(table);
		System.out.println(model.evaluateAccuracy());
		
		double[] gammas = new double[11];
		for (int i=0; i<gammas.length; i++)
			gammas[i] = (double)i/5;
		int[] ks = new int[30];
		for (int i=0; i<ks.length; i++)
			ks[i] = i;
		DataTable accTable = new DataTable(model.accuracyParameterSweep(gammas, ks));
		System.out.println(accTable);
		
	}

}
