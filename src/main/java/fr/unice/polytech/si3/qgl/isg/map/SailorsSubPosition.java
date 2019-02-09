package fr.unice.polytech.si3.qgl.isg.map;

public class SailorsSubPosition {
	private Position position;
	private int x;
	private int y;

	public SailorsSubPosition(Position position, int x, int y) {
		this.x = x;
		this.y = y;
		this.position = position;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Update sailorsSubPosition depending on the direction taken
	 * When the sailors change cell, their position
	 * is updated and the subPosition parameter
	 * is modified accordingly.
	 * Each cell goes from (0,0), bottom left corner
	 * to (2,2), top right corner
	 */
	public void updatePosition(Direction direction) {
		switch (direction) {
			case EAST: this.x++; break;
			case WEST: this.x--; break;
			case NORTH: this.y++; break;
			case SOUTH: this.y--; break;
		}
		if (x % 3 == 0 && x > 0) {
			position.setX(position.getX() + 1);
			x = 0;
		}
		if (y % 3 == 0 && y > 0) {
			position.setY(position.getY() + 1);
			y = 0;
		}
		if (x < 0) {
			position.setX(position.getX() - 1);
			x = 2;
		}
		if (y < 0) {
			position.setY(position.getY() - 1);
			y = 2;
		}
	}
}
