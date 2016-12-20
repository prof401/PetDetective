package net.april1.detective;

import java.util.Set;

public class Detective {
	private final static String[] DEFAULTMAP = { "A.B.g.f", ". . . .", "c.C.D.E", ".     .", "F.j.0. ", ". . . .",
			"b.G h e", "  . .  ", "H.I.i.J", ". .   .", "d.k.K.a" };
	private final static int DEFAULTMOVES = 32;
	@SuppressWarnings("unused")
	private final static boolean DEBUG = false;

	private Set<Cell> cellSet = new java.util.HashSet<Cell>();
	private int maxMoves = -1;
	private int numberOfPets = -1;
	private int[] petStatus;
	private Set<Character> car;
	private char[] moves;
	private int movesNeeded = -1;

	public Detective() {
		this(DEFAULTMAP, DEFAULTMOVES);
	}

	public Detective(String[] map, int moves) {
		Cell[][] cellArray = createCells(map);
		updatePath(map, cellArray);
		maxMoves = moves;
	}

	private void updatePath(String[] map, Cell[][] cellArray) {
		int maxRow = cellArray.length - 1;
		int maxCol = cellArray[0].length - 1;
		int rowCounter = 0;
		for (Cell[] row : cellArray) {
			int colCounter = 0;
			for (Cell cell : row) {
				cellSet.add(cell);
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
	}

	private Cell[][] createCells(String[] map) {
		Set<Character> pets = new java.util.HashSet<Character>();
		int rowCount = (map.length + 1) / 2;
		int colCount = (map[0].length() + 1) / 2;
		Cell[][] arrayMap = new Cell[rowCount][colCount];
		for (int row = 0; row < rowCount; row++) {
			String rowString = map[row * 2];
			for (int col = 0; col < colCount; col++) {
				char colChar = rowString.charAt(col * 2);
				arrayMap[row][col] = new Cell(colChar);
				if (Character.isUpperCase(colChar)) {
					pets.add(colChar);
				}
			}
		}
		numberOfPets = pets.size();
		return arrayMap;
	}

	public void findSolutions() {
		long startTime = System.currentTimeMillis();
		petStatus = new int[numberOfPets];
		moves = new char[maxMoves];
		for (int cnt = 0; cnt < numberOfPets; cnt++) {
			petStatus[cnt] = 2;
		}
		movesNeeded = numberOfPets * 2;

		car = new java.util.HashSet<Character>(4);
		Cell start = findStart();

		nextCell(start, 0);
		System.out.println(System.currentTimeMillis() - startTime);
	}

	private Cell findStart() {
		Cell start = null;
		for (Cell cell : cellSet) {
			if (cell.getContent() == '0') {
				start = cell;
			}
		}
		return start;
	}

	private void nextCell(Cell cell, int move) {
		if (cell == null)
			return;

		char content = cell.getContent();

		if (canDropoff(content)) {
			petStatus[content - 'a']--;
			movesNeeded--;
			car.remove(Character.toUpperCase(content));

			if (movesNeeded == 0) {
				System.out.println(moves);
			} else {
				nextMove(cell, move);
			}

			car.add(Character.toUpperCase(content));
			petStatus[content - 'a']++;
			movesNeeded++;
		} else {
			boolean pickup = false;
			if (canPickup(content)) {
				pickup = true;
				petStatus[content - 'A']--;
				movesNeeded--;
				car.add(content);

				nextMove(cell, move);

				car.remove(content);
				petStatus[content - 'A']++;
				movesNeeded++;
			}
			if ((movesNeeded + move <= maxMoves)) {
				nextMove(cell, move, !pickup);
			}
		}
	}

	private void nextMove(Cell cell, int move) {
		nextMove(cell, move, true);
	}

	private void nextMove(Cell cell, int move, boolean upper) {	
		if (move == maxMoves)
			return;
		moves[move] = upper?'U':'u';
		nextCell(cell.getUp(), move + 1);
		moves[move] = upper?'D':'d';
		nextCell(cell.getDown(), move + 1);
		moves[move] = upper?'L':'l';
		nextCell(cell.getLeft(), move + 1);
		moves[move] = upper?'R':'r';
		nextCell(cell.getRight(), move + 1);
		moves[move] = ' ';
	}

	private boolean canDropoff(char content) {
		boolean dropoff = false;
		if (Character.isLowerCase(content)) {
			if (car.contains(Character.toUpperCase(content))) {
				dropoff = true;
			}
		}
		return dropoff;
	}

	private boolean canPickup(char content) {
		boolean pickup = false;
		if (Character.isUpperCase(content)) {
			if (car.size() < 4) {
				if (petStatus[content - 'A'] == 2) {
					pickup = true;
				}
			}
		}
		return pickup;
	}

	public static void main(String[] args) {
		/*
		 * Detective simple1 = new Detective(new String[] { "A.B. ", ". . .",
		 * " .0 b", "  . .", " . .a" }, 6); simple1.findSolutions();
		 */

//		Detective simple9 = new Detective(
//				new String[] { 
//						"A.c e", 
//						". . .", 
//						"B C.D", 
//						".   .", 
//						"a 0.b", 
//						". . .", 
//						" .f.E", 
//						". . .", 
//						" .d.F" }, 17);
//		simple9.findSolutions();

//		Detective simple13 = new Detective(new String[] { 
//				"A.g.f", 
//				". . .", 
//				"e B.C", 
//				". . .", 
//				"D 0.E", 
//				". . .", 
//				"F.d  ",
//				"  . .", 
//				"b.G h", 
//				". . .", 
//				"a.c.H" }, 21);
//		simple13.findSolutions();

//		Detective simple17 = new Detective(new String[] { 
//				"g.h.b.f", 
//				".   .  ", 
//				"A.a.c.B", 
//				". . . .", 
//				"i.C 0  ",
//				".   . .", 
//				"D.e.E F", 
//				". . .  ", 
//				" .d.G. ", 
//				". . . .", 
//				"H.j.I.J" }, 30);
//		simple17.findSolutions();

		Detective simple19 = new Detective(new String[] { 
				"A.b B.C", 
				". . . .", 
				"D.E.F.G", 
				"  . . .", 
				"H.I.J. ",
				". . . .", 
				"g.a.0.K", 
				". .   .", 
				"h f.j d", 
				".   . .", 
				"i.c.e.k" }, 35);
		simple19.findSolutions();

		// Detective detective = new Detective();
		// detective.findSolutions();
		System.out.println("DONE");
	}

}
