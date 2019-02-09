package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StopTest {

	@Test
	public void runTest() {
		JSONObject stop = new Stop().run();
		assertEquals("stop", stop.getString("action"));
	}

	@Test
	public void resultsTest() {
		Stop stop = new Stop();
		stop.saveResults(new JSONObject("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }"));
		assertEquals(6, stop.getCost());
	}
}
