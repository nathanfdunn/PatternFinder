package behaviorClassification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Reads input from the user using standard input. Can record user's input in
 *  a file to be used with InputSimulator later
 * @author nathandunn
 *
 */
public class ManualInputReader implements IInputReader {
	private Scanner scanner;
	private String fileName;
	
	public ManualInputReader(){
		this(null);
	}
	
	public ManualInputReader(String fileName){
		this.fileName = fileName;
		scanner = new Scanner(System.in);
	}

	@Override
	public String getInput() {
		String out = scanner.nextLine();
		if (fileName != null)
			record(out);
		return out;
	}
	
	private void record(String s){		
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			file.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file != null) file.close();
	}
}
