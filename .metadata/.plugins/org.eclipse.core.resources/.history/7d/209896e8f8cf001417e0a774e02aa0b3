package tests;

import gui.TokenStreamDisplayer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.Behavior;
import patternDetection.Token;

public class TokenStreamDisplayerTest {

	public static void main(String[] args) {

		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(new BorderLayout());
		
		TokenStreamDisplayer tsd = new TokenStreamDisplayer(testList(), testVals(),
				"Blah", partition());
		//win.add(tsd, BorderLayout.CENTER);
		//win.add(tsd.getSymbolPanel(), BorderLayout.CENTER);
		//win.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
		win.setLayout(new GridLayout(10,1)); // 0,1
		
		for (int i=0; i<10; i++)
			//			win.add(new TokenStreamDisplayer(testList()));//,gbc);
			win.add(new TokenStreamDisplayer(testList(), testVals(),
					"Blah" + i, partition()));
		
		win.pack();
		win.setVisible(true);

	}
	
	private static ArrayList<Token> testList(){
		ArrayList<Token> out = new ArrayList<Token>();
		out.add(new Token("",Behavior.DIP,0));
		out.add(new Token("",Behavior.DEC,1));
		out.add(new Token("",Behavior.FLA,2));
		out.add(new Token("",Behavior.INC,3));
		out.add(new Token("",Behavior.SPI,4));
		out.add(new Token("",Behavior.UNK,5));
		
		return out;
	}
	
	private static double[][] testVals(){
		return new double[][]{
			new double[]{1,2,3,4,5,6,7,8,9,10},
			new double[]{67,43,6,8,5,3,2,2,67,32}
		};
	}

	private static double[] partition(){
		return new double[]{
			3, 6, 9
		};
	}
}
