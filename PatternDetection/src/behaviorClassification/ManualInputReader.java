package behaviorClassification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class ManualInputReader implements IInputReader {
	private Scanner scanner;
	public ManualInputReader(){
		scanner = new Scanner(System.in);
	}

	@Override
	public String getInput() {
		String out = scanner.nextLine();
		record(out);
		return out;
	}
	
	private void record(String s){
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter("record.txt", true)));
			file.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file != null) file.close();
	}
}