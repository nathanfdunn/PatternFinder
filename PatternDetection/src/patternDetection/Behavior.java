package patternDetection;

public enum Behavior {
	SPIKE,
	INC,
	FLAT,
	DEC,
	DIP;
	public String toString(){
		switch(this){
			case SPIKE: return "^";
			case INC: return "/";
			case FLAT: return "-";
			case DEC: return "\\";
			case DIP: return "v";
			default: return null;
		}
	}
}


