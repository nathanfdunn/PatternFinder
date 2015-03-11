package behaviorClassification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import patternDetection.Behavior;

//TODO completely refactor
public class UserClassifier {

	private IInputReader inputReader;
	
	public UserClassifier(IInputReader inputReader) {
		this.inputReader = inputReader;
	}
	
	public UserClassifier(){
		inputReader = new InputReader();
	}

	public ClassifiedChunkList classify(ChunkList chunkList){
		//TODO control which quantities to classify?
		ClassifiedChunkList out = new ClassifiedChunkList();
		
		for (String quantity : chunkList.getQuantities()){
			System.out.println("Classifying "+quantity+" values");
			ArrayList<ClassifiedChunk> list = 
					classify(chunkList.getChunks(quantity));
			out.addList(quantity, list);
		}
		
		return out;
	}
	
	public ArrayList<ClassifiedChunk> classify(ArrayList<Chunk> chunks){
		ArrayList<ClassifiedChunk> out = new ArrayList<ClassifiedChunk>();

		boolean go = true;
		int i=0;
		while (go && i<chunks.size()){
			
			System.out.println(chunks.size());
			
			Chunk chunk = cleanChunk(chunks.get(i));
			
			if ( FeatureExtractor.isFeaturizable(chunk) ){
				displayChunk(chunk);
				System.out.println("Please enter best guess of behavior ("+i+")");
				String input = this.inputReader.getInput();
				if (validBehaviors.contains(input)){
					out.add(new ClassifiedChunk(chunk, input));
					i++;
				}else if (controlSequences.contains(input)){
					if ("q".equals(input)){
						go = false;
					}else if ("back".equals(input)){
						if (i > 0){
							out.remove(out.size()-1);
							i--;
						}//else do nothing
						else			//TODO get rid of this
						{
							if (out.size() != 0) throw new Error("Something went wrong");
						}
					}
				}else{
					System.out.println("Invalid entry");
				}
			}else{
				i++;
			}
		}

		return out;
	}
	
	/**
	 * Strips out the NaN values
	 * @param chunk
	 * @return
	 */
	private Chunk cleanChunk(Chunk c){
		ArrayList<Double> outTimes = new ArrayList<Double>();
		ArrayList<Double> outVals = new ArrayList<Double>();
		
		double[] times = c.getTimes();
		double[] vals = c.getVals();
		
		for (int i=0; i<times.length; i++){
			if (!Double.isNaN(times[i]) && !Double.isNaN(vals[i])){
				outTimes.add(times[i]);
				outVals.add(vals[i]);
			}
		}
		
		double[] outTimes2 = new double[outTimes.size()];
		double[] outVals2 = new double[outVals.size()];
		for (int i=0; i<outTimes2.length; i++){
			outTimes2[i] = outTimes.get(i);
			outVals2[i] = outVals.get(i);
		}
		
		return new Chunk( outVals2, outTimes2, c.getStart(), c.getEnd() );
	}

	
	public void displayChunk(Chunk chunk){
		//TODO add GUI components
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
		"q", "back",
	};
	
	private static ArrayList<String> controlSequences = 
			new ArrayList<String>( Arrays.asList(temp) );
	
	private static ArrayList<String> validBehaviors = 
			Behavior.getValidStrings();

	
	public static class InputReader implements IInputReader {
		private Scanner scanner;
		public InputReader(){
			scanner = new Scanner(System.in);
		}

		@Override
		public String getInput() {
			return scanner.nextLine();
		}
	}
}