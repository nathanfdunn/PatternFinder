package tests;

import gui.ChunkDisplayer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import behaviorClassification.Chunk;

public class ChunkDisplayerTest {

	public static void main(String[] args) {
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ChunkDisplayer cd = new ChunkDisplayer();
		
		double[] vals = new double[]{1,2,4,2,5,8,4,2,6,7,4 ,2 };
		double[] time = new double[]{0,1,2,3,4,5,6,7,8,9,10,11};
		

		win.setContentPane(cd);
		win.pack();
		win.setVisible(true);		
		
		cd.displayChunk(time, vals);

		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		vals = new double[]{8,4,-10,7,5,8,4,2,6,7,4,2};
		time = new double[]{0,1,2,3,4,5,6,7,8,9,10,11};

		cd.displayChunk(time, vals);

		win.dispose();
	}

}
