package ui;

import java.util.ArrayList;
import java.util.HashMap;

import patternDetection.SimpleTokenStream;
//import ui.AppVar.AppNull;
import ui.AppVar.*;
//import static ui.CommandAppUtil.argTypeCheck;
import ui.CommandAppUtil;



public class CommandApp {
	
	public static final AppNull NULL = AppVar.NULL;
	public static final AppString STR = AppVar.STR;
	public static final AppBool BOOL = AppVar.BOOL;
	public static final AppInt INT = AppVar.INT;
	public static final AppDouble DOUBLE = AppVar.DOUBLE;
	

	protected IInputReader in;
	protected boolean quit;
	protected HashMap<String, AppVar<? extends Object>> variables;
	protected IAppOutput out;
	protected boolean echo = true;
	
	
	
	
//	private static final AppVar<? extends Object> 
	
//	private static final AppNull NULL = AppVar.NULL;

	public CommandApp(){
		this( new ManualInputReader(), new StdOutAppOutput());
	}
	
	public CommandApp(IInputReader in, IAppOutput out){
		this.in = in;
		this.out = out;
		quit = false;
		variables = new HashMap<String, AppVar<? extends Object>>();
	}
	
	private void intro(){
		out.print("Welcome to the PEAM app");
	}
	
	public void run(){
		intro();
		while (!quit){
			String cmd = in.getInput();
			AppVar<? extends Object> res = eval(cmd);
			if (echo)
				printFunction(res);
		}
	}
	
	//Cannot nest functions
	public AppVar<? extends Object> eval(String s){
		
		int lParenInd = s.indexOf('(');
		int rParenInd = s.lastIndexOf(')');
		
		if (lParenInd == -1 || rParenInd == -1){
			return atomicEval(s);
		}
		
		if (lParenInd != 0 || rParenInd != s.length()-1){
			showError("Bad command: "+s);
			return NULL;
		}
		s = s.substring(lParenInd+1, rParenInd);

		String[] strings = s.split("\\s+");
		if (strings.length==0){
			showError("Empty function call");
			return NULL;
		}
		String functionName = strings[0];
		if (functionName.equals("set")){
			return setFunction(strings);
		}
		
		ArrayList<AppVar<? extends Object>> args = 
				new ArrayList<AppVar<? extends Object>>();
		for (int i=1; i<strings.length; i++){
			AppVar<? extends Object> arg = atomicEval(strings[i]);
			if (arg == NULL){
				showError("Function "+functionName + " failed");
				return NULL;
			}
			args.add(arg);
		}
		return callFunction(functionName, args);
		
	}
	
	protected AppVar<? extends Object> callFunction(String name, 
			ArrayList<AppVar<? extends Object>> args ){
		if (name.equals("quit"))
			return quitFunction(args);
		if (name.equals("print"))
			return printFunction(args);
		if (name.equals("setEcho"))
			return setEcho(args);
		
		showError("Unrecognized function");
		return NULL;
	}
	

	
	protected AppVar<? extends Object> quitFunction(ArrayList<AppVar<? extends Object>> args){ 
		if (args.size() != 0) showError("Unused arguments in 'quit'");
		quit();
		return NULL;
	}
	
	protected AppVar<? extends Object> setEcho(ArrayList<AppVar<? extends Object>> args){
		String check = CommandAppUtil.argTypeCheck(args, BOOL);
		if (check.equals("")){
			this.echo = BOOL.convert(args.get(0));
		}
		return NULL;
	}
	
	protected AppVar<? extends Object> setFunction(String[] args){ 
		if (args.length != 3){
			showError("'set' takes exactly 2 arguments");
			return NULL;
		}else
			return set(args[1], atomicEval(args[2]));
	}
	
	protected AppVar<? extends Object> printFunction(ArrayList<AppVar<? extends Object>> args){
		for (AppVar<? extends Object> arg : args )
			out.print(arg.toString());
		return NULL;
	}
	
	protected void printFunction(AppVar<? extends Object> arg ){
		if (arg != NULL)
			out.print(arg.toString());
	}
	
	
	private AppVar<? extends Object> set(String varName, AppVar<? extends Object> obj ){
		if (CommandAppUtil.isValidVarName(varName)){
			if (obj != NULL){
				variables.put(varName, obj);
				return obj;
			}else{
				showError("Variable "+varName + " cannot be assigned a NULL");
				return NULL;
			}
		}
		showError("Invalid LHS (Bad variable name)");
		return NULL;
	}
	
	
	
	public void quit(){
		quit = true;
	}
	
//	private AppVar<SimpleTokenStream> tokenize()
	
	
	
	
	/**
	 * Evaluates either a literal or a variable
	 * @param s
	 * @return
	 */
	public AppVar<? extends Object> atomicEval(String s){
		
		
		if (CommandAppUtil.isValidVarName(s)){
			return varEval(s);
		}
		
		AppVar<? extends Object> out;

//		AppVar<? extends Object> out = varEval(s);
//		if (out != NULL) return out;
		out = stringLitEval(s);
		if (out != NULL) return out;
		out = intLitEval(s);
		if (out != NULL) return out;
		out = doubleLitEval(s);
		if (out != NULL) return out;
		out = boolLitEval(s);
		if (out != NULL) return out;
		
		showError("Unknown atomic expression: "+s);
		return NULL;
	}
	
	
	private AppVar<? extends Object> varEval(String s){ 
		
		if (isVariable(s)) {
			return variables.get(s);
		} else {
			if (CommandAppUtil.isValidVarName(s))
				showError("Variable '"+s+"' is undefined");
			return AppNull.NULL;
		}
	}
	
	private AppVar<? extends Object> stringLitEval(String s){ 
		int quoteInd = s.indexOf('"');
		int quoteInd2 = s.lastIndexOf('"');
		if (quoteInd == 0 && quoteInd2 == s.length()-1 && quoteInd!=quoteInd2){
			return new AppString(s.substring(quoteInd+1, quoteInd2));
		}else{
			return NULL;
		}
	}

	private AppVar<? extends Object> boolLitEval(String s){
		if (s.equals("true"))
			return AppBool.TRUE;
		if (s.equals("false"))
			return AppBool.FALSE;
		return NULL;
	}
	
	private AppVar<? extends Object> intLitEval(String s){
		try{
			Integer i = Integer.parseInt(s);
			return new AppInt(i);
		}catch(NumberFormatException e){
			return NULL;
		}
	}
	
	private AppVar<? extends Object> doubleLitEval(String s){
		try{
			Double d = Double.parseDouble(s);
			return new AppDouble(d);
		}catch(NumberFormatException e){
			return NULL;
		}
	}
	
	private boolean isVariable(String name){
		return variables.containsKey(name);
	}
	
	public void showWarning(String warningMessage){
		out.print("Warning: "+warningMessage);
	}
	
	public void showError(String errorMessage){
		out.print("Error: "+errorMessage);
	}
	
}




