package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

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

	public JPanel getSymbolPanel() {
		return symbolPanel;
	}

	private static final Dimension prefSize = new Dimension(100,300);//new Dimension(500,300);
	
	
//	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JPanel symbolPanel;
	
	public TokenStreamDisplayer(ArrayList<Token> tokens){
		symbolPanel = new SymbolPanel(tokens);
		this.setLayout(new BorderLayout());
		this.add(symbolPanel, BorderLayout.SOUTH);
		this.add(symbolPanel);
		//this.setPreferredSize(new Dimension(500, 300));
	}
	
	public TokenStreamDisplayer(ArrayList<Token> tokens, double[][] values,
			String quant, double[] partition){
		symbolPanel = new SymbolPanel(tokens);
		this.setLayout(new BorderLayout());
		this.add(symbolPanel, BorderLayout.SOUTH);
		this.add(symbolPanel);
		
		chartPanel = createChartPanel(values, quant, partition);
		//chartPanel.setPreferredSize(prefSize);

		this.add(chartPanel, BorderLayout.NORTH);
//		this.add(chartPanel);
		
//		symbolPanel = new SymbolPanel(tokens);
//		chartPanel = createChartPanel(values, quant, partition);
//		symbolPanel.setPreferredSize(prefSize);
//		chartPanel.setPreferredSize(prefSize);
//		this.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		this.add(chartPanel, gbc);		
//		this.add(symbolPanel, gbc);
//
//		this.setPreferredSize(prefSize);
	}
	
	
	private static ChartPanel createChartPanel(double[][] values, String quant,
			double[] partition){
		
		DefaultXYDataset dataSet = new DefaultXYDataset();
		dataSet.addSeries(quant, values);

		JFreeChart chart  = ChartFactory.createXYLineChart(
				quant, // chart title
				"", // domain axis label
				"", // range axis label
				dataSet,  // initial series
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
		
		for (double mark : partition){
			ValueMarker m = new ValueMarker(mark);
			m.setPaint(Color.black);
			XYPlot xyp = (XYPlot)chart.getPlot();
			xyp.addDomainMarker(m);
		}
		
		double min = Math.min(0, MyMath.getMin(values[1]));
		double max = MyMath.getMax(values[1]);
		double pad = (max-min)/10;
		
		((XYPlot)chart.getPlot()).getRangeAxis().setRange(min, max + pad);
		ChartPanel out = new ChartPanel(chart, false);
		
		out.setMinimumDrawWidth( 0 );
		out.setMinimumDrawHeight( 0 );
		out.setMaximumDrawWidth( 1920 );
		out.setMaximumDrawHeight( 1200 );
		
		return out;
	}

	
	private static class SymbolPanel extends JPanel {
		private static final double xBuffer = 0.4;
		private static final int horizBuffer = 50;
		private static final int vertBuffer = 50;
		
		private double[][][] pointsList;
		private double[][][] linesList;
		private double maxX;
		
		public SymbolPanel(ArrayList<Token> tokens){
			initializePoints(tokens);
			//this.setPreferredSize(new Dimension(500, 300));
		}

		
		private void initializePoints(ArrayList<Token> tokens){
			pointsList = new double[tokens.size()][][];
			linesList = new double[tokens.size()+1][][];
			int i;
			for (i=0; i<= pointsList.length; i++){		//Iterate one extra to draw last line
				
				if (i < pointsList.length){
					double[][] tokePoints = getPoints(tokens.get(i).behavior);
					pointsList[i] = new double[][]{
						MyMath.add(tokePoints[0], //(i+0.5)*(xBuffer+1)),
								i + i*xBuffer + 0.5*xBuffer + 0.5),
						MyMath.add(tokePoints[1], 0.5)
					};
				}
				
				linesList[i] = new double[][]{
						new double[]{i*(1+xBuffer), i*(1+xBuffer)},
						new double[]{0, 1}
				};
			}
			maxX = pointsList.length*(1+xBuffer);
		}


		private int[][][][] scaleValues(){
			int width = this.getWidth() - 2*horizBuffer;
			int height = this.getHeight() - 2*vertBuffer;
			
			int[][][] symbols = new int[pointsList.length][][];
			for (int i=0; i<symbols.length; i++)
				symbols[i] = scaleValues( pointsList[i], horizBuffer, vertBuffer, width, height );
			
			int[][][] lines = new int[linesList.length][][];
			for (int i=0; i<lines.length; i++)
				lines[i] = scaleValues( linesList[i], horizBuffer, vertBuffer, width, height );
			
			return new int[][][][]{symbols, lines};
		}
		
		private int[][] scaleValues(double[][] vals, int hBuff, int vBuff, int width, int height){
			//double xScale = width/maxX;
			int[][] out = new int[][]{
				MyMath.round( MyMath.add( MyMath.mult( vals[0], width/maxX), hBuff )),
				MyMath.round( MyMath.add( MyMath.mult( MyMath.add( MyMath.neg(vals[1]), 1), height ), vertBuffer ))
			};
			return out;
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.white);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.BLACK);
			int[][][][] pointsList = scaleValues();
			drawSymbols(g, pointsList[0]);
			drawLines(g, pointsList[1]);

		}
		
		private void drawSymbols(Graphics g, int[][][] symbolPoints){
			g.setColor(Color.red);
			for (int i=0; i<symbolPoints.length; i++)
				paintLine(g, symbolPoints[i]);
		}
		
		private void drawLines(Graphics g, int[][][] linesPoints){
			g.setColor(Color.black);
			for (int i=0; i<linesPoints.length; i++)
				paintLine(g, linesPoints[i]);
		}
		
		private void paintLine(Graphics g, int[][] points){
			//Pnt.pntArr(points);			//TODO
			for (int i=0; i<points[0].length-1; i++){
				g.drawLine(points[0][i], points[1][i], 
						points[0][i+1], points[1][i+1]);
			}
		}
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
			new double[]{0.2,0.5,0.5,0.2,0.2,-0.2,-0.5}
		};
	}
	
	private static double[][] getSpiPoints(){
		return new double[][]{
			new double[]{-0.5,0.0,0.0, 0.5},
			new double[]{-0.5,0.5,0.5,-0.5}
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
