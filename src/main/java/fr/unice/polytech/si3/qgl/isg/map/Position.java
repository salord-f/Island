package fr.unice.polytech.si3.qgl.isg.map;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.isg.Explorer.logger;

public class Position {
	protected int x;
	protected int y;
	private Direction firstDirection;
	private Direction secondDirection;
	private List<SubPosition> subPositions;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		addAllSubPositions();
	}

	public Position() {
	}

	public Direction getFirstDirection() {
		return firstDirection;
	}

	public void setFirstDirection(Direction firstDirection) {
		this.firstDirection = firstDirection;
	}

	public Direction getSecondDirection() {
		return secondDirection;
	}

	public void setSecondDirection(Direction secondDirection) {
		this.secondDirection = secondDirection;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equalsPosition(int x, int y) {
		return this.x == x && this.y == y;
	}

	/**
	 * Adds all the subPositions possible in a Cell
	 */
	private void addAllSubPositions() {
		this.subPositions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				subPositions.add(new SubPosition(i, j));
			}
		}
		// This sorting is to insure our subPositions can be iterated over in the correct order.
		subPositions.sort((s1, s2) -> {
			if (s1.getY() == s2.getY()) {
				return Integer.compare(s1.getX(), s2.getX());
			} else return s1.getY() > s2.getY() ? 1 : -1;
		});
	}

	/**
	 * Update the drone position : one time if it's a Fly, two times if it's a heading
	 *
	 * @param newDirection   the new direction of the drone.
	 * @param currentHeading the current drone heading, same than the new direction if it's a fly
	 */
	public void updatePosition(Direction newDirection, Direction currentHeading) {
		updateWithDirection(currentHeading);
		if (newDirection != currentHeading) {
			updateWithDirection(newDirection);
		}
	}

	/**
	 * Update axes depends on the new direction
	 */
	private void updateWithDirection(Direction direction) {
		switch (direction) {
			case NORTH: this.y++; break;
			case SOUTH: this.y--; break;
			case EAST: this.x++; break;
			case WEST: this.x--; break;

			default:
				logger.info("Error in updateWithDirection");
				break;
		}
	}

	/**
	 * Calculate the distance between two positions
	 */
	public double distance(Position pos) {
		return Math.sqrt(Math.pow((pos.x - this.x), 2) + Math.pow((pos.y - this.y), 2));
	}

	public void setPosition(Position position) {
		this.x = position.getX();
		this.y = position.getY();
	}

	public void reverseFirstDirection() {
		this.firstDirection = this.firstDirection.getReverse();
	}

	public void reverseSecondDirection() {
		this.secondDirection = this.secondDirection.getReverse();
	}

	/**
	 * Find an adjacent subcell not visited for the sailors
	 *
	 * @param (i, j) the current sailors position inside a cell.
	 * @return the direction of the next subcell to go to
	 */
	public Direction getDirectionInsideCell(int i, int j) {
		if (allSubCellIsVisited()) {
			logger.info("Every subPosition of this Cell is visited.");
			return null;
		}
		Direction direction = null;
		for (SubPosition subPosition : subPositions) {
			if (!subPosition.isVisited()) {
				int a = subPosition.getX();
				int b = subPosition.getY();
				if (j == b) {
					if (i + 1 == a) return Direction.EAST;
					if (i - 1 == a) return Direction.WEST;
				}
				if (i == a) {
					if (j + 1 == b) return Direction.NORTH;
					if (j - 1 == b) direction = Direction.SOUTH;
				}
			}
		}
		return direction;
	}

	/**
	 * Return true if all subCell of the current cell is visited
	 */
	private boolean allSubCellIsVisited() {
		return subPositions.stream().allMatch(SubPosition::isVisited);
	}

	/**
	 * Set the current subCell as visited
	 */
	public void setSubPositionVisited(int i, int j) {
		subPositions.stream()
				.filter(subPos -> subPos.getX() == i && subPos.getY() == j)
				.forEach(SubPosition::setVisited);
	}

	/**
	 * True if the current position is adjacent to the new position
	 */
	public boolean isAdjacent(Position position) {
		int a = position.getX();
		int b = position.getY();
		return ((b == this.y && (a + 1 == this.x || a - 1 == this.x))
				|| (a == this.x && (b + 1 == this.y || b - 1 == this.y)));
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}
}