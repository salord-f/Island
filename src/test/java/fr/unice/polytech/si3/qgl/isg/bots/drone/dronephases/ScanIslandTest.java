package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScanIslandTest {

	@Test
	public void scanIslandOverGroundTest() {
		ScanIsland scanIsland = new ScanIsland();
		scanIsland.run(false, Direction.NORTH, new Position(0, 0));

		List<Action> actions = scanIsland.getActions();
		assertEquals(2, actions.size());
		assertTrue(actions.get(0).is(ActionName.FLY));
		assertTrue(actions.get(1).is(ActionName.SCAN));
		assertEquals(DronePhase.SCANISLAND, scanIsland.getPhase());
	}

	@Test
	public void scanIslandOverOceanTest() {
		ScanIsland scanIsland = new ScanIsland();
		scanIsland.run(true, Direction.NORTH, new Position(0, 0));

		List<Action> actions = scanIsland.getActions();
		assertEquals(1, actions.size());
		assertTrue(actions.get(0).is(ActionName.ECHO));
		assertEquals(DronePhase.ECHOANDTURN, scanIsland.getPhase());
	}
}
