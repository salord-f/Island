package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeadingTest {

	@Test
	public void runTest() {
		Heading heading = new Heading(Direction.NORTH);
		assertEquals(Direction.NORTH, heading.getDirection());

		JSONObject jsonObject = new Heading(Direction.NORTH).run();
		assertEquals("heading", jsonObject.getString("action"));
		assertEquals("N", jsonObject.getJSONObject("parameters").getString("direction"));

		jsonObject = new Heading(Direction.SOUTH).run();
		assertEquals("S", jsonObject.getJSONObject("parameters").getString("direction"));
		assertNotEquals("N", jsonObject.getJSONObject("parameters").getString("direction"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 3, \"extras\": {}, \"status\": \"OK\" }");
		Heading heading = new Heading(Direction.NORTH);
		heading.saveResults(results);
		assertTrue(heading.is(ActionName.HEADING));
		assertEquals(3, heading.getCost());
	}
}