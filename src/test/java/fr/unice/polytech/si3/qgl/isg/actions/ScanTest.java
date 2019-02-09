package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScanTest {
	private Position position = new Position(0, 0);

	@Test
	public void runTest() {
		JSONObject scan = new Scan(position).run();
		assertEquals("scan", scan.getString("action"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{\"cost\": 2, " +
				"\"extras\": { " +
				"\"biomes\": [\"GLACIER\", \"ALPINE\"]," +
				"\"creeks\": [], " +
				"\"sites\": []},}");
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertEquals(2, scan.getCost());
		assertTrue(scan.getBiomeList().contains(Biome.GLACIER));
		assertTrue(scan.getBiomeList().contains(Biome.ALPINE));
		assertEquals(0, scan.getCreeks().size());
		assertTrue(scan.getSite().isEmpty());
	}

	@Test
	public void resultsCreekTest() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 3,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [\"d51b00e3-c3bf-428c-87f4-716dcd4e3cc3\"],\n" +
				"      \"biomes\": [\n" +
				"        \"OCEAN\",\n" +
				"        \"MANGROVE\"\n" +
				"      ],\n" +
				"      \"sites\": []\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertTrue(scan.is(ActionName.SCAN));
		assertEquals(3, scan.getCost());
		assertTrue(scan.getBiomeList().contains(Biome.MANGROVE));
		assertEquals("d51b00e3-c3bf-428c-87f4-716dcd4e3cc3", scan.getCreeks().get(0));
		assertTrue(scan.getSite().isEmpty());
	}

	@Test
	public void resultsSiteTest() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"MANGROVE\",\n" +
				"        \"BEACH\"\n" +
				"      ],\n" +
				"      \"sites\": [\"276bdc0a-5853-4325-8321-f62ab2d2b389\"]\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertTrue(scan.is(ActionName.SCAN));
		assertEquals(4, scan.getCost());
		assertTrue(scan.getBiomeList().contains(Biome.BEACH));
		assertTrue(scan.getCreeks().isEmpty());
		assertEquals("276bdc0a-5853-4325-8321-f62ab2d2b389", scan.getSite());
	}

	@Test
	public void resultsNoSiteTest() {
		Scan scan = new Scan(new Position(0, 1));
		scan.parseResults(new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"MANGROVE\",\n" +
				"        \"BEACH\"\n" +
				"      ],\n" +
				"      \"sites\": []\n" +
				"    },\n" +
				"  }"));
		assertEquals("", scan.getSite());
	}

	@Test
	public void overOceanTrueTest() {
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
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertTrue(scan.overOcean());
	}

	@Test
	public void overOceanFalseBecauseGroundAndOceanTest() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"OCEAN\",\n" +
				"        \"BEACH\"\n" +
				"      ],\n" +
				"      \"sites\": [\"276bdc0a-5853-4325-8321-f62ab2d2b389\"]\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertFalse(scan.overOcean());
	}

	@Test
	public void overOceanFalseBecauseGroundTest() {
		JSONObject results = new JSONObject("{\n" +
				"    \"cost\": 4,\n" +
				"    \"extras\": {\n" +
				"      \"creeks\": [],\n" +
				"      \"biomes\": [\n" +
				"        \"BEACH\"\n" +
				"      ],\n" +
				"      \"sites\": [\"276bdc0a-5853-4325-8321-f62ab2d2b389\"]\n" +
				"    },\n" +
				"  }");
		Scan scan = new Scan(position);
		scan.saveResults(results);
		assertFalse(scan.overOcean());
	}
}
