package tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ui.IInputReader;
import ui.InputSimulator;
import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;
import behaviorClassification.UserChunkClassifier;

public class SerializationTest {
	//TODO actually test the Serializer class
	public static void main(String[] args) {
		RawTimeSeriesTable rdt = CsvToTable.readCsv("../../DataSets_R/nfdunn_Moulton1.csv");
		ChunkList cl = new ChunkList(rdt, 30);
		System.out.println(cl.getNumChunks());
		System.out.println(cl);
		
		ClassifiedChunkList ccl = new UserChunkClassifier(new InputSimulator()).classify(cl);
		System.out.println(ccl);

		try{
			FileOutputStream fileOut = new FileOutputStream("../../SerializedObjects/ccl.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(ccl);
			out.close();
			fileOut.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ClassifiedChunkList retrievedCcl = null;
		try{
	         FileInputStream fileIn = new FileInputStream("../../SerializedObjects/ccl.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         retrievedCcl = (ClassifiedChunkList)in.readObject();
	         in.close();
	         fileIn.close();
	         
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(retrievedCcl);
		
	}
}
