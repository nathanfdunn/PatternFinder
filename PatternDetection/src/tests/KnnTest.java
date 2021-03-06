package tests;

import behaviorClassification.FeatureTable;
import behaviorClassification.KnnModel;

public class KnnTest {

	public static void main(String[] args){
		//String[] headers = new String[]{"V1","V2"};//,"V2"};
		double[][] train = new double[][]{
				new double[]{5,  6,  4,  6,  7,  -5,  -3,  -2,  -1 },	//V1 features
				new double[]{20, 30, 40, 50, 10, -15, -20, -30, -20},	//V2 features
				//new double[]{}			//V3 features
		};
		String[] classifications = new String[]
				{"++","++","++","++","++","--","--","--","--"};

		double[] test1 = new double[]{-6, 0};	//should be --
		double[] test2 = new double[]{4, 40};	//should be ++

		FeatureTable trainingData = new FeatureTable(train, new String[2], classifications);
		KnnModel model = new KnnModel(trainingData);

		Pnt.pnt(model.getTrainingData());

		int[] inds = new int[]{1,4,3};
		KnnModel model2 = new KnnModel(trainingData.subTable(inds));
		Pnt.pnt(model2.getTrainingData());


		//model.train(trainingData, classifications);
		//model.setGamma(1);
		model.setK(5);
		System.out.println(model.classify(test1));
		System.out.println(model.classify(test2));

		//					for (int i=0; i<10; i++){
		//						Pnt.pnt();
		//						Pnt.pntArr(KnnModel.rangeExclude(i, 10));
		//					}

		Pnt.pnt(model.evaluateAccuracy());
		//pntArr(model.classes);

	}
}
