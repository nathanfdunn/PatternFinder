package ui;

public class StdOutAppOutput implements IAppOutput {

	@Override
	public void print(String s) {
		System.out.println(s);
	}
	
}
