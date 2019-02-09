package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Fly;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class EchoAndTurnTest {

	@Test
	public void alwaysGroundInFront() {
		EchoAndTurn echoAndTurn = new EchoAndTurn(new Position(0, 0), new AtomicBoolean(true), true);
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, null, false);
		assertEquals(DronePhase.FLYTO, echoAndTurn.getPhase());
	}

	@Test
	public void turnAboutTest() {
		Position pos = new Position(0, 0);
		pos.setFirstDirection(Direction.EAST);
		pos.setSecondDirection(Direction.SOUTH);
		EchoAndTurn echoAndTurn = new EchoAndTurn(pos, new AtomicBoolean(true), true);
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, null, true);

		assertTrue(echoAndTurn.getActions().get(0).is(ActionName.HEADING));
		assertEquals(Direction.NORTH, ((Heading) echoAndTurn.getActions().get(0)).getDirection());

		assertTrue(echoAndTurn.getActions().get(1).is(ActionName.FLY));

		assertTrue(echoAndTurn.getActions().get(2).is(ActionName.HEADING));
		assertEquals(Direction.EAST, ((Heading) echoAndTurn.getActions().get(2)).getDirection());

		assertTrue(echoAndTurn.getActions().get(3).is(ActionName.HEADING));
		assertEquals(Direction.SOUTH, ((Heading) echoAndTurn.getActions().get(3)).getDirection());

		assertTrue(echoAndTurn.getActions().get(4).is(ActionName.HEADING));
		assertEquals(Direction.WEST, ((Heading) echoAndTurn.getActions().get(4)).getDirection());

		assertEquals(DronePhase.SCANISLAND, echoAndTurn.getPhase());
	}

	@Test
	public void canLandAfterOneWay() {
		EchoAndTurn echoAndTurn = new EchoAndTurn(null, new AtomicBoolean(true), false);
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, null, true);

		assertEquals(DronePhase.LANDPHASE, echoAndTurn.getPhase());
	}

	@Test
	public void echoInSecondDirection() {
		Position pos = new Position(0, 0);
		pos.setFirstDirection(Direction.EAST);
		pos.setSecondDirection(Direction.SOUTH);
		EchoAndTurn echoAndTurn = new EchoAndTurn(pos, new AtomicBoolean(true), true);
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, null, false);

		assertTrue(echoAndTurn.getActions().get(0).is(ActionName.ECHO));
		assertEquals(Direction.SOUTH, ((Echo) echoAndTurn.getActions().get(0)).getDirection());
	}

	@Test
	public void alwaysInGroundScanArea() {
		Position pos = new Position(0, 0);
		pos.setFirstDirection(Direction.EAST);
		pos.setSecondDirection(Direction.SOUTH);
		EchoAndTurn echoAndTurn = new EchoAndTurn(pos, new AtomicBoolean(true), true);
		Echo echo = new Echo(Direction.SOUTH);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 1, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, null, false);

		assertTrue(echoAndTurn.getActions().get(0).is(ActionName.FLY));
	}

	@Test
	public void turnTest() {
		Position pos = new Position(0, 0);
		pos.setFirstDirection(Direction.EAST);
		pos.setSecondDirection(Direction.SOUTH);
		EchoAndTurn echoAndTurn = new EchoAndTurn(pos, new AtomicBoolean(true), true);
		Echo echo = new Echo(Direction.SOUTH);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		echoAndTurn.run(echo, Direction.EAST, Direction.EAST, false);

		assertTrue(echoAndTurn.getActions().get(0).is(ActionName.HEADING));
		assertEquals(Direction.SOUTH, ((Heading) echoAndTurn.getActions().get(0)).getDirection());

		assertTrue(echoAndTurn.getActions().get(1).is(ActionName.HEADING));
		assertEquals(Direction.WEST, ((Heading) echoAndTurn.getActions().get(1)).getDirection());

		assertEquals(DronePhase.SCANISLAND, echoAndTurn.getPhase());
	}

	@Test
	public void echoAfterFly() {
		Position pos = new Position(0, 0);
		pos.setFirstDirection(Direction.EAST);
		pos.setSecondDirection(Direction.SOUTH);
		EchoAndTurn echoAndTurn = new EchoAndTurn(pos, null, true);
		echoAndTurn.run(new Fly(), null, null, false);

		assertTrue(echoAndTurn.getActions().get(0).is(ActionName.ECHO));
		assertEquals(Direction.SOUTH, ((Echo) echoAndTurn.getActions().get(0)).getDirection());
	}
}
