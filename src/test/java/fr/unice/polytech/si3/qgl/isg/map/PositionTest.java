package fr.unice.polytech.si3.qgl.isg.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {
	private Position position;

	@Before
	public void init() {
		position = new Position(0, 0);
	}

	@Test
	public void rightTest() {
		Position position2 = new Position(1, 1);
		position2.setPosition(position);
		assertEquals(0, position2.getX());
		assertEquals(0, position2.getY());
	}

	@Test
	public void reverseFirstDirectionTest() {
		position.setFirstDirection(Direction.NORTH);
		position.reverseFirstDirection();
		assertEquals(Direction.SOUTH, position.getFirstDirection());
	}

	@Test
	public void reverseSecondDirection() {
		position.setSecondDirection(Direction.NORTH);
		position.reverseSecondDirection();
		assertEquals(Direction.SOUTH, position.getSecondDirection());
	}

	@Test
	public void distanceTest() {
		Position position2 = new Position(2, 0);
		assertEquals(2, position.distance(position2), 2);
		assertEquals(0, position.distance(position), 2);

		position2.setX(2);
		position2.setY(2);
		assertEquals(2.83, position.distance(position2), 2);
	}

	@Test
	public void updatePosition() {
		//Actual heading
		position.updatePosition(Direction.NORTH, Direction.NORTH);
		assertTrue(position.equalsPosition(0, 1));

		//A new heading
		position.updatePosition(Direction.WEST, Direction.NORTH);
		assertTrue(position.equalsPosition(-1, 2));
		position.updatePosition(Direction.SOUTH, Direction.WEST);
		assertTrue(position.equalsPosition(-2, 1));
		position.updatePosition(Direction.EAST, Direction.SOUTH);
		assertTrue(position.equalsPosition(-1, 0));
	}
}
