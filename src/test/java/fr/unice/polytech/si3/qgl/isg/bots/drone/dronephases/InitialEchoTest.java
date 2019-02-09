package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InitialEchoTest {

	@Test
	public void initialEchoTest() {
		InitialEcho initialEcho = new InitialEcho();
		initialEcho.run(Direction.NORTH);

		assertEquals(3, initialEcho.getActions().size());
		initialEcho.getActions().forEach(action -> assertTrue(action.is(ActionName.ECHO)));
		assertEquals(DronePhase.SEARCHDIRECTION, initialEcho.getPhase());
	}

	@Test
	public void initialEchoAllDirections() {
		InitialEcho initialEcho = new InitialEcho();
		List<Action> actions = initialEcho.getActions();
		for (Direction direction : Direction.values()) {
			actions.clear();
			initialEcho.run(direction);
			assertEquals(3, actions.size());
			initialEcho.getActions().forEach(action -> assertTrue(action.is(ActionName.ECHO)));

			assertEquals(direction.getLeft(), ((Echo) actions.get(0)).getDirection());
			assertEquals(direction.getRight(), ((Echo) actions.get(1)).getDirection());
			assertEquals(direction, ((Echo) actions.get(2)).getDirection());

			assertEquals(DronePhase.SEARCHDIRECTION, initialEcho.getPhase());
		}
	}
}
