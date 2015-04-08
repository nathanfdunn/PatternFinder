package ui;

import java.util.Scanner;

public class ManualInputReader implements IInputReader {

	private Scanner scanner;
	
	public ManualInputReader(){
		scanner = new Scanner(System.in);
	}

	public String getInput() {
		String out = scanner.nextLine();
		return out;
	}
	
}
