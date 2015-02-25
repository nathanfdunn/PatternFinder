package patternDetection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * @author nathandunn
 * Provides static methods for transforming the intermediary text file of tokens
 *  into a token stream
 */
public class FileTranslator {

	public static TokenStream translateFile(){
		return translateFile("TokenStream.txt");
	}
	
	public static TokenStream translateFile(String fileName) {
		TokenStream out = new TokenStream();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();		//Labels
			
			String[] labels = line.split(" +");
			ArrayList<ArrayList<Token>> tokenLists = new ArrayList<ArrayList<Token>>();
			for (int i=0; i<labels.length; i++){
				tokenLists.add(new ArrayList<Token>());
			}
			int time = 0;
			while ((line = br.readLine()) != null) {
				String[] behaviors = line.split(" +");
				//Assert behaviors.length == labels.length
				for (int i=0; i<behaviors.length; i++){
					Token t = new Token(labels[i], translate(behaviors[i]), time);
					tokenLists.get(i).add(t);
				}
				time++;
			}
			
			for (int i=0; i<labels.length; i++){
				out.add(labels[i], tokenLists.get(i));
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}
	
	private static Behavior translate(String rep){
		if ("SPIKE".equals(rep)){
			return Behavior.SPIKE;
		}else if ("INC".equals(rep)){
			return Behavior.INC;
		}else if ("FLAT".equals(rep)){
			return Behavior.FLAT;
		}else if ("DEC".equals(rep)){
			return Behavior.DEC;
		}else if ("DIP".equals(rep)){
			return Behavior.DIP;
		}else{	//if "NULL_".equals(rep)){
			return Behavior.UNK;
		}
	}
}