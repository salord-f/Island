package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchIslandTest {
	private Position position = new Position(0, 0);

	@Before
	public void init() {
		position.setFirstDirection(Direction.NORTH);
		position.setSecondDirection(Direction.WEST);
	}

	@Test
	public void searchIslandGroundFoundTest() {
		SearchIsland searchIsland = new SearchIsland(position, Direction.NORTH);
		searchIsland.run(Direction.NORTH);

		assertTrue(searchIsland.getActions().isEmpty());
		assertEquals(DronePhase.FLYTO, searchIsland.getPhase());
	}

	@Test
	public void searchIslandGroundNotFoundFirstDirectionTest() {
		SearchIsland searchIsland = new SearchIsland(position, Direction.NORTH);
		searchIsland.run(null);

		List<Action> actions = searchIsland.getActions();
		assertEquals(2, actions.size());
		assertEquals(DronePhase.SEARCHISLAND, searchIsland.getPhase());

		assertEquals(Direction.WEST, ((Heading) actions.get(0)).getDirection());
		assertEquals(Direction.WEST, ((Echo) actions.get(1)).getDirection());
	}

	@Test
	public void searchIslandNotFoundSecondDirectionTest() {
		SearchIsland searchIsland = new SearchIsland(position, Direction.SOUTH);
		searchIsland.run(null);

		List<Action> actions = searchIsland.getActions();
		assertEquals(2, actions.size());
		assertEquals(DronePhase.SEARCHISLAND, searchIsland.getPhase());

		assertEquals(Direction.NORTH, ((Heading) actions.get(0)).getDirection());
		assertEquals(Direction.NORTH, ((Echo) actions.get(1)).getDirection());
	}
}
