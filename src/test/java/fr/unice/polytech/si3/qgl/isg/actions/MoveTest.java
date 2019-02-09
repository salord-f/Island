package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

	@Test
	public void runTest() {
		JSONObject move = new Move(Direction.NORTH).run();
		assertEquals("move_to", move.getString("action"));
		assertEquals("N", move.getJSONObject("parameters").getString("direction"));

		move = new Move(Direction.SOUTH).run();
		assertEquals("S", move.getJSONObject("parameters").getString("direction"));
		assertNotEquals("E", move.getJSONObject("parameters").getString("direction"));
	}

	@Test
	public void resultsTest() {
		JSONObject results = new JSONObject("{ \"cost\": 6, \"extras\": { }, \"status\": \"OK\" }");
		Move move = new Move(Direction.SOUTH);
		move.saveResults(results);
		assertTrue(move.is(ActionName.MOVE));
		assertEquals(6, move.getCost());
		assertEquals(Direction.SOUTH, move.getDirection());
	}
}
