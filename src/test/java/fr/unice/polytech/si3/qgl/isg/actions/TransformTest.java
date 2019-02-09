package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransformTest {

	@Test
	public void runTest() {
		Transform transform = new Transform(Manufactured.GLASS, 1);
		JSONObject transformRun = transform.run();
		assertEquals("transform", transformRun.getString("action"));
		assertEquals(5, transformRun.getJSONObject("parameters").get("WOOD"));
		assertEquals(10, transformRun.getJSONObject("parameters").get("QUARTZ"));
		assertEquals(Manufactured.GLASS.getRequirements(), transform.getResourcesList());
		assertEquals(1, transform.getWantedAmount());

	}

	@Test
	public void runTest2() {
		JSONObject transform = new Transform(Manufactured.PLANK, 10).run();
		assertEquals("transform", transform.getString("action"));
		assertEquals(3, transform.getJSONObject("parameters").get("WOOD"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 5, \"extras\": { \"production\": 5, \"kind\": \"GLASS\" },\"status\": \"OK\" }");
		Transform transform = new Transform(Manufactured.GLASS, 5);
		transform.saveResults(results);
		assertTrue(transform.getProductsList().containsKey(Manufactured.GLASS));
		assertEquals(5, transform.getTransformedAmount());
	}
}
