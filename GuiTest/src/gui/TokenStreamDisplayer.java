package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;


import behaviorClassification.MyMath;
import patternDetection.Behavior;
import patternDetection.Token;
import tests.Pnt;
import behaviorClassification.MyMath;


/**					//TODO revise notation
 * A class to display a TokenStream for just one quantity
 * @author nathandunn
 *
 */
public class TokenStreamDisplayer extends JPanel {
//
//	private JFreeChart chart;
//	private ChartPanel chartPanel;
	private JPanel symbolPanel;
	private double[][][] symbolPoints;
	
	public TokenStreamDisplayer(ArrayList<Token> tokens){
		symbolPanel = new SymbolPanel(tokens);
		this.add(symbolPanel);
		this.setPreferredSize(new Dimension(500, 300));
	}
	
//	private static ChartPanel createChartPanel(ArrayList<Token> tokens){
//		
//	}
	
//	private static JPanel createSymbolPanel(ArrayList<Token> tokens){
//		
//	}
	
	
	private static class SymbolPanel extends JPanel {
		
		private double[][][] pointsList;
		
		public SymbolPanel(ArrayList<Token> tokens){
			pointsList = new double[tokens.size()][][];
			for (int i=0; i<tokens.size(); i++){
				pointsList[i] = getPoints(tokens.get(i).behavior);
			}
		}
		
		public void paintComponent(Graphics g){
			System.out.println("Drawing");
			int[][][] pointsList = scale();
			for (int i=0; i<pointsList.length; i++)
				paintLine(g, pointsList[i]);
		}
		private void paintLine(Graphics g, int[][] points){
			//Pnt.pntArr(points);
			for (int i=0; i<points[0].length-1; i++){
				g.drawLine(points[0][i], points[1][i], 
						points[0][i+1], points[1][i+1]);
			}
		}
		
		private int[][][] scale(){
			int xBuffer = 20;
			int yBuffer = 20;
			int width = this.getWidth() - 2*xBuffer;
			int height = this.getHeight() - 2*yBuffer;

			final double xOffset = 1.2;
			final double staticXOffset = 0.5 + (xOffset-1)/2;

			final double staticYOffset = 0.5;
			
			double maxX = pointsList.length*xOffset;
			double xScale = maxX / width;
			
			double yScale = height;
			
			//double[][][] out = new double[pointsList.length][][];
			int[][][] out = new int[pointsList.length][][];
			for (int i=0; i<out.length; i++){
				double[] newX = pointsList[i][0];
				newX = MyMath.add(newX, staticXOffset + i*xOffset);
				newX = MyMath.mult(newX, xScale);
				
				double[] newY = pointsList[i][1];
				newY = MyMath.mult(newY, -1);	//TODO y reversal?
				newY = MyMath.add(newY, staticYOffset);
				newY = MyMath.mult(newY, yScale);

				out[i] = new int[][]{MyMath.round(newX), MyMath.round(newY)};
			}
			
			int[][][] lines = divLines(height, width, pointsList.length, xBuffer, yBuffer, xOffset);
			
//			int[][][] out2 = new int[out.length + lines.length][][];
//			
//			for (int i=0; i<out.length; i++)
//				out2[i] = out[i];
//			for (int i=0; i<lines.length; i++)
//				out2[i+out2.length] = lines[i];
//			return out2;
			return out;
		}
		
		private int[][][] divLines( double height, double width, int num,
				double xBuffer, double yBuffer, double xOffset ){
//			int h = MyMath.round(height);
//			int w = MyMath.round(width);
			int[][][] out = new int[num+1][][];
			for (int i=0; i<=num; i++){
				out[i] = new int[][]{
					new int[]{MyMath.round(i*xOffset+xBuffer), MyMath.round(i*xOffset+xBuffer)},
					new int[]{MyMath.round(yBuffer), MyMath.round(height-yBuffer)}
				};
			}
			return out;
		}
		
//		private void paintSymbol(Graphics g, double[] points, int offset){
//			
//		}
	}
	
	private static double[][][] translate(Behavior[] behaviors){
		double[][][] out = new double[behaviors.length][][];
		for (int i=0; i<behaviors.length; i++)
			out[i] = getPoints( behaviors[i] );
		return out;
	}
	
	private static double[][] getPoints( Behavior b ){
		if (b == Behavior.DIP)
			return getDipPoints();
		if (b == Behavior.DEC)
			return getDecPoints();
		if (b == Behavior.FLA)
			return getFlaPoints();
		if (b == Behavior.INC)
			return getIncPoints();
		if (b == Behavior.SPI)
			return getSpiPoints();
		if (b == Behavior.UNK)
			return getUnkPoints();
		throw new Error("Unrecognized Behavior");
	}
	
	private static double[][] getUnkPoints(){
		return new double[][]{
			new double[]{-0.5,0,0,0.5,0.5,0,0},
			new double[]{0,0.5,0.5,0,0,-0.5,-0.75}
		};
	}
	
	private static double[][] getSpiPoints(){
		return new double[][]{
			new double[]{-0.5,0.5,0.5,-0.5},
			new double[]{-0.5,0.0,0.0,-0.5}
		};
	}
	private static double[][] getDipPoints(){
		return flipY( getSpiPoints() );
	}
	
	private static double[][] getIncPoints(){
		return flipY( getDecPoints() );
	}
	private static double[][] getDecPoints(){
		return new double[][]{
			new double[]{-0.5, 0.5},
			new double[]{-0.5, 0.5}
		};
	}
	private static double[][] getFlaPoints(){
		return new double[][]{
			new double[]{-0.5, 0.5},
			new double[]{0, 0}
		};
	}
	
	private static double[][] flipY(double[][] points){
		double[] y = points[1];
		for (int i=0; i<y.length; i++)
			y[i] = -y[i];
		return points;
	}
}
