package fr.unice.polytech.si3.qgl.isg.bots;

import fr.unice.polytech.si3.qgl.isg.actions.*;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionSequenceTest {
	private ActionSequence actionSequence;

	@Before
	public void init() {
		actionSequence = new ActionSequence();
	}

	@Test
	public void emergencyStopTest() {
		actionSequence.add(new Move(Direction.EAST));
		actionSequence.add(new Heading(Direction.NORTH));
		actionSequence.getNextAction();
		assertEquals(2, actionSequence.getSequence().size());
		actionSequence.emergencyStop();
		actionSequence.getNextAction();
		assertEquals(ActionName.STOP, actionSequence.getLastAction().getName());
	}

	@Test
	public void goingRightEmptySequenceTest() {
		assertFalse(actionSequence.goingRight());
	}

	@Test
	public void goingRightTrueTest() {
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		Echo echo2 = new Echo(Direction.WEST);
		echo2.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 20, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));

		actionSequence.add(echo, echo2, new Move(Direction.EAST));
		actionSequence.getNextAction();
		actionSequence.getNextAction();
		actionSequence.getNextAction();

		assertTrue(actionSequence.goingRight());
	}

	@Test
	public void goingRightFalseTest() {
		Echo echo = new Echo(Direction.EAST);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 20, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		Echo echo2 = new Echo(Direction.WEST);
		echo2.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));

		actionSequence.add(echo, echo2, new Move(Direction.EAST));
		actionSequence.getNextAction();
		actionSequence.getNextAction();
		actionSequence.getNextAction();

		assertFalse(actionSequence.goingRight());
	}

	@Test
	public void shouldTurnTrueTest() {
		actionSequence.add(new Move(Direction.NORTH),
				new Move(Direction.EAST),
				new Heading(Direction.NORTH),
				new Move(Direction.EAST),
				new Move(Direction.EAST),
				new Move(Direction.EAST));
		for (int i = 0; i < 5; i++) {
			actionSequence.getNextAction();
			assertFalse(actionSequence.shouldTurn());
		}
		actionSequence.getNextAction();
		assertTrue(actionSequence.shouldTurn());
	}

	@Test
	public void shouldTurnFalseFirstConditionTest() {
		actionSequence.add(new Move(Direction.NORTH),
				new Move(Direction.EAST),
				new Move(Direction.NORTH),
				new Move(Direction.EAST),
				new Move(Direction.EAST),
				new Move(Direction.EAST));
		for (int i = 0; i < 5; i++) {
			actionSequence.getNextAction();
			assertFalse(actionSequence.shouldTurn());
		}
		actionSequence.getNextAction();
		assertFalse(actionSequence.shouldTurn());
	}

	@Test
	public void shouldTurnFalseSecondConditionTest() {
		actionSequence.add(new Heading(Direction.NORTH),
				new Move(Direction.EAST),
				new Heading(Direction.NORTH),
				new Move(Direction.EAST),
				new Move(Direction.EAST),
				new Move(Direction.EAST));
		for (int i = 0; i < 5; i++) {
			actionSequence.getNextAction();
			assertFalse(actionSequence.shouldTurn());
		}
		actionSequence.getNextAction();
		assertFalse(actionSequence.shouldTurn());
	}

	@Test
	public void lastScanOverOceanEmptyTest() {
		assertFalse(actionSequence.lastScanOverOcean());
	}

	@Test
	public void lastScanOverOceanTrueTest() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"OCEAN\",\n" +
				"      ],\n" +
				"      \"sites\": [\"276bdc0a-5853-4325-8321-f62ab2d2b389\"]\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(new Position(0, 0));
		scan.saveResults(results);
		assertTrue(scan.overOcean());

		actionSequence.add(scan);
		actionSequence.getNextAction();
		assertTrue(actionSequence.lastScanOverOcean());
	}

	@Test
	public void lastScanOverOceanFalseTest() {
		actionSequence.add(new Move(Direction.NORTH));
		actionSequence.getNextAction();
		assertFalse(actionSequence.lastScanOverOcean());
	}

	@Test
	public void lastScanOverOceanFalse2Test() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"TUNDRA\",\n" +
				"      ],\n" +
				"      \"sites\": [\"276bdc0a-5853-4325-8321-f62ab2d2b389\"]\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(new Position(0, 0));
		scan.saveResults(results);
		assertFalse(scan.overOcean());

		actionSequence.add(scan);
		actionSequence.getNextAction();
		assertFalse(actionSequence.lastScanOverOcean());
	}

	@Test
	public void saveResultsTrueTest() {
		JSONObject jsonObject = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }");

		actionSequence.add(new Echo(Direction.WEST));
		actionSequence.getNextAction();
		actionSequence.saveResults(jsonObject);

		assertEquals(1, actionSequence.getLastAction().getCost());
	}

	@Test
	public void saveResultsFalseTest() {
		JSONObject jsonObject = new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }");

		actionSequence.add(new Stop());
		actionSequence.getNextAction();
		actionSequence.saveResults(jsonObject);

		assertEquals(0, actionSequence.getLastAction().getCost());
	}
}
