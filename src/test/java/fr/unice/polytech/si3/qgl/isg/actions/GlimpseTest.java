package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class GlimpseTest {

	@Test
	public void runTest() {
		JSONObject glimpse = new Glimpse(Direction.NORTH, 4).run();
		assertEquals("glimpse", glimpse.getString("action"));
		assertEquals("N", glimpse.getJSONObject("parameters").getString("direction"));
		assertEquals(4, glimpse.getJSONObject("parameters").getInt("range"));
	}

	@Test
	public void runTest2() {
		JSONObject glimpse = new Glimpse(Direction.SOUTH, 3).run();
		assertEquals("S", glimpse.getJSONObject("parameters").getString("direction"));
		assertEquals(3, glimpse.getJSONObject("parameters").getInt("range"));
		assertNotEquals("E", glimpse.getJSONObject("parameters").getString("direction"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rangeConditionNegativeTest() {
		new Glimpse(Direction.NORTH, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rangeConditionOverTest() {
		new Glimpse(Direction.NORTH, 5);
	}

	@Test
	public void resultsTestRange4() {
		JSONObject results = new JSONObject("{ \n" +
				"  \"cost\": 3,\n" +
				"  \"extras\": {\n" +
				"    \"asked_range\": 4,\n" +
				"    \"report\": [\n" +
				"      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
				"      [ [ \"OCEAN\", 100  ] ],\n" +
				"      [ \"TUNDRA\", \"TAIGA\" ],\n" +
				"      [ \"GLACIER\" ]\n" +
				"    ]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}  ");
		Glimpse glimpse = new Glimpse(Direction.SOUTH, 4);
		glimpse.saveResults(results);
		assertEquals(3, glimpse.getCost());
		assertEquals(Direction.SOUTH, glimpse.getDirection());
		assertEquals(4, glimpse.getRange());

		assertTrue(glimpse.getBiomes().get(4).containsKey(Biome.GLACIER));
		assertEquals(-1.0, glimpse.getBiomes().get(4).get(Biome.GLACIER), 0.0);

		assertTrue(glimpse.getBiomes().get(3).containsKey(Biome.TUNDRA));
		assertEquals(-1.0, glimpse.getBiomes().get(3).get(Biome.TUNDRA), 0.0);

		assertTrue(glimpse.getBiomes().get(3).containsKey(Biome.TAIGA));
		assertEquals(-1.0, glimpse.getBiomes().get(3).get(Biome.TAIGA), 0.0);

		assertTrue(glimpse.getBiomes().get(2).containsKey(Biome.OCEAN));
		assertEquals(100, glimpse.getBiomes().get(2).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.OCEAN));
		assertEquals(40.65, glimpse.getBiomes().get(1).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.BEACH));
		assertEquals(59.35, glimpse.getBiomes().get(1).get(Biome.BEACH), 0.0);
	}

	@Test
	public void resultsTestRange3() {
		JSONObject results = new JSONObject("{ \n" +
				"  \"cost\": 3,\n" +
				"  \"extras\": {\n" +
				"    \"asked_range\": 4,\n" +
				"    \"report\": [\n" +
				"      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
				"      [ [ \"OCEAN\", 100 ] ],\n" +
				"      [ \"TUNDRA\", \"TAIGA\" ],\n" +
				"    ]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}  ");
		Glimpse glimpse = new Glimpse(Direction.EAST, 3);
		glimpse.saveResults(results);
		assertEquals(3, glimpse.getCost());
		assertEquals(Direction.EAST, glimpse.getDirection());
		assertEquals(3, glimpse.getRange());

		assertTrue(glimpse.getBiomes().get(3).containsKey(Biome.TUNDRA));
		assertEquals(-1.0, glimpse.getBiomes().get(3).get(Biome.TUNDRA), 0.0);

		assertTrue(glimpse.getBiomes().get(3).containsKey(Biome.TAIGA));
		assertEquals(-1.0, glimpse.getBiomes().get(3).get(Biome.TAIGA), 0.0);

		assertTrue(glimpse.getBiomes().get(2).containsKey(Biome.OCEAN));
		assertEquals(100, glimpse.getBiomes().get(2).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.OCEAN));
		assertEquals(40.65, glimpse.getBiomes().get(1).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.BEACH));
		assertEquals(59.35, glimpse.getBiomes().get(1).get(Biome.BEACH), 0.0);
	}

	@Test
	public void resultsTestRange2() {
		JSONObject results = new JSONObject("{ \n" +
				"  \"cost\": 3,\n" +
				"  \"extras\": {\n" +
				"    \"asked_range\": 4,\n" +
				"    \"report\": [\n" +
				"      [ [ \"BEACH\", 59.35 ], [ \"OCEAN\", 40.65 ] ],\n" +
				"      [ [ \"OCEAN\", 100 ] ],\n" +
				"    ]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}  ");
		Glimpse glimpse = new Glimpse(Direction.WEST, 2);
		glimpse.saveResults(results);
		assertEquals(3, glimpse.getCost());
		assertEquals(Direction.WEST, glimpse.getDirection());
		assertEquals(2, glimpse.getRange());

		assertTrue(glimpse.getBiomes().get(2).containsKey(Biome.OCEAN));
		assertEquals(100, glimpse.getBiomes().get(2).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.OCEAN));
		assertEquals(40.65, glimpse.getBiomes().get(1).get(Biome.OCEAN), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.BEACH));
		assertEquals(59.35, glimpse.getBiomes().get(1).get(Biome.BEACH), 0.0);
	}

	@Test
	public void resultsTestRange1() {
		JSONObject results = new JSONObject("{ \n" +
				"  \"cost\": 3,\n" +
				"  \"extras\": {\n" +
				"    \"asked_range\": 4,\n" +
				"    \"report\": [\n" +
				"      [ [ \"OCEAN\", 70.2  ], [ \"LAKE\", 29.8  ] ],\n" +
				"    ]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}  ");
		Glimpse glimpse = new Glimpse(Direction.NORTH, 1);
		glimpse.saveResults(results);
		assertEquals(3, glimpse.getCost());
		assertEquals(Direction.NORTH, glimpse.getDirection());
		assertEquals(1, glimpse.getRange());

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.LAKE));
		assertEquals(29.8, glimpse.getBiomes().get(1).get(Biome.LAKE), 0.0);

		assertTrue(glimpse.getBiomes().get(1).containsKey(Biome.OCEAN));
		assertEquals(70.2, glimpse.getBiomes().get(1).get(Biome.OCEAN), 0.0);
	}
}
