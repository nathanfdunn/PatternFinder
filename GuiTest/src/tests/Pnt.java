package tests;

public class Pnt {

	public static void pnt(Object o){
		System.out.println(o);
	}
	
	public static void pnt(){
		System.out.println();
	}
	
	public static<T extends Object> void pntArr(T[] arg){
		for (int i=0; i<arg.length; i++)
			System.out.println(arg[i]);
	}
	
	public static<T extends Object> void pntArr(T[][] arg){
		for (int i=0; i<arg.length; i++){
			String line = "";
			for (int j=0; j<arg[i].length; j++){
				line += arg[i][j] + " ";
			}
			pnt(line);
		}
	}
	
	public static void pntArr(int[][] arg){
		for (int i=0; i<arg.length; i++){
			String line = "";
			for (int j=0; j<arg[i].length; j++){
				line += arg[i][j] + " ";
			}
			pnt(line);
		}
	}
	
	public static void pntArr(int[] arg){
		for (int i=0; i<arg.length; i++)
			System.out.println(arg[i]);
	}
	
	
	public static void pntArr(double[][] arg){
		for (int i=0; i<arg.length; i++){
			String line = "";
			for (int j=0; j<arg[i].length; j++){
				line += arg[i][j] + " ";
			}
			pnt(line);
		}
	}
	
	public static void pntArr(double[] arg){
		for (int i=0; i<arg.length; i++)
			System.out.println(arg[i]);
	}
}
