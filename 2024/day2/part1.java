package day2;

import java.io.*;
import java.util.Scanner;

public class part1 {
	public static void main(String[] args) {
		int[][] data = fileToArray();
		int safe = 0;
		
		for(int i = 0; i < 1000; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
		
		for(int i = 0; i < 1000; i++) {
			boolean validDiff = true;
			boolean isIncr = true;
			boolean isDecr = true;
			
			for(int j = 0; j < 7 && data[i][j + 1] != 0; j++) {
				int cur = data[i][j];
				int next = data[i][j + 1];
				
				if(j == 0 && next < cur) {
					isIncr = false;
				}
				
				int diff = Math.abs(cur - next);
				if(diff < 1 || diff > 3) {
					validDiff = false;
					
					break;
				}
				
				if(cur < next) {
					isDecr = false;
				}
				else if(cur > next) {
					isIncr = false;
				}
			}
			
			if(validDiff && (isDecr || isIncr)) {
				safe++;
			}
		}
		
		System.out.println("Safe: " + safe);
	}
	
	public static int[][] fileToArray() {
		String fileName = "src/day2/data.txt";
		File file = new File(fileName);
		Scanner inStream = null;
		int[][] data = new int[1000][8];
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		for(int i = 0; inStream.hasNextLine(); i++) {
			String cur = inStream.nextLine();
			String[] ints = cur.split("\s");
			
			for(int j = 0; j < ints.length; j++) {
				data[i][j] = Integer.parseInt(ints[j]);
			}
		}
		
		inStream.close();
		
		return data;
	}
}
