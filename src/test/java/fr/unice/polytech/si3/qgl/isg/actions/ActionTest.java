package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;

public class ActionTest {

	@Test(expected = EmptyStackException.class)
	public void saveResultsWrongCostTest() {
		Echo echo = new Echo(Direction.NORTH);
		echo.saveResults(new JSONObject("{" +
				"\"extras\": { " +
				"\"range\": 0, " +
				"\"found\": \"OUT_OF_RANGE\" }, " +
				"\"status\": \"OK\"" +
				"}"));
	}

	@Test
	public void saveResultsWrongCost2Test() {
		Echo echo = new Echo(Direction.NORTH);
		echo.saveResults(new JSONObject("{ \"cost\": wrongCost, " +
				"\"extras\": { " +
				"\"range\": 0, " +
				"\"found\": \"OUT_OF_RANGE\" }, " +
				"\"status\": \"OK\"" +
				"}"));
		assertEquals(0, echo.getCost());
	}
}
