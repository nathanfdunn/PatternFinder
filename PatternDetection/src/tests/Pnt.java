package tests;

import java.util.ArrayList;
import java.util.Scanner;

public class Pnt {

	public static void pnt(Object o){
		System.out.println(o);
	}
	
	public static void pnt(){
		System.out.println();
	}
	
	public static<T> void pntArr(T[] arg){
		for (int i=0; i<arg.length; i++)
			System.out.println(arg[i]);
	}
	
	public static<T> void pntArr(T[][] arg){
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
	
	public static void pause(){
		System.out.print("PAUSE");
		new Scanner(System.in).nextLine();
	}
	
	public static<T> void pntArr(ArrayList<T> arg){
		for (T t : arg)
			System.out.println(t);
	}
}






