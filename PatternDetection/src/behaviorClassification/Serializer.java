package behaviorClassification;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class Serializer {

	public static void serialize(Serializable obj, String fileName){
		try{
			fileName = "../../SerializedObjects/" + fileName + ".ser";
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String contents, String fileName){
		try{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			pw.write(contents);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
