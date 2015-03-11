package patternDetection;

public enum Behavior {
	SPIKE,
	INC,
	FLAT,
	DEC,
	DIP,
	UNK;
	
	public String toString(){
		switch(this){
		case SPIKE: return "^";
		case INC: return "/";
		case FLAT: return "-";
		case DEC: return "\\";
		case DIP: return "v";
		case UNK: return "?";
		default: return null;
		}
	}

	public Behavior translate(int i){
		switch(i){
		case 2: return SPIKE;
		case 1: return INC;
		case 0: return FLAT;
		case -1: return DEC;
		case -2: return DIP;
		case -10: return UNK;
		default: return null;
		}
	}
}








