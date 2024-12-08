package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {	
	public static void main(String[] args) {
		String inputFileName = "day6/input";
		int[] inputFileDimensions = inputFileDimensions(inputFileName);
		
		char[][] input = fileToArray(inputFileName, inputFileDimensions[0], inputFileDimensions[1]);
		// display2DArray(input);
		
		int[] guardPos = getGuardPos(input);
		char guardDirection = getGuardDirection(input);
		
		char[][] guardPath = getGuardPath(input, guardPos, guardDirection);
		
		System.out.println("The guard visited " + getGuardPathLength(guardPath) + " distinct positions");
		// display2DArray(guardPath);
		
		System.out.println(getNewObstructionsLength(input, guardPath, guardPos, guardDirection) + " distinct positions for a new object to get the guard stuck in a loop");
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
	
	public static char[][] fileToArray(String fileName, int rows, int cols) {
		String filePath = "src/" + fileName + ".txt";
		File file = new File(filePath);
		Scanner inStream = null;
		char[][] data = new char[rows][cols];
		
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
	
	public static int[] getGuardPos(char[][] arr) {
		int[] pos = new int[2];
		
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != '^' && cur != '<' && cur != '>' && cur != 'v') {
					continue;
				}
				
				pos[0] = i;
				pos[1] = j;
				
				return pos;
			}
		}
		
		return pos;
	}
	
	public static char getGuardDirection(char[][] arr) {		
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != '^' && cur != '<' && cur != '>' && cur != 'v') {
					continue;
				}
				
				return cur;
			}
		}
		
		return '0';
	}
	
	public static char[][] getGuardPath(char[][] arr, int[] guardPos, char guardDirection) {
		char[][] guardPath = copyArr(arr);
		int guardRowPos = guardPos[0];
		int guardColPos = guardPos[1];
		char BLOCKER = '#';
		
		//System.out.println("guardPath in getGuardPath (new map)");
		//display2DArray(guardPath);
		
		// while guard isn't on map border
		// where next move would be out of bounds
		while(guardRowPos > 0 && guardRowPos < guardPath.length && guardColPos > 0 && guardColPos < guardPath[0].length) {
			// guard facing up and able to move up
			if(guardDirection == '^' && guardRowPos - 1 >= 0 && guardPath[guardRowPos - 1][guardColPos] != BLOCKER) {
				guardPath[guardRowPos--][guardColPos] = '^';
				
				// System.out.println("Guard moved up to (" + guardRowPos + "," + guardColPos + ")");
			}
			// guard facing left and able to move left
			else if(guardDirection == '<' && guardColPos - 1 >= 0 && guardPath[guardRowPos][guardColPos - 1] != BLOCKER) {
				guardPath[guardRowPos][guardColPos--] = '<';
				
				// System.out.println("Guard moved left to (" + guardRowPos + "," + guardColPos + ")");
			}
			// guard facing right and able to move right
			else if(guardDirection == '>' && guardColPos < guardPath[guardRowPos].length && guardPath[guardRowPos][guardColPos + 1] != BLOCKER) {
				guardPath[guardRowPos][guardColPos++] = '>';
				
				// System.out.println("Guard moved right to (" + guardRowPos + "," + guardColPos + ")");
			}
			// guard facing down and able to move down
			else if(guardDirection == 'v' && guardRowPos + 1 < guardPath.length && guardPath[guardRowPos + 1][guardColPos] != BLOCKER) {
				guardPath[guardRowPos++][guardColPos] = 'v';
				
				// System.out.println("Guard moved down to (" + guardRowPos + "," + guardColPos + ")");
			}
			// next move blocked by obstacle
			// change direction 90 degrees clockwise
			else {
				// System.out.print("Guard turned right 90 degrees from " + guardDirection + " to ");
				if(guardDirection == '^') {
					guardDirection = '>';
				}
				else if(guardDirection == '>') {
					guardDirection = 'v';
				}
				else if(guardDirection == 'v') {
					guardDirection = '<';
				}
				else if(guardDirection == '<') {
					guardDirection = '^';
				}
				// System.out.println(guardDirection + " at (" + guardRowPos + "," + guardColPos + ")");
				
				continue;
			}
			
			// guard is on border heading direction of border
			// stop movement as guard escaped map
			if((guardRowPos == 0 && guardDirection == '^') || (guardColPos == 0 && guardDirection == '<') || (guardColPos == guardPath[guardRowPos].length - 1 && guardDirection == '>') || (guardRowPos == guardPath.length - 1 && guardDirection == 'v')) {
				guardPath[guardRowPos][guardColPos] = guardDirection;
				
				// System.out.println("Guard escaped");
				
				break;
			}
			
			if(isGuardLooping(guardPath, guardRowPos, guardColPos, guardDirection)) {
				break;
			}
		}
		
		return guardPath;
	}
	
	public static int getGuardPathLength(char[][] arr) {
		int count = 0;
		
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != '^' && cur != '<' && cur != '>' && cur != 'v') {
					continue;
				}
				
				count++;
			}
		}
		
		return count;
	}
	
	public static int getNewObstructionsLength(char[][] arr, char[][] guardPath, int[] guardPos, char guardDirection) {
		char BLOCKER = '#';
		int count = 0;
				
		for(int i = 0; i < guardPath.length; i++) {
			for(int j = 0; j < guardPath[i].length; j++) {
				if(guardPath[i][j] != '^' && guardPath[i][j] != '<' && guardPath[i][j] != '>' && guardPath[i][j] != 'v') {
					continue;
				}
				
				//System.out.println("arr in getNewObstructionsLength");
				//display2DArray(arr);
				
				char[][] arrNew = copyArr(arr);
				
				arrNew[i][j] = BLOCKER;
				
				// System.out.println("\nNext obstacle at (" + i + "," + j + ")");
				
				char[][] curGuardPath = getGuardPath(arrNew, guardPos, guardDirection);
				
				if(guardEscaped(curGuardPath)) {
					// System.out.println("Guard escaped (" + i + "," + j + ")");
					// display2DArray(curGuardPath);
					continue;
				}
				
				count++;
				// System.out.println("Guard failed to escape at (" + i + "," + j + ")");
				// display2DArray(curGuardPath);
			}
		}
		
		return count;
	}
	
	public static boolean guardEscaped(char[][] arr) {		
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(cur != '^' && cur != '<' && cur != '>' && cur != 'v') {
					continue;
				}
				
				// guard escaped by corner
				if((i == 0 && (j == 0 || j == arr[i].length - 1)) || (i == arr.length - 1 && (j == 0 || j == arr[i].length - 1))) {
					return true;
				}
				// guard escaped by top or bottom
				else if((i == 0 && cur == '^') || (i == arr.length - 1 && cur == 'v')) {
					return true;
				}
				// guard escaped by left or right
				else if((j == 0 && cur == '<') || (j == arr[i].length - 1 && cur == '>')) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean isGuardLooping(char[][] arr, int guardRowPos, int guardColPos, char guardDirection) {
		char BLOCKER = '#';
		
		if(arr[guardRowPos][guardColPos] == guardDirection) {
			return true;
		}
		
		if(guardDirection == '^' && arr[guardRowPos][guardColPos] == 'v' && arr[guardRowPos - 1][guardColPos] == BLOCKER) {
			return true;
		}
		else if(guardDirection == '<' && arr[guardRowPos][guardColPos] == '>' && arr[guardRowPos][guardColPos - 1] == BLOCKER) {
			return true;
		}
		else if(guardDirection == '>' && arr[guardRowPos][guardColPos] == '<' && arr[guardRowPos][guardColPos + 1] == BLOCKER) {
			return true;
		}
		else if(guardDirection == 'v' && arr[guardRowPos][guardColPos] == '^' && arr[guardRowPos + 1][guardColPos] == BLOCKER) {
			return true;
		}
		
		/* if((guardDirection == '^' && arr[guardRowPosNew - 1][guardColPosNew] == 'v') || (guardDirection == '<' && arr[guardRowPosNew][guardColPosNew - 1] == '>') || (guardDirection == '>' && arr[guardRowPosNew][guardColPosNew + 1] == '<') || (guardDirection == 'v' && arr[guardRowPosNew + 1][guardColPosNew] == '^')) {
			return true;
		} */
		
		return false;
	}
	
	public static char[][] copyArr(char[][] arr) {
		char[][] arrCopy = new char[arr.length][arr[0].length];
		
		for(int i = 0; i < arrCopy.length; i++) {
			for(int j = 0; j < arrCopy[i].length; j++) {
				arrCopy[i][j] = arr[i][j];
			}
		}
		
		return arrCopy;
	}

	public static void display2DArray(char[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
}
