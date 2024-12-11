package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Solution {
	public static void main(String[] args) {
		String inputFileName = "day8/input";
		int[] inputFileDimensions = inputFileDimensions(inputFileName);
		final int MAX_ROWS = inputFileDimensions[0];
		final int MAX_COLS = inputFileDimensions[1];
		char[][] input = fileToArray(inputFileName, MAX_ROWS, MAX_COLS);
		
		HashMap<Character,Coordinate[]> antennas = getAntennaMap(input);
		// displayHashMap(antennas);
		
		HashMap<Character,Coordinate[]> antinodes = findAntinodes(antennas, MAX_ROWS, MAX_COLS);
		// displayHashMap(antinodes);
		// display2DArrayMap(input, antinodes);
		
		System.out.println(getAntinodesLength(antinodes) + " antinodes");
		
		HashMap<Character,Coordinate[]> antinodes2 = findAntinodes2(antennas, MAX_ROWS, MAX_COLS);
		// display2DArrayMap(input, antinodes2);
		
		System.out.println(getAntinodesLength(antinodes2) + " antinodes at any distance");
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

	public static HashMap<Character,Coordinate[]> getAntennaMap(char[][] arr) {
		HashMap<Character,Coordinate[]> antennas = new HashMap<>();
		
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				char cur = arr[i][j];
				
				if(!((cur >= 'A' && cur <= 'Z') || (cur >= 'a' && cur <= 'z') || (cur >= '0' && cur <= '9'))) {
					continue;
				}
				
				Coordinate[] curCoords = antennas.getOrDefault(cur, new Coordinate[20]);
				curCoords[getArrayValueLength(curCoords)] = new Coordinate(i, j);
				antennas.put(cur, curCoords);
			}
		}
		
		return antennas;
	}
	
	public static HashMap<Character,Coordinate[]> findAntinodes(HashMap<Character,Coordinate[]> map, int MAX_ROWS, int MAX_COLS) {
		HashMap<Character,Coordinate[]> antinodesMap = new HashMap<>();
		
		for(Map.Entry<Character,Coordinate[]> entry : map.entrySet()) {
			char freq = entry.getKey();
			Coordinate[] antennas = entry.getValue();
			Coordinate[] antinodes = new Coordinate[(int)(Math.pow(antennas.length - 1, 2))];
			
			int antennaCount = getArrayValueLength(antennas);
			for(int i = 0; i < antennaCount; i++) {
				for(int j = 0; j < antennaCount; j++) {
					if(antennas[i].equals(antennas[j])) {
						continue;
					}
					
					Coordinate coord1 = antennas[i];
					Coordinate coord2 = antennas[j];
					int coord1Row = coord1.getRow();
					int coord1Col = coord1.getCol();
					int coord2Row = coord2.getRow();
					int coord2Col = coord2.getCol();
					
					int rowDiff = (int)(Math.abs(coord1Row - coord2Row));
					int colDiff = (int)(Math.abs(coord1Col - coord2Col));
					
					// System.out.print(coord1.toString() + ", " + coord2.toString() + ": ");
					
					if((coord1Row < coord2Row && coord1Col < coord2Col) || (coord1Row > coord2Row && coord1Col > coord2Col)) {
						// get antinode at top left
						Coordinate topLeft = (int)(Math.min(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						int antinodeRow = topLeft.getRow() - rowDiff;
						int antinodeCol = topLeft.getCol() - colDiff;
						Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
						
						if(antinodeRow >= 0 && antinodeCol >= 0 && !contains(antinodes, antinode)) {
							// System.out.print("top left: " + antinode.toString() + "; ");
							// System.out.println("Antinode at " + antinode.toString() + 
							//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
							antinodes[getArrayValueLength(antinodes)] = antinode;
						}
						
						// get antinode at bottom right
						Coordinate bottomRight = (int)(Math.max(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						antinodeRow = bottomRight.getRow() + rowDiff;
						antinodeCol = bottomRight.getCol() + colDiff;
						antinode = new Coordinate(antinodeRow, antinodeCol);
						
						if(antinodeRow < MAX_ROWS && antinodeCol < MAX_COLS && !contains(antinodes, antinode)) {
							// System.out.println("bottom right: " + antinode.toString());
							// System.out.println("Antinode at " + antinode.toString() + 
							//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
							antinodes[getArrayValueLength(antinodes)] = antinode;
						}
					}
					else if((coord1Row < coord2Row && coord1Col > coord2Col) || (coord1Row > coord2Row && coord1Col < coord2Col)) {
						// get antinode at top right
						Coordinate topRight = (int)(Math.min(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						int antinodeRow = topRight.getRow() - rowDiff;
						int antinodeCol = topRight.getCol() + colDiff;
						Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
						
						if(antinodeRow >= 0 && antinodeCol < MAX_COLS && !contains(antinodes, antinode)) {
							// System.out.print("top right: " + antinode.toString() + "; ");
							// System.out.println("Antinode at " + antinode.toString() + 
							//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
							antinodes[getArrayValueLength(antinodes)] = antinode;
						}
						
						// get antinode at bottom left
						Coordinate bottomLeft = (int)(Math.max(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						antinodeRow = bottomLeft.getRow() + rowDiff;
						antinodeCol = bottomLeft.getCol() - colDiff;
						antinode = new Coordinate(antinodeRow, antinodeCol);
						
						if(antinodeRow < MAX_ROWS && antinodeCol >= 0 && !contains(antinodes, antinode)) {
							// System.out.println("bottom left: " + antinode.toString());
							// System.out.println("Antinode at " + antinode.toString() + 
							//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
							antinodes[getArrayValueLength(antinodes)] = antinode;
						}
					}
				}
			}
			
			antinodesMap.put(freq, antinodes);
		}
		
		return antinodesMap;
	}
	
	public static HashMap<Character,Coordinate[]> findAntinodes2(HashMap<Character,Coordinate[]> map, int MAX_ROWS, int MAX_COLS) {
		HashMap<Character,Coordinate[]> antinodesMap = new HashMap<>();
		
		for(Map.Entry<Character,Coordinate[]> entry : map.entrySet()) {
			char freq = entry.getKey();
			Coordinate[] antennas = entry.getValue();
			Coordinate[] antinodes = new Coordinate[(int)(Math.pow(antennas.length - 1, 2))];
			
			int antennaCount = getArrayValueLength(antennas);
			for(int i = 0; i < antennaCount; i++) {
				for(int j = 0; j < antennaCount; j++) {
					if(antennas[i].equals(antennas[j])) {
						continue;
					}
					
					Coordinate coord1 = antennas[i];
					Coordinate coord2 = antennas[j];
					int coord1Row = coord1.getRow();
					int coord1Col = coord1.getCol();
					int coord2Row = coord2.getRow();
					int coord2Col = coord2.getCol();
					
					int rowDiff = (int)(Math.abs(coord1Row - coord2Row));
					int colDiff = (int)(Math.abs(coord1Col - coord2Col));
					
					// System.out.print(coord1.toString() + ", " + coord2.toString() + ": ");
					
					if((coord1Row < coord2Row && coord1Col < coord2Col) || (coord1Row > coord2Row && coord1Col > coord2Col)) {
						// get antinode at top left
						Coordinate topLeft = (int)(Math.min(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						
						for(int k = 0; topLeft.getRow() - rowDiff * k >= 0 && topLeft.getCol() - colDiff * k >= 0; k++) {
							int antinodeRow = topLeft.getRow() - rowDiff * k;
							int antinodeCol = topLeft.getCol() - colDiff * k;
							Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
							
							if(antinodeRow >= 0 && antinodeCol >= 0 && !contains(antinodes, antinode)) {
								// System.out.print("top left: " + antinode.toString() + "; ");
								// System.out.println("Antinode at " + antinode.toString() + 
								//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
								antinodes[getArrayValueLength(antinodes)] = antinode;
							}
						}
							
						// get antinode at bottom right
						Coordinate bottomRight = (int)(Math.max(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						
						for(int k = 0; bottomRight.getRow() + rowDiff * k < MAX_ROWS && bottomRight.getCol() + colDiff * k < MAX_COLS; k++) {
							int antinodeRow = bottomRight.getRow() + rowDiff * k;
							int antinodeCol = bottomRight.getCol() + colDiff * k;
							Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
							
							if(antinodeRow < MAX_ROWS && antinodeCol < MAX_COLS && !contains(antinodes, antinode)) {
								// System.out.println("bottom right: " + antinode.toString());
								// System.out.println("Antinode at " + antinode.toString() + 
								//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
								antinodes[getArrayValueLength(antinodes)] = antinode;
							}
						}
					}
					else if((coord1Row < coord2Row && coord1Col > coord2Col) || (coord1Row > coord2Row && coord1Col < coord2Col)) {
						// get antinode at top right
						Coordinate topRight = (int)(Math.min(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						
						for(int k = 0; topRight.getRow() - rowDiff * k >= 0 && topRight.getCol() + colDiff * k < MAX_COLS; k++) {
							int antinodeRow = topRight.getRow() - rowDiff * k;
							int antinodeCol = topRight.getCol() + colDiff * k;
							Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
							
							if(antinodeRow >= 0 && antinodeCol < MAX_COLS && !contains(antinodes, antinode)) {
								// System.out.print("top right: " + antinode.toString() + "; ");
								// System.out.println("Antinode at " + antinode.toString() + 
								//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
								antinodes[getArrayValueLength(antinodes)] = antinode;
							}
						}
						
						// get antinode at bottom left
						Coordinate bottomLeft = (int)(Math.max(coord1Row, coord2Row)) == coord1Row ? coord1 : coord2;
						
						for(int k = 0; bottomLeft.getRow() + rowDiff * k < MAX_ROWS && bottomLeft.getCol() - colDiff * k >= 0; k++) {
							int antinodeRow = bottomLeft.getRow() + rowDiff * k;
							int antinodeCol = bottomLeft.getCol() - colDiff * k;
							Coordinate antinode = new Coordinate(antinodeRow, antinodeCol);
							
							if(antinodeRow < MAX_ROWS && antinodeCol >= 0 && !contains(antinodes, antinode)) {
								// System.out.println("bottom left: " + antinode.toString());
								// System.out.println("Antinode at " + antinode.toString() + 
								//	" from antennas at " + coord1.toString() + " and " + coord2.toString());
								antinodes[getArrayValueLength(antinodes)] = antinode;
							}
						}
					}
				}
			}
			
			antinodesMap.put(freq, antinodes);
		}
		
		return antinodesMap;
	}
	
	public static boolean contains(Coordinate[] arr, Coordinate c) {
		for(int i = 0; i < getArrayValueLength(arr) && arr[i] != null; i++) {			
			if(arr[i].getRow() == c.getRow() && arr[i].getCol() == c.getCol()) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int getAntinodesLength(HashMap<Character,Coordinate[]> map) {
		Coordinate[] antinodes = new Coordinate[1000];
		
		for(Map.Entry<Character,Coordinate[]> entry : map.entrySet()) {
			Coordinate[] coords = entry.getValue();
			
			for(int i = 0; i < getArrayValueLength(coords); i++) {
				if(contains(antinodes, coords[i])) {
					continue;
				}
				
				antinodes[getArrayValueLength(antinodes)] = coords[i];
			}
		}
		
		return getArrayValueLength(antinodes);
	}
	
	public static char[][] copy2DArray(char[][] arr) {
		char[][] arrNew = new char[arr.length][arr[0].length];
		
		for(int i = 0; i < arrNew.length; i++) {
			for(int j = 0; j < arrNew[i].length; j++) {
				arrNew[i][j] = arr[i][j];
			}
		}
		
		return arrNew;
	}
	
	
	public static int getArrayValueLength(Coordinate[] arr) {
		int count = 0;
		Coordinate coordBase = new Coordinate();
		
		for(int i = 0; i < arr.length && arr[i] != null; i++) {
			if(arr[i].equals(coordBase)) {
				continue;
			}
			
			count++;
		}
				
		return count;
	}
	
	
	public static void displayHashMap(HashMap<Character,Coordinate[]> map) {
		for(Map.Entry<Character,Coordinate[]> entry : map.entrySet()) {
			System.out.print(entry.getKey() + ": ");
			
			Coordinate[] value = entry.getValue();
			
			for(int i = 0; i < getArrayValueLength(value) && value[i] != null; i++) {
				System.out.print(value[i].toString() + ", ");
			}
			
			System.out.println();
		}
	}
	
	public static void display2DArray(char[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]);
			}
			
			System.out.println();
		}
	}
		
	public static void display2DArrayMap(char[][] arr, HashMap<Character,Coordinate[]> map) {
		char[][] out = copy2DArray(arr);
		
		for(Map.Entry<Character,Coordinate[]> entry : map.entrySet()) {
			Coordinate[] coords = entry.getValue();
			
			for(int i = 0; i < getArrayValueLength(coords); i++) {
				Coordinate cur = coords[i];
				int curRow = cur.getRow();
				int curCol = cur.getCol();
				char curPos = out[curRow][curCol];
				
				if(curPos != '.') {
					// System.out.println("Antinode at " + cur.toString() + 
					//	(curPos == '#' ? " already in position" : " blocked by antenna"));
					
					continue;
				}
				
				out[curRow][curCol] = '#';
			}
		}
		
		display2DArray(out);
	}
}
