package ui;

import java.util.ArrayList;


//TODO remove class

/**
 * Really a function call
 * @author nathandunn
 *
 */
public class AppExpression extends AppVar<String>{
	
	private String funName;
	private ArrayList<AppVar<? extends Object>> args;
	
	public AppExpression(String s){
		super(s);
		
	}

	@Override
	public String getType() {
		throw new Error("Shouldn't call this");
	}

	@Override
	public boolean instance(AppVar<? extends Object> appVar) {
		throw new Error("Shouldn't call this");
	}

	@Override
	public String convert(AppVar<? extends Object> appVar) {
		throw new Error("Shouldn't call this");
	}


//	@Override
//	public boolean instance(AppVar<? extends Object> appVar) {
//		throw new Error("Shouldn't call this");
////		return false;
//	}
//
//	@Override
//	public String convert(AppVar<? extends Object> appVar) {
//		throw new Error("Shouldn't call this");
////		return null;
//	}
	
//	public AppVar<? extends Object> eval(){ 
//
//	}
	
	
}