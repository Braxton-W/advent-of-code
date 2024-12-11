package day8;

public class Coordinate {
	private int row;
	private int col;
	
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Coordinate() {
		this(0, 0);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public String toString() {
		return "(" + row + "," + col + ")";
	}
}
