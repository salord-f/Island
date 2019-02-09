package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlyToTest {
	@Test
	public void flyToTest() {
		FlyTo flyTo = new FlyTo();
		flyTo.run(Direction.NORTH, Direction.NORTH, 5);

		assertEquals(5, flyTo.getActions().size());
		flyTo.getActions().forEach(action -> assertTrue(action.is(ActionName.FLY)));
		assertEquals(DronePhase.SCANISLAND, flyTo.getPhase());
	}

	@Test
	public void flyToWithBadHeading() {
		FlyTo flyTo = new FlyTo();
		flyTo.run(Direction.WEST, Direction.NORTH, 2);

		assertEquals(3, flyTo.getActions().size());
		assertTrue(flyTo.getActions().get(0).is(ActionName.HEADING));
		assertEquals(Direction.NORTH, ((Heading) flyTo.getActions().get(0)).getDirection());
		assertTrue(flyTo.getActions().get(1).is(ActionName.FLY));
		assertTrue(flyTo.getActions().get(2).is(ActionName.FLY));
	}

	@Test
	public void flyToWithGroundDistanceAt0() {
		FlyTo flyTo = new FlyTo();
		flyTo.run(Direction.NORTH, Direction.NORTH, 0);
		assertEquals(0, flyTo.getActions().size());
		assertEquals(DronePhase.SCANISLAND, flyTo.getPhase());
	}
}
