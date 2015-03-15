package behaviorClassification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * Reads from a file to simulate a user entering commands
 * @author nathandunn
 *
 */
public class InputSimulator implements IInputReader{
	private BufferedReader br;
	private boolean isClosed;
	private ManualInputReader man;
	public InputSimulator(){
		this("record.txt");
	}
	
	public InputSimulator(String fileName){
		man = new ManualInputReader();
		try {
			br = new BufferedReader(new FileReader(fileName));
			isClosed = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getInput() {
		if (isClosed){
			return defaultInput();
		}
		
		String out = null;
		try {
			out = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out == null){
			try {
				br.close();
				isClosed = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return defaultInput();
		}
		System.out.println(out);
		return out;
	}
	private String defaultInput(){
		return (new Scanner(System.in)).nextLine();
		//return "Q";
	}
}