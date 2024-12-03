package day3;

import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class part1 {
	public static void main(String[] args) {
		int[][] data = fileToArray();
		long sumOfProducts = 0;
		
		for(int i = 0; i < data.length; i++) {
			sumOfProducts += data[i][0] * data[i][1];
		}
		
		System.out.println("Sum of Products: " + sumOfProducts);
	}
	
	public static int[][] fileToArray() {
		String fileName = "src/day3/data.txt";
		File file = new File(fileName);
		Scanner inStream = null;
		String text = "";
		String[] mul = new String[1000];
		int[][] data = new int[1000][2];
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		while(inStream.hasNextLine()) {
			text += inStream.nextLine();
		}
		
		inStream.close();
		
		String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
		Pattern muls = Pattern.compile(regex);
		Matcher matcher = muls.matcher(text);
		
		for(int i = 0; matcher.find() && i < mul.length; i++) {			
			data[i][0] = Integer.parseInt(matcher.group(1));
			data[i][1] = Integer.parseInt(matcher.group(2));
			System.out.println("i: " + i + "; " + matcher.group(1) + " * " + matcher.group(2)); 
		}
		
		return data;
	}
}
