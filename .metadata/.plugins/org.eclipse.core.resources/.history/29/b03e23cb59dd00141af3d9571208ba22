package behaviorClassification;

import gui.ChunkDisplayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

import patternDetection.Behavior;

//TODO completely refactor
public class UserChunkClassifier {

	private IInputReader inputReader;
	private JFrame window;
	private ChunkDisplayer cd;
	
	
	public UserChunkClassifier(IInputReader inputReader) {
		this.inputReader = inputReader;
		this.window = new JFrame();
		this.cd = new ChunkDisplayer();
		window.setContentPane(cd);
		window.setPreferredSize(ChunkDisplayer.prefSize);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
	}
	
	public UserChunkClassifier(){
		this(new ManualInputReader());
	}

	public ClassifiedChunkList classify(ChunkList chunkList){
		//TODO control which quantities to classify?
		ClassifiedChunkList out = new ClassifiedChunkList();
		
		this.window.setVisible(true);
		
		for (String quantity : chunkList.getQuantities()){
			System.out.println("Classifying "+quantity+" values");
			ArrayList<ClassifiedChunk> list = 
					classify(chunkList.getChunks(quantity));
			out.addList(quantity, list);
		}
		
		this.window.dispose();
		return out;
	}
	
	private static ArrayList<Chunk> cleanChunks(ArrayList<Chunk> chunks){
		ArrayList<Chunk> out = new ArrayList<Chunk>();
		for (Chunk chunk : chunks){
			Chunk clean = Chunk.cleanChunk(chunk);
			
			//TODO, fix this so that it doesn't depend on the FeatureExtractor,
			// and so that it won't interfere with the classification
			if (FeatureExtractor.isFeaturizable(clean))
				out.add(clean);
		}
		return out;
	}
	
	public ArrayList<ClassifiedChunk> classify(ArrayList<Chunk> chunks){
		ArrayList<ClassifiedChunk> out = new ArrayList<ClassifiedChunk>();
		
		chunks = cleanChunks(chunks);

		boolean go = true;
		int i=0;
		while (go && i<chunks.size()){
			Chunk chunk = chunks.get(i);
			displayChunk(chunk);
			System.out.println("Please enter best guess of behavior ("+i+")");
			//TODO: add best guess by system
			String input = inputReader.getInput();
			if (validBehaviors.contains(input)){
				out.add(new ClassifiedChunk(chunk, input));
				i++;
			}else if (controlSequences.contains(input)){
				if ("Q".equals(input)){
					go = false;
				}else if ("BACK".equals(input)){
					if (i > 0){
						out.remove(out.size()-1);
						i--;
					}/*TODO remove*/else if (out.size() != 0) throw new Error("Something went wrong");
				}
			}else{
				System.out.println("Invalid entry");
				System.out.println("Valid entries are: "+validBehaviors);
			}
			
		}


		ArrayList<ClassifiedChunk> out2 = new ArrayList<ClassifiedChunk>();
		for (ClassifiedChunk cc : out)
			if (!cc.getClassification().equals("UNK"))
				out2.add(cc);
		
		
		
		return out2;
	}
	
//	/**
//	 * Strips out the NaN values
//	 * @param chunk
//	 * @return
//	 */
//	private static Chunk cleanChunk(Chunk c){
//		ArrayList<Double> outTimes = new ArrayList<Double>();
//		ArrayList<Double> outVals = new ArrayList<Double>();
//		
//		double[] times = c.getTimes();
//		double[] vals = c.getVals();
//		
//		for (int i=0; i<times.length; i++){
//			if (!Double.isNaN(times[i]) && !Double.isNaN(vals[i])){
//				outTimes.add(times[i]);
//				outVals.add(vals[i]);
//			}
//		}
//		
//		double[] outTimes2 = new double[outTimes.size()];
//		double[] outVals2 = new double[outVals.size()];
//		for (int i=0; i<outTimes2.length; i++){
//			outTimes2[i] = outTimes.get(i);
//			outVals2[i] = outVals.get(i);
//		}
//		
//		return new Chunk( outVals2, outTimes2, c.getStart(), c.getEnd() );
//	}

	
	public void displayChunk(Chunk chunk){
		this.cd.displayChunk(chunk);
	}
	
//	private ClassifiedChunk classify( Chunk chunk ){
//		String classification = getClassification(chunk);
//		return new ClassifiedChunk(chunk, classification);
//	}


	//TODO: remove
//	private String getClassification(Chunk chunk){
//		return "None";
//	}
	


	private static String[] temp = new String[]{
		"Q", "BACK",
	};
	
	private static ArrayList<String> controlSequences = 
			new ArrayList<String>( Arrays.asList(temp) );
	
	private static ArrayList<String> validBehaviors = 
			Behavior.getValidStrings();

}
