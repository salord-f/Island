package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.resources.IslandResource;
import fr.unice.polytech.si3.qgl.isg.resources.IslandResource.Amount;
import fr.unice.polytech.si3.qgl.isg.resources.IslandResource.Condition;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExploreTest {

	@Test
	public void runTest() {
		JSONObject explore = new Explore().run();
		assertEquals("explore", explore.getString("action"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{\n" +
				"  \"cost\": 5,\n" +
				"  \"extras\": {\n" +
				"    \"resources\": [\n" +
				"      { \"amount\": \"HIGH\", \"resource\": \"FUR\", \"cond\": \"FAIR\" },\n" +
				"      { \"amount\": \"LOW\", \"resource\": \"WOOD\", \"cond\": \"HARSH\" }\n" +
				"    ],\n" +
				"    \"pois\": [{\"kind\": \"Creek\", \"id\": \"43e3eb42-50f0-47c5-afa3-16cd3d50faff\"}]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}");
		Explore explore = new Explore();
		explore.saveResults(results);
		assertEquals(5, explore.getCost());

		IslandResource fur = explore.getResources().get(0);
		assertEquals(Resource.FUR, fur.getResource());
		assertEquals(Amount.HIGH, fur.getAmount());
		assertEquals(Condition.FAIR, fur.getCondition());

		IslandResource wood = explore.getResources().get(1);
		assertEquals(Resource.WOOD, wood.getResource());
		assertEquals(Amount.LOW, wood.getAmount());
		assertEquals(Condition.HARSH, wood.getCondition());

		assertEquals(1, explore.getCreeks().size());
		assertEquals("43e3eb42-50f0-47c5-afa3-16cd3d50faff", explore.getCreeks().get(0));

		assertTrue(explore.getSites().isEmpty());
	}

	@Test
	public void resultsSiteTest() {
		JSONObject results = new JSONObject("{\n" +
				"  \"cost\": 5,\n" +
				"  \"extras\": {\n" +
				"    \"resources\": [\n" +
				"      { \"amount\": \"HIGH\", \"resource\": \"FUR\", \"cond\": \"FAIR\" },\n" +
				"      { \"amount\": \"LOW\", \"resource\": \"WOOD\", \"cond\": \"HARSH\" }\n" +
				"    ],\n" +
				"    \"pois\": [{\"kind\": \"Site\", \"id\": \"43e3eb42-50f0-47c5-afa3-16cd3d50faff\"}]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}");

		Explore explore = new Explore();
		explore.saveResults(results);
		assertTrue(explore.is(ActionName.EXPLORE));

		assertFalse(explore.getSites().isEmpty());
		assertEquals("43e3eb42-50f0-47c5-afa3-16cd3d50faff", explore.getSites().get(0));
	}

	@Test
	public void resultsNoResourceTest() {
		JSONObject results = new JSONObject("{\n" +
				"  \"cost\": 5,\n" +
				"  \"extras\": {\n" +
				"    \"resources\": [\n" +
				"    ],\n" +
				"    \"pois\": [{\"kind\": \"Site\", \"id\": \"43e3eb42-50f0-47c5-afa3-16cd3d50faff\"}]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}");

		Explore explore = new Explore();
		explore.saveResults(results);
		assertTrue(explore.getResources().isEmpty());
	}

	@Test
	public void overOceanTrueTest() {
		JSONObject results = new JSONObject("{\n" +
				"  \"cost\": 5,\n" +
				"  \"extras\": {\n" +
				"    \"resources\": [\n" +
				"      { \"amount\": \"HIGH\", \"resource\": \"FISH\", \"cond\": \"FAIR\" },\n" +
				"    ],\n" +
				"    \"pois\": [{\"kind\": \"Site\", \"id\": \"43e3eb42-50f0-47c5-afa3-16cd3d50faff\"}]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}");

		Explore explore = new Explore();
		explore.saveResults(results);
		assertTrue(explore.overOcean());
	}

	@Test
	public void overCoeanFalseTest() {
		JSONObject results = new JSONObject("{\n" +
				"  \"cost\": 5,\n" +
				"  \"extras\": {\n" +
				"    \"resources\": [\n" +
				"      { \"amount\": \"HIGH\", \"resource\": \"FUR\", \"cond\": \"FAIR\" },\n" +
				"      { \"amount\": \"LOW\", \"resource\": \"WOOD\", \"cond\": \"HARSH\" }\n" +
				"    ],\n" +
				"    \"pois\": [{\"kind\": \"Site\", \"id\": \"43e3eb42-50f0-47c5-afa3-16cd3d50faff\"}]\n" +
				"  },\n" +
				"  \"status\": \"OK\"\n" +
				"}");

		Explore explore = new Explore();
		explore.saveResults(results);
		assertFalse(explore.overOcean());
	}
}
