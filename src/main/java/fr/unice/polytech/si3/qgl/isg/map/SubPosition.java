package fr.unice.polytech.si3.qgl.isg.map;

/**
 * SubPosition object contained in Position object.
 * Represents all the different sailors cells inside one drone cell.
 */
public class SubPosition {
	private int x;
	private int y;
	private boolean visited;

	public SubPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.visited = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited() {
		this.visited = true;
	}
}
