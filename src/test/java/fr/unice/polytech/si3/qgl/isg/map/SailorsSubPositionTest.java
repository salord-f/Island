package fr.unice.polytech.si3.qgl.isg.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SailorsSubPositionTest {
	private Position position;
	private SailorsSubPosition sailorsSubPosition;

	@Before
	public void init() {
		position = new Position(0, 0);
		sailorsSubPosition = new SailorsSubPosition(position, 0, 0);
	}

	private void positionChecker(int a, int b, int c, int d) {
		assertEquals(a, position.getX());
		assertEquals(b, position.getY());
		assertEquals(c, sailorsSubPosition.getX());
		assertEquals(d, sailorsSubPosition.getY());
	}

	@Test
	public void updatePositionInitTest() {
		assertEquals(0, sailorsSubPosition.getX());
		assertEquals(0, sailorsSubPosition.getY());
	}

	@Test
	public void updatePositionChangeCellXTest() {
		for (int i = 0; i < 2; i++) {
			sailorsSubPosition.updatePosition(Direction.EAST);
			positionChecker(0, 0, i + 1, 0);
		}
		sailorsSubPosition.updatePosition(Direction.EAST);
		positionChecker(1, 0, 0, 0);
	}

	@Test
	public void updatePositionChangeCellYTest() {
		for (int i = 0; i < 2; i++) {
			sailorsSubPosition.updatePosition(Direction.NORTH);
			positionChecker(0, 0, 0, i + 1);
		}
		sailorsSubPosition.updatePosition(Direction.NORTH);
		positionChecker(0, 1, 0, 0);
	}

	@Test
	public void updatePositionChangeCellXLeftTest() {
		positionChecker(0, 0, 0, 0);
		sailorsSubPosition.updatePosition(Direction.WEST);
		positionChecker(-1, 0, 2, 0);
	}

	@Test
	public void updatePositionChangeCellYLeftTest() {
		positionChecker(0, 0, 0, 0);

		sailorsSubPosition.updatePosition(Direction.SOUTH);

		positionChecker(0, -1, 0, 2);

	}

	@Test
	public void updatePositionResetTest() {
		positionChecker(0, 0, 0, 0);

		sailorsSubPosition.updatePosition(Direction.EAST);
		sailorsSubPosition.updatePosition(Direction.WEST);

		positionChecker(0, 0, 0, 0);

	}

	@Test
	public void updatePositionReset2Test() {
		positionChecker(0, 0, 0, 0);

		sailorsSubPosition.updatePosition(Direction.WEST);
		sailorsSubPosition.updatePosition(Direction.EAST);

		positionChecker(0, 0, 0, 0);

	}

	@Test
	public void updatePositionReset3Test() {
		positionChecker(0, 0, 0, 0);

		sailorsSubPosition.updatePosition(Direction.NORTH);
		sailorsSubPosition.updatePosition(Direction.SOUTH);

		positionChecker(0, 0, 0, 0);

	}

	@Test
	public void updatePositionReset4Test() {
		positionChecker(0, 0, 0, 0);

		sailorsSubPosition.updatePosition(Direction.SOUTH);
		sailorsSubPosition.updatePosition(Direction.NORTH);

		positionChecker(0, 0, 0, 0);

	}

	@Test
	public void updatePositionCornerTopRightTest() {
		sailorsSubPosition.updatePosition(Direction.EAST);
		sailorsSubPosition.updatePosition(Direction.EAST);
		sailorsSubPosition.updatePosition(Direction.EAST);
		sailorsSubPosition.updatePosition(Direction.NORTH);
		sailorsSubPosition.updatePosition(Direction.NORTH);
		sailorsSubPosition.updatePosition(Direction.NORTH);

		positionChecker(1, 1, 0, 0);
	}

	@Test
	public void getDirectionInsideSubCellx0y0Test() {
		SailorsSubPosition mySubPosition = new SailorsSubPosition(position, 0, 0);
		position.setSubPositionVisited(0, 0);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(0, 0));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(1, 0);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(1, 0));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(2, 0);

		assertEquals(Direction.NORTH, position.getDirectionInsideCell(2, 0));
		mySubPosition.updatePosition(Direction.NORTH);
		position.setSubPositionVisited(2, 1);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(2, 1));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(1, 1);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(1, 1));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(0, 1);

		assertEquals(Direction.NORTH, position.getDirectionInsideCell(0, 1));
		mySubPosition.updatePosition(Direction.NORTH);
		position.setSubPositionVisited(0, 2);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(0, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(1, 2);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(1, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(2, 2);

		assertNull(position.getDirectionInsideCell(2, 2));
	}

	@Test
	public void getDirectionInsideSubCellx2y2Test() {
		SailorsSubPosition mySubPosition = new SailorsSubPosition(position, 2, 2);
		position.setSubPositionVisited(2, 2);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(2, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(1, 2);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(1, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(0, 2);

		assertEquals(Direction.SOUTH, position.getDirectionInsideCell(0, 2));
		mySubPosition.updatePosition(Direction.SOUTH);
		position.setSubPositionVisited(0, 1);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(0, 1));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(1, 1);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(1, 1));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(2, 1);

		assertEquals(Direction.SOUTH, position.getDirectionInsideCell(2, 1));
		mySubPosition.updatePosition(Direction.SOUTH);
		position.setSubPositionVisited(2, 0);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(2, 0));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(1, 0);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(1, 0));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(0, 0);

		assertNull(position.getDirectionInsideCell(0, 0));
	}

	@Test
	public void getDirectionInsideSubCellx1y1Test() {
		SailorsSubPosition mySubPosition = new SailorsSubPosition(position, 2, 1);
		position.setSubPositionVisited(2, 1);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(2, 1));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(1, 1);

		assertEquals(Direction.WEST, position.getDirectionInsideCell(1, 1));
		mySubPosition.updatePosition(Direction.WEST);
		position.setSubPositionVisited(0, 1);

		assertEquals(Direction.NORTH, position.getDirectionInsideCell(0, 1));
		mySubPosition.updatePosition(Direction.NORTH);
		position.setSubPositionVisited(0, 2);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(0, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(1, 2);

		assertEquals(Direction.EAST, position.getDirectionInsideCell(1, 2));
		mySubPosition.updatePosition(Direction.EAST);
		position.setSubPositionVisited(2, 2);

		assertNull(position.getDirectionInsideCell(2, 2));
	}
}
