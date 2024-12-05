package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		char[][] data = fileToArray();
		
		// outputs given input
		/* for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j]);
			}
			System.out.println();
		} */
		
		System.out.println("XMAS: " + searchArrXMAS(data));
		System.out.println("X-MAS: " + searchArrMASX(data));
	}
	
	public static char[][] fileToArray() {
		String fileName = "src/day4/input.txt";
		File file = new File(fileName);
		Scanner inStream = null;
		char[][] data = new char[140][140];
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		for(int i = 0; inStream.hasNextLine() && i < data.length; i++) {
			data[i] = inStream.nextLine().toCharArray();
		}
		
		inStream.close();
		
		return data;
	}
	
	public static int searchArrXMAS(char[][] arr) {
		int count = 0;
		
		char[][] newArr = new char[arr.length][arr[0].length];
		
		// creates 2D char array for output from input
		// with only characters included in any XMAS
		/* for(int i = 0; i < newArr.length; i++) {
			for(int j = 0; j < newArr[i].length; j++) {
				newArr[i][j] = '.';
			}
		} */
		
		for(int i = 0; i < arr.length; i++) {			
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != 'X' && cur != 'S') {
					continue;
				}
				
				// search horizontally left to right
				if(j + 3 < arr[i].length && cur == 'X' && arr[i][j + 1] == 'M' && arr[i][j + 2] == 'A' && arr[i][j + 3] == 'S') {					
					newArr[i][j] = 'X';
					newArr[i][j + 1] = 'M';
					newArr[i][j + 2] = 'A';
					newArr[i][j + 3] = 'S';
					
					count++;
				}
				// search horizontally right to left
				if(j + 3 < arr[i].length && cur == 'S' && arr[i][j + 1] == 'A' && arr[i][j + 2] == 'M' && arr[i][j + 3] == 'X') {					
					newArr[i][j] = 'S';
					newArr[i][j + 1] = 'A';
					newArr[i][j + 2] = 'M';
					newArr[i][j + 3] = 'X';
					
					count++;
				}
				// search vertically up to down
				if(i + 3 < arr.length && cur == 'X' && arr[i + 1][j] == 'M' && arr[i + 2][j] == 'A' && arr[i + 3][j] == 'S') {					
					newArr[i][j] = 'X';
					newArr[i + 1][j] = 'M';
					newArr[i + 2][j] = 'A';
					newArr[i + 3][j] = 'S';
					
					count++;
				}
				// search vertically down to up
				if(i + 3 < arr.length && cur == 'S' && arr[i + 1][j] == 'A' && arr[i + 2][j] == 'M' && arr[i + 3][j] == 'X') {
					newArr[i][j] = 'S';
					newArr[i + 1][j] = 'A';
					newArr[i + 2][j] = 'M';
					newArr[i + 3][j] = 'X';
					
					count++;
				}
				// search diagonally
				if(i - 3 >= 0 && j - 3 >= 0 && cur == 'X' && arr[i - 1][j - 1] == 'M' && arr[i - 2][j - 2] == 'A' && arr[i - 3][j - 3] == 'S') {
					newArr[i][j] = 'X';
					newArr[i - 1][j - 1] = 'M';
					newArr[i - 2][j - 2] = 'A';
					newArr[i - 3][j - 3] = 'S';
					
					count++;
				}
				if(i - 3 >= 0 && j + 3 < arr[i].length && cur == 'X' && arr[i - 1][j + 1] == 'M' && arr[i - 2][j + 2] == 'A' && arr[i - 3][j + 3] == 'S') {
					newArr[i][j] = 'X';
					newArr[i - 1][j + 1] = 'M';
					newArr[i - 2][j + 2] = 'A';
					newArr[i - 3][j + 3] = 'S';
					
					count++;
				}
				if(i + 3 < arr.length && j - 3 >= 0 && cur == 'X' && arr[i + 1][j - 1] == 'M' && arr[i + 2][j - 2] == 'A' && arr[i + 3][j - 3] == 'S') {
					newArr[i][j] = 'X';
					newArr[i + 1][j - 1] = 'M';
					newArr[i + 2][j - 2] = 'A';
					newArr[i + 3][j - 3] = 'S';
					
					count++;
				}
				if(i + 3 < arr.length && j + 3 < arr[i].length && cur == 'X' && arr[i + 1][j + 1] == 'M' && arr[i + 2][j + 2] == 'A' && arr[i + 3][j + 3] == 'S') {
					newArr[i][j] = 'X';
					newArr[i + 1][j + 1] = 'M';
					newArr[i + 2][j + 2] = 'A';
					newArr[i + 3][j + 3] = 'S';
					
					count++;
				}
			}
		}
		
		/* for(int i = 0; i < newArr.length; i++) {
			for(int j = 0; j < newArr[i].length; j++) {
				System.out.print(newArr[i][j]);
			}
			System.out.println();
		} */
		
		return count;
	}
	
	public static int searchArrMASX(char[][] arr) {
		int count = 0;
		
		char[][] newArr = new char[arr.length][arr[0].length];
		
		// creates 2D char array for output from input
		// with only characters included in any X-MAS
		/* for(int i = 0; i < newArr.length; i++) {
			for(int j = 0; j < newArr[i].length; j++) {
				newArr[i][j] = '.';
			}
		} */
		
		for(int i = 0; i < arr.length; i++) {			
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != 'A') {
					continue;
				}
				
				if(!(i - 1 >= 0 && j - 1 >= 0 && i + 1 < arr.length && j + 1 < arr[i].length)) {
					continue;
				}
				
				if(arr[i - 1][j - 1] == 'M' && arr[i - 1][j + 1] == 'M' && arr[i + 1][j - 1] == 'S' && arr[i + 1][j + 1] == 'S') {
					newArr[i - 1][j - 1] = 'M';
					newArr[i - 1][j + 1] = 'M';
					newArr[i][j] = 'A';
					newArr[i + 1][j - 1] = 'S';
					newArr[i + 1][j + 1] = 'S';
					
					count++;
				}
				else if(arr[i - 1][j - 1] == 'M' && arr[i - 1][j + 1] == 'S' && arr[i + 1][j - 1] == 'M' && arr[i + 1][j + 1] == 'S') {
					newArr[i - 1][j - 1] = 'M';
					newArr[i - 1][j + 1] = 'S';
					newArr[i][j] = 'A';
					newArr[i + 1][j - 1] = 'M';
					newArr[i + 1][j + 1] = 'S';
					
					count++;
				}
				else if(arr[i - 1][j - 1] == 'S' && arr[i - 1][j + 1] == 'M' && arr[i + 1][j - 1] == 'S' && arr[i + 1][j + 1] == 'M') {
					newArr[i - 1][j - 1] = 'S';
					newArr[i - 1][j + 1] = 'M';
					newArr[i][j] = 'A';
					newArr[i + 1][j - 1] = 'S';
					newArr[i + 1][j + 1] = 'M';
					
					count++;
				}
				else if(arr[i - 1][j - 1] == 'S' && arr[i - 1][j + 1] == 'S' && arr[i + 1][j - 1] == 'M' && arr[i + 1][j + 1] == 'M') {
					newArr[i - 1][j - 1] = 'S';
					newArr[i - 1][j + 1] = 'S';
					newArr[i][j] = 'A';
					newArr[i + 1][j - 1] = 'M';
					newArr[i + 1][j + 1] = 'M';
					
					count++;
				}
			}
		}
		
		/* for(int i = 0; i < newArr.length; i++) {
			for(int j = 0; j < newArr[i].length; j++) {
				System.out.print(newArr[i][j]);
			}
			System.out.println();
		} */
		
		return count;
	}
}
