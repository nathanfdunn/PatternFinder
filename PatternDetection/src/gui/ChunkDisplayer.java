package gui;

//import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
//import org.jfree.chart.*;
//import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import behaviorClassification.Chunk;
import behaviorClassification.MyMath;

//import org.jfree.chart.plot.XYPlot;

/**
 * A GUI component to display a graph of time series data for a 
 *  a Chunk of data. Gives users visual feedback when manually
 *  classifying Chunks.
 * @author nathandunn
 *
 */
public class ChunkDisplayer extends JPanel{
	
	private static final long serialVersionUID = -6970537554754880572L;
	
	public static final Dimension prefSize = new Dimension(600, 600);
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	
	public ChunkDisplayer(){
		this.setPreferredSize(prefSize);
	}
	
	public void displayChunk(Chunk chunk){
		displayChunk(chunk.getTimes(), chunk.getVals());
	}
	
	public void displayChunk(double[] time, double[] vals){
		
		DefaultXYDataset dataSet = new DefaultXYDataset();
		dataSet.addSeries("Values", new double[][]{
				time,
				vals
		});
		
		chart = ChartFactory.createXYLineChart(
				"", // chart title
				"", // domain axis label
				"", // range axis label
				dataSet,  // initial series
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
		
		if (chartPanel != null)
			this.remove(chartPanel);
		
		double min = Math.min(0, MyMath.getMin(vals));
		double max = MyMath.getMax(vals);
		double pad = (max-min)/10;
		
		((XYPlot)chart.getPlot()).getRangeAxis().setRange(
				min,
				max + pad
				);
		chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(prefSize);

		this.add(chartPanel);
		this.revalidate();

	}

}
