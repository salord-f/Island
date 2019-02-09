package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchDirectionTest {

	@Test
	public void searchDirectionRightTest() {
		Position position = new Position(0, 0);
		SearchDirection searchDirection = new SearchDirection(position, Direction.NORTH);
		searchDirection.run(true, null, false);

		List<Action> actions = searchDirection.getActions();
		assertEquals(2, actions.size());
		assertEquals(DronePhase.SEARCHISLAND, searchDirection.getPhase());

		assertEquals(Direction.NORTH, position.getFirstDirection());
		assertEquals(Direction.EAST, position.getSecondDirection());
	}

	@Test
	public void searchDirectionLeftTest() {
		Position position = new Position(0, 0);
		SearchDirection searchDirection = new SearchDirection(position, Direction.NORTH);
		searchDirection.run(false, null, false);

		List<Action> actions = searchDirection.getActions();
		assertEquals(2, actions.size());
		assertEquals(DronePhase.SEARCHISLAND, searchDirection.getPhase());

		assertEquals(Direction.NORTH, position.getFirstDirection());
		assertEquals(Direction.WEST, position.getSecondDirection());
	}

	@Test
	public void searchDirectionGroundFoundTest() {
		SearchDirection searchDirection = new SearchDirection(new Position(0, 0), Direction.NORTH);
		searchDirection.run(false, Direction.NORTH, false);

		assertEquals(DronePhase.FLYTO, searchDirection.getPhase());
	}

	@Test
	public void searchDirectionOzIslandTest() {
		SearchDirection searchDirection = new SearchDirection(new Position(0, 0), Direction.NORTH);
		searchDirection.run(false, Direction.NORTH, true);

		assertEquals(DronePhase.SEARCHDIRECTION, searchDirection.getPhase());
		assertTrue(searchDirection.getActions().get(0).is(ActionName.STOP));
		assertEquals(1, searchDirection.getActions().size());
	}
}
