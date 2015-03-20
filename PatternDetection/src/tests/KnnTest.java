package tests;

import behaviorClassification.DataTable;
import behaviorClassification.FeatureTable;
import behaviorClassification.KnnModel;
import behaviorClassification.Serializer;

public class KnnTest {

	public static void main(String[] args) {
		evaluateGisp();
		// TODO Auto-generated method stub
		//KnnModel model = new KnnModel();
		
		
	}
	
	
	//Evaluates the GISP KnnModel
	private static void evaluateGisp(){
		KnnModel model = (KnnModel)Serializer.deserialize("GISP2_Model");
		Pnt.pnt(model.evaluateAccuracy());
		
//		int[] ks = new int[30];
//		for (int i=0; i<ks.length; i++) ks[i] = i+1;
		
		double[] gammas = new double[31];
		for (int i=0; i<gammas.length; i++)
			gammas[i] = (double)i/10;
		int[] ks = new int[30];
		for (int i=0; i<ks.length; i++)
			ks[i] = i+1;
		double[] maxWeights = new double[]{
				0.001, 0.01, 0.1, 1.0, 10.0, 100.0, 1000.0, Double.POSITIVE_INFINITY	
		};
		
		DataTable accTable = model.accuracyParameterSweep(gammas, ks, maxWeights);
		Serializer.writeFile(accTable.toString(), "AccTable_g_k_mw4");
		
		//model.setMaxWeight(Double.POSITIVE_INFINITY);
//		DataTable accTable = new DataTable(model.accuracyParameterSweep(gammas, ks), null);
//		System.out.println(accTable);
		
		//Pnt.pntArr();
		
	
	}
	
	private static void basicTest(){
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
				
//				for (int i=0; i<10; i++){
//					Pnt.pnt();
//					Pnt.pntArr(KnnModel.rangeExclude(i, 10));
//				}
				
				Pnt.pnt(model.evaluateAccuracy());
				//pntArr(model.classes);
				
	}
	

	


}
