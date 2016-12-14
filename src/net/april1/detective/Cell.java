package net.april1.detective;

public class Cell {
	private char content;
	private boolean pickup = false;
	private boolean dropoff = false;
	private Cell up;
	private Cell down;
	private Cell left;
	private Cell right;
	public char getContent() {
		return content;
	}
	public void setContent(char content) {
		this.content = content;
	}
	public boolean isPickup() {
		return pickup;
	}
	public void setPickup(boolean pickup) {
		this.pickup = pickup;
	}
	public boolean isDropoff() {
		return dropoff;
	}
	public void setDropoff(boolean dropoff) {
		this.dropoff = dropoff;
	}
	public Cell getUp() {
		return up;
	}
	public void setUp(Cell up) {
		this.up = up;
	}
	public Cell getDown() {
		return down;
	}
	public void setDown(Cell down) {
		this.down = down;
	}
	public Cell getLeft() {
		return left;
	}
	public void setLeft(Cell left) {
		this.left = left;
	}
	public Cell getRight() {
		return right;
	}
	public void setRight(Cell right) {
		this.right = right;
	}

}
