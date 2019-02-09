package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class EchoTest {

	@Test
	public void runTest() {
		JSONObject echo = new Echo(Direction.NORTH).run();
		assertEquals("echo", echo.getString("action"));
		assertEquals("N", echo.getJSONObject("parameters").getString("direction"));
		echo = new Echo(Direction.SOUTH).run();
		assertEquals("S", echo.getJSONObject("parameters").getString("direction"));
		assertNotEquals("E", echo.getJSONObject("parameters").getString("direction"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 1, " +
				"\"extras\": { " +
				"\"range\": 0, " +
				"\"found\": \"OUT_OF_RANGE\" }, " +
				"\"status\": \"OK\"" +
				"}");
		Echo echo = new Echo(Direction.NORTH);
		echo.saveResults(results);
		assertEquals(1, echo.getCost());
		assertEquals(Direction.NORTH, echo.getDirection());
		assertEquals(0, echo.getRange());
		assertEquals("OUT_OF_RANGE", echo.getFound());
	}

	@Test
	public void resultsTestGround() {
		JSONObject results = new JSONObject("{ \"cost\": 5, " +
				"\"extras\": { " +
				"\"range\": 1, " +
				"\"found\": \"GROUND\" }, " +
				"\"status\": \"OK\"" +
				"}");
		Echo echo = new Echo(Direction.NORTH);
		echo.saveResults(results);
		assertTrue(echo.is(ActionName.ECHO));
		assertNotEquals(1, echo.getCost());
		assertEquals(1, echo.getRange());
		assertEquals("GROUND", echo.getFound());
	}
}
