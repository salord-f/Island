package fr.unice.polytech.si3.qgl.isg.map;

import java.util.Arrays;

public enum Direction {
	NORTH("N", 0),
	EAST("E", 1),
	SOUTH("S", 2),
	WEST("W", 3);

	private final String acronym;
	private final int index;

	Direction(String acronym, int index) {
		this.acronym = acronym;
		this.index = index;
	}

	public static Direction getDirection(String letter) {
		return Arrays.stream(Direction.values())
				.filter(d -> d.getAcronym().equalsIgnoreCase(letter))
				.findFirst().orElse(null);
	}

	public String getAcronym() {
		return this.acronym;
	}

	public Direction getRight() {
		return Direction.values()[(this.index + 1) % 4];
	}

	public Direction getLeft() {
		return Direction.values()[(this.index + 3) % 4];
	}

	/**
	 * The opposite of the current direction
	 */
	public Direction getReverse() {
		return Direction.values()[(this.index + 2) % 4];
	}
}