package net.april1.detective;

import java.util.Set;

public class Detective {
	private final static String[] DEFAULTMAP = { "A.B.g.f", ". . . .", "c.C.D.E", ".     .", "F.j.0. ", ". . . .",
			"b.G h e", "  . .  ", "H.I.i.J", ". .   .", "d.k.K.a" };
	private final static int DEFAULTMOVES = 32;

	Set<Cell> map = new java.util.HashSet<Cell>();

	public Detective() {
		this(DEFAULTMAP, DEFAULTMOVES);
	}

	public Detective(String[] map, int moves) {
		Cell[][] cellArray = createCells(map);
		int maxRow = cellArray.length - 1;
		int maxCol = cellArray[0].length - 1;
		int rowCounter = 0;
		for (Cell[] row : cellArray) {
			int colCounter = 0;
			for (Cell cell : row) {
				// UP
				if (rowCounter > 0 && map[(2 * rowCounter) - 1].charAt((2 * colCounter)) == '.') {
					cell.setUp(cellArray[rowCounter - 1][colCounter]);
				}
				// DOWN
				if (rowCounter < maxRow && map[(2 * rowCounter) + 1].charAt((2 * colCounter)) == '.') {
					cell.setDown(cellArray[rowCounter + 1][colCounter]);
				}
				// LEFT
				if (colCounter > 0 && map[(2 * rowCounter)].charAt((2 * colCounter) - 1) == '.') {
					cell.setLeft(cellArray[rowCounter][colCounter - 1]);
				}
				// RIGHT
				if (colCounter < maxCol && map[(2 * rowCounter)].charAt((2 * colCounter) + 1) == '.') {
					cell.setRight(cellArray[rowCounter][colCounter + 1]);
				}
				colCounter++;
			}
			rowCounter++;
		}
		Cell testCell = cellArray[1][0];
		if (testCell.getUp() == null)
			System.out.println("NULL");
		else
			System.out.println("NOT NULL");
		if (testCell.getDown() == null)
			System.out.println("NULL");
		else
			System.out.println("NOT NULL");
		if (testCell.getLeft() == null)
			System.out.println("NULL");
		else
			System.out.println("NOT NULL");
		if (testCell.getRight() == null)
			System.out.println("NULL");
		else
			System.out.println("NOT NULL");
	}

	private Cell[][] createCells(String[] map) {
		int rowCount = (map.length + 1) / 2;
		int colCount = (map[0].length() + 1) / 2;
		Cell[][] arrayMap = new Cell[rowCount][colCount];
		for (int row = 0; row < rowCount; row++) {
			String rowString = map[row * 2];
			for (int col = 0; col < colCount; col++) {
				char colChar = rowString.charAt(col * 2);
				arrayMap[row][col] = new Cell(colChar);
			}
		}
		return arrayMap;
	}

	public static void main(String[] args) {
		Detective detect = new Detective();

	}

}
