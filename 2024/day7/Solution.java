package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		String inputFileName = "day7/input";
		int[] inputFileDimensions = inputFileDimensions(inputFileName);
		String[] input = fileToArray(inputFileName, inputFileDimensions[0], inputFileDimensions[1]);
		
		long[][] equations = new long[inputFileDimensions[0]][20];
		equations = getEquations(input, equations);
		
		System.out.println("The sum of the result of each valid equation using operators + (add) and * (multiply) is " + getValidEquationsTotalSum(equations, 2));
		System.out.println("The sum of the result of each valid equation using operators +, *, and || (concatenate)  is " + getValidEquationsTotalSum(equations, 3));
	}
	
	public static int[] inputFileDimensions(String fileName) {
		String filePath = "src/" + fileName + ".txt";
		File file = new File(filePath);
		Scanner inStream = null;
		int rows = 0;
		int cols = 0;
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		while(inStream.hasNextLine()) {
			int curLineLength = inStream.nextLine().length();
			
			rows++;
			
			if(curLineLength > cols) {
				cols = curLineLength;
			}
		}
		
		inStream.close();
		
		int[] dimensions = {rows, cols};
		
		return dimensions;
	}
	
	public static String[] fileToArray(String fileName, int rows, int cols) {
		String filePath = "src/" + fileName + ".txt";
		File file = new File(filePath);
		Scanner inStream = null;
		String[] data = new String[rows];
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		for(int i = 0; inStream.hasNextLine() && i < data.length; i++) {
			data[i] = inStream.nextLine();
		}
		
		inStream.close();
		
		return data;
	}
	
	public static long[][] getEquations(String[] input, long[][] equations) {
		for(int i = 0; i < input.length; i++) {
			String cur = input[i];
			String[] split = cur.split(" ");
			
			equations[i][0] = Long.parseLong(split[0].replace(":", ""));
			
			for(int j = 1; j < split.length; j++) {
				equations[i][j] = Integer.parseInt(split[j]);
			}
		}
		
		return equations;
	}
	
	/* public static boolean isValidEquationAddMult(long[] equation) {
		int equationLength = arrayValueLength(equation);
		int operatorsNeeded = equationLength - 2;
				
		for(int i = 0; i <= (int)(Math.pow(2, operatorsNeeded) - 1); i++) {
			int[] bin = toBinary(i, operatorsNeeded);
			long result = equation[1];
			
			for(int j = 0; j < bin.length && j + 2 != 0; j++) {
				if(bin[j] == 0) {
					result += equation[j + 2];
				}
				else {
					result *= equation[j + 2];
				}
			}
			
			if(result == equation[0]) {
				return true;
			}
		}
		
		return false;
	} */
	
	public static boolean isValidEquation(long[] equation, int operatorsAvailable) {
		int equationLength = arrayValueLength(equation);
		int operatorsNeeded = equationLength - 2;
				
		for(int i = 0; i <= (int)(Math.pow(operatorsAvailable, operatorsNeeded) - 1); i++) {
			int[] bin = toBaseX(i, operatorsNeeded, operatorsAvailable);
			long result = equation[1];
			
			for(int j = 0; j < bin.length && j + 2 != 0; j++) {
				long curValue = equation[j + 2];
				
				if(bin[j] == 0) {
					result += curValue;
				}
				else if(bin[j] == 1) {
					result *= curValue;
				}
				else {
					result = Long.parseLong(result + "" + curValue);
				}
			}
			
			if(result == equation[0]) {
				return true;
			}
		}
		
		return false;
	}
	
	public static long getValidEquationsTotalSum(long[][] equations, int operationsAvailable) {
		long sum = 0;
		
		for(int i = 0; i < equations.length; i++) {
			if(!isValidEquation(equations[i], operationsAvailable)) {
				continue;
			}
			
			sum += equations[i][0];
		}
		
		return sum;
	}
	
	public static int arrayValueLength(long[] arr) {
		int count = 0;
		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == 0) {
				continue;
			}
			
			count++;
		}
		
		return count;
	}
	
	/* public static int[] toBinary(int num, int length) {
		int[] bin = new int[length];
		int difference = num;
		
		for(int i = 0; i < length; i++) {
			int curBinValue = (int)(Math.pow(2, length - i - 1));
			
			if(difference >= curBinValue) {
				bin[i] = 1;
				
				difference -= curBinValue;
			}
		}
		
		return bin;
	} */
	
	public static int[] toBaseX(int num, int length, int base) {
		int[] out = new int[length];
		int difference = num;
		
		for(int i = 0; i < length; i++) {
			int curBaseValue = (int)(Math.pow(base, length - i - 1));
			
			if(base == 3 && difference >= curBaseValue * 2) {
				out[i] = 2;
				
				difference -= curBaseValue * 2;
			}
			else if(difference >= curBaseValue) {
				out[i] = 1;
				
				difference -= curBaseValue;
			}
		}
		
		return out;
	}

	public static void displayIntArray(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void displayLongArray(long[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
