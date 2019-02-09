package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlyTest {

	@Test
	public void runTest() {
		JSONObject fly = new Fly().run();
		assertEquals("fly", fly.getString("action"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 2, \"extras\": {}, \"status\": \"OK\" }");
		Fly fly = new Fly();
		fly.saveResults(results);
		assertTrue(fly.is(ActionName.FLY));
		assertEquals(2, fly.getCost());
	}
}
