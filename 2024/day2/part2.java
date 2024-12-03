package day2;

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class part2 {
	public static void main(String[] args) {
		int[][] data = fileToArray();
		int safe = 0;
		
		for(int i = 0; i < 1000; i++) {
			if(isSafeReportDampner(data[i])) {
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
	
	public static boolean isSafeReport(int[] arr) {
		boolean validDiff = true;
		boolean isIncr = true;
		boolean isDecr = true;
		
		for(int i = 0; i < arr.length - 1 && arr[i + 1] != 0; i++) {
			int cur = arr[i];
			int next = arr[i + 1];
			
			int diff = Math.abs(cur - next);
			if(diff < 1 || diff > 3) {
				validDiff = false;			
			}
			
			if(isDecr && cur < next) {
				isDecr = false;
			}
			else if(isIncr && cur > next) {
				isIncr = false;
			}
			
			if(!validDiff || (!isDecr && !isIncr)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isSafeReportDampner(int[] arr) {
		System.out.println(Arrays.toString(arr));
		boolean isSafe = isSafeReport(arr);
		
		for(int i = 0; !isSafe && i < arr.length; i++) {
			int[] newArr = copyArrRemoveIndex(arr, i);
			
			System.out.println("newArr: " + Arrays.toString(newArr));
			isSafe = isSafeReport(newArr);
		}
		System.out.println("Safe: " + isSafe + "\n");
		
		return isSafe;
	}
	
	public static int[] copyArrRemoveIndex(int[] arr, int index) {
		int[] newArr = new int[7];
		int newIndex = 0;
		
		for (int i = 0; i < arr.length; i++) {
			if (i == index) {
				continue;
			}
			
			newArr[newIndex] = arr[i];
			newIndex++;
		}
		
		return newArr;
	}
}
