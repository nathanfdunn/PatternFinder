package ui;

import patternDetection.EvaluationObject;
import patternDetection.EvaluationObject.EvaluationSettings;
import tests.Objs;
import ui.AppVar.AppDouble;
import ui.AppVar.AppInt;
import ui.AppVar.AppString;

public class PatternCommandApp extends CommandApp {

	private static final String DATA_PATH_VAR_NAME = "$__DATA";
	private static final String DEFAULT_DATA_PATH = Objs.DATA_PATH;
	
	private static final String SAVE_PATH_VAR_NAME = "$__SAVE";
	private static final String DEFAULT_SAVE_PATH = Objs.SERIAL_PATH;
	
	private static final String SETUP_FILE = "setup.txt";
	
	private static final String ALPHA_VAR_NAME = "$__ALPHA";
	private static final String BETA_VAR_NAME = "$__BETA";
	private static final String PAD_VAR_NAME = "$__PAD";
	
	private static final String NUM_PATTERNS = "$__EXT_NUM";

	
	//	private static final String 
	
//	protected Tokenizer tokenizer;
	
	
	public PatternCommandApp(){
		super();
		this.addFunctions(PatternAppFunctions.getPatternAppFunctions(this));
		this.initRun();
		this.tokenizer = Objs.tokenizer;
//		System.out.println(this.tokenizer);
	}
	
	public PatternCommandApp(IInputReader in, IAppOutput out){
		super(in, out);
		this.addFunctions(PatternAppFunctions.getPatternAppFunctions(this));
		this.initRun();
		this.tokenizer = Objs.tokenizer;
		
	}
	
	protected void initRun(){
		this.runFile(SETUP_FILE);
	}
	
	
	
	
	
	protected void initializeVars(){
		super.initializeVars();
		AppString dataPath = new AppString(DEFAULT_DATA_PATH);
		AppString savePath = new AppString(DEFAULT_SAVE_PATH);
		this.setVar(DATA_PATH_VAR_NAME, dataPath);
		this.setVar(SAVE_PATH_VAR_NAME, savePath);
		AppDouble alpha = new AppDouble(EvaluationObject.EvaluationSettings.DEFAULT_ALPHA);
		AppDouble beta = new AppDouble(EvaluationObject.EvaluationSettings.DEFAULT_BETA);
		AppDouble padding = new AppDouble(EvaluationObject.EvaluationSettings.DEFAULT_PADDING);
		this.setVar(ALPHA_VAR_NAME, alpha);
		this.setVar(BETA_VAR_NAME, beta);
		this.setVar(PAD_VAR_NAME, padding);
		
		AppInt numPatterns = new AppInt(Objs.DEFAULT_EXTRACT);
		this.setVar(NUM_PATTERNS, numPatterns);
	}
	
	protected int getNumPatterns(){
		return INT.convert(this.getVar(NUM_PATTERNS));
	}
	
	protected EvaluationSettings getEvalSettings(){
		double alpha = DOUBLE.convert(this.getVar(ALPHA_VAR_NAME));
		double beta = DOUBLE.convert(this.getVar(BETA_VAR_NAME));
		double padding = DOUBLE.convert(this.getVar(PAD_VAR_NAME));
		return EvaluationSettings.getEvaluationSettings(alpha, beta, padding);
	}
	
	protected String getDataPath(){
		return STR.convert(this.getVar(DATA_PATH_VAR_NAME));
	}
	
	protected String getSavePath(){
		return STR.convert(this.getVar(SAVE_PATH_VAR_NAME));
	}
	

}
