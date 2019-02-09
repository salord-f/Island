package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoutTest {

	@Test
	public void runTest() {
		JSONObject scout = new Scout(Direction.NORTH).run();
		assertEquals("scout", scout.getString("action"));
		assertEquals("N", scout.getJSONObject("parameters").getString("direction"));

		scout = new Scout(Direction.SOUTH).run();
		assertEquals("S", scout.getJSONObject("parameters").getString("direction"));
		assertNotEquals("N", scout.getJSONObject("parameters").getString("direction"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
		Scout scout = new Scout(Direction.NORTH);
		scout.saveResults(results);
		assertTrue(scout.is(ActionName.SCOUT));
		assertEquals(5, scout.getCost());
		assertEquals(Direction.NORTH, scout.getDirection());
		assertEquals(1, scout.getAltitude());
		assertTrue(scout.getResources().contains(Resource.FUR));
		assertTrue(scout.getResources().contains(Resource.WOOD));
	}

	@Test
	public void overOceanTest() {
		JSONObject results = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FISH\"] }, \"status\": \"OK\" }");
		Scout scout = new Scout(Direction.NORTH);
		scout.saveResults(results);
		assertTrue(scout.overOcean());

		JSONObject results2 = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
		Scout scout2 = new Scout(Direction.NORTH);
		scout.saveResults(results2);
		assertFalse(scout2.overOcean());

		JSONObject results3 = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\"] }, \"status\": \"OK\" }");
		Scout scout3 = new Scout(Direction.NORTH);
		scout.saveResults(results3);
		assertFalse(scout3.overOcean());

		JSONObject results4 = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"FISH\"] }, \"status\": \"OK\" }");
		Scout scout4 = new Scout(Direction.NORTH);
		scout.saveResults(results4);
		assertFalse(scout4.overOcean());
	}

	@Test
	public void containsResourceTest() {
		JSONObject results = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\",\"FISH\"] }, \"status\": \"OK\" }");
		Scout scout = new Scout(Direction.NORTH);
		scout.saveResults(results);
		assertTrue(scout.containsResource(Resource.FISH));
		assertTrue(scout.containsResource(Resource.FUR));
	}

	@Test
	public void containsResourceTest2() {
		JSONObject results = new JSONObject("{ \"cost\": 5, \"extras\": { \"altitude\": 1, \"resources\": [\"FUR\", \"WOOD\"] }, \"status\": \"OK\" }");
		Scout scout = new Scout(Direction.NORTH);
		scout.saveResults(results);
		assertTrue(scout.containsResource(Resource.FUR));
		assertFalse(scout.containsResource(Resource.FISH));
	}
}
