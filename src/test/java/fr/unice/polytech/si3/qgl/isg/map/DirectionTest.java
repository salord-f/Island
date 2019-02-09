package fr.unice.polytech.si3.qgl.isg.map;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class DirectionTest {
	@Test
	public void rightTest() {
		assertSame(Direction.EAST, Direction.NORTH.getRight());
		assertSame(Direction.WEST, Direction.SOUTH.getRight());
		assertSame(Direction.SOUTH, Direction.EAST.getRight());
		assertSame(Direction.NORTH, Direction.WEST.getRight());
	}

	@Test
	public void leftTest() {
		assertSame(Direction.WEST, Direction.NORTH.getLeft());
		assertSame(Direction.EAST, Direction.SOUTH.getLeft());
		assertSame(Direction.NORTH, Direction.EAST.getLeft());
		assertSame(Direction.SOUTH, Direction.WEST.getLeft());
	}

	@Test
	public void reverseTest() {
		assertSame(Direction.SOUTH, Direction.NORTH.getReverse());
		assertSame(Direction.NORTH, Direction.SOUTH.getReverse());
		assertSame(Direction.WEST, Direction.EAST.getReverse());
		assertSame(Direction.EAST, Direction.WEST.getReverse());
	}
}
