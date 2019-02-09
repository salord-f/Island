package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.actions.Move;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveBetweenCellsTest {
	private List<Cell> cellToExploit;
	private Position position;
	private MoveBetweenCells moveBetweenCells;

	@Before
	public void init() {
		cellToExploit = new ArrayList<>();
		position = new Position(0, 0);
		moveBetweenCells = new MoveBetweenCells();
	}

	@Test
	public void moveNorthEast() {
		cellToExploit.add(new Cell(1, 1, new HashSet<>()));
		moveBetweenCells.run(cellToExploit, position);

		Move firstAction = (Move) moveBetweenCells.getActions().get(0);
		Move secondAction = (Move) moveBetweenCells.getActions().get(1);

		assertTrue(firstAction.is(ActionName.MOVE));
		assertEquals(Direction.NORTH, firstAction.getDirection());

		assertTrue(secondAction.is(ActionName.MOVE));
		assertEquals(Direction.EAST, secondAction.getDirection());
	}

	@Test
	public void moveSouthWest() {
		cellToExploit.add(new Cell(-1, -1, new HashSet<>()));
		moveBetweenCells.run(cellToExploit, position);

		Move firstAction = (Move) moveBetweenCells.getActions().get(0);
		Move secondAction = (Move) moveBetweenCells.getActions().get(1);

		assertTrue(firstAction.is(ActionName.MOVE));
		assertEquals(Direction.SOUTH, firstAction.getDirection());

		assertTrue(secondAction.is(ActionName.MOVE));
		assertEquals(Direction.WEST, secondAction.getDirection());
	}

	@Test
	public void isOnACellToExploit() {
		cellToExploit.add(new Cell(0, 0, new HashSet<>()));
		moveBetweenCells.run(cellToExploit, position);

		assertEquals(SailorsPhase.MOVEANDEXPLOIT, moveBetweenCells.getPhase());
	}
}
