package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class LandTest {

	@Test
	public void runTest() {
		JSONObject land = new Land("creekId", 12).run();
		assertEquals("land", land.getString("action"));
		assertEquals("creekId", land.getJSONObject("parameters").getString("creek"));
		assertEquals(12, land.getJSONObject("parameters").getInt("people"));

		land = new Land("creekId_2", 42).run();
		assertEquals("creekId_2", land.getJSONObject("parameters").getString("creek"));
		assertNotEquals(12, land.getJSONObject("parameters").getInt("people"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void sailorNumberConditionTest() {
		new Land("", -1);
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 15, \"extras\": { }, \"status\": \"OK\" }");
		Land land = new Land("creekId", 12);
		land.saveResults(results);
		assertTrue(land.is(ActionName.LAND));
		assertEquals(15, land.getCost());
		assertEquals(12, land.getSailors());
		assertEquals("creekId", land.getCreek());
	}
}
