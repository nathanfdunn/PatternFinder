package ui;

import java.util.ArrayList;

import ui.AppVar.AppBool;
import ui.AppVar.AppDouble;
import ui.AppVar.AppInt;
import ui.AppVar.AppNull;
import ui.AppVar.AppString;

//import java.util.ArrayList;

public abstract class CommandAppFunction {

	public static final AppNull NULL = AppVar.NULL;
	public static final AppString STR = AppVar.STR;
	public static final AppBool BOOL = AppVar.BOOL;
	public static final AppInt INT = AppVar.INT;
	public static final AppDouble DOUBLE = AppVar.DOUBLE;
	
	
	protected CommandApp app;
	protected final String name;
	
	public CommandAppFunction(CommandApp app, String name){
		this.app = app;
		this.name = name;
	}
	
	public abstract AppVar<? extends Object> call( AppVar<? extends Object>... args );
	
	public String getName(){
		return name;
	}
	
	
	
	public static class quitFunction extends CommandAppFunction {

		public quitFunction(CommandApp app) {
			super(app, "quit");
		}

		@Override
		public AppVar<? extends Object> call(AppVar<? extends Object>... args) {
			if (args.length != 0) this.app.showError("Unused arguments in 'quit'");
			this.app.quit();
			return NULL;
		}
	}
	
	public static class printFunction extends CommandAppFunction {
		public printFunction(CommandApp app) {
			super(app, "print");
		}

		@Override
		public AppVar<? extends Object> call(AppVar<? extends Object>... args) {
			for (AppVar<? extends Object> arg : args )
				this.app.out.print(arg.toString());
			return NULL;
		}
	}
	
	public static class setEchoFunction extends CommandAppFunction {
		public setEchoFunction(CommandApp app) {
			super(app, "setEcho");
		}

		@Override
		public AppVar<? extends Object> call(AppVar<? extends Object>... args) {
			@SuppressWarnings("unchecked")
			String check = CommandAppUtil.argTypeCheck(args, BOOL);
			if (check.equals("")){
				this.app.echo = BOOL.convert(args[0]);
			}
			return NULL;
		}
	}
	
	public static ArrayList<CommandAppFunction> getCommonFunctions(CommandApp app){
		ArrayList<CommandAppFunction> out = new ArrayList<CommandAppFunction>();
		out.add(new printFunction(app));
		out.add(new quitFunction(app));
		out.add(new setEchoFunction(app));
		return out;
	}
	
	
}
