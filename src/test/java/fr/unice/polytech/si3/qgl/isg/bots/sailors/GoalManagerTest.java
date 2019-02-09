package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GoalManagerTest {
	private GoalManager goalManager;
	private Contract contract;

	@Before
	public void init() {
		String contractValue = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 151, \"resource\": \"RUM\" },\n" +
				"    { \"amount\": 20, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 150, \"resource\": \"LEATHER\" },\n" +
				"    { \"amount\": 250, \"resource\": \"QUARTZ\" },\n" +
				"    { \"amount\": 200, \"resource\": \"FUR\" },\n" +
				"    { \"amount\": 10, \"resource\": \"GLASS\" },\n" +
				"    { \"amount\": 100, \"resource\": \"PLANK\" },\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";

		contract = new Contract();
		contract.initialize(contractValue);
		goalManager = new GoalManager(contract);
		goalManager.initialize(10000);
	}

	@After
	public void reset() {
		contract.initialize("");
	}

	@Test
	public void checkGoalsTest() {
		assertEquals(0, goalManager.getCompletedGoals().size());
		assertEquals(7, goalManager.getUncompletedGoals().size());

		Map<Resource, Integer> resourceIntegerMap = new HashMap<>();
		Map<Manufactured, Integer> manufacturedIntegerMap = new HashMap<>();

		for (Resource resource : Resource.values()) {
			resourceIntegerMap.put(resource, 1000);
		}
		for (Manufactured manufactured : Manufactured.values()) {
			manufacturedIntegerMap.put(manufactured, 1000);
		}

		goalManager.checkGoals(resourceIntegerMap, manufacturedIntegerMap);

		assertEquals(7, goalManager.getCompletedGoals().size());
		assertEquals(0, goalManager.getUncompletedGoals().size());
	}

	@Test
	public void checkGoalsAdvancedTest() {
		assertEquals(0, goalManager.getCompletedGoals().size());
		assertEquals(7, goalManager.getUncompletedGoals().size());

		Map<Resource, Integer> resourceIntegerMap = new HashMap<>();
		Map<Manufactured, Integer> manufacturedIntegerMap = new HashMap<>();

		resourceIntegerMap.put(Resource.WOOD, 20);
		resourceIntegerMap.put(Resource.QUARTZ, 200);
		resourceIntegerMap.put(Resource.FUR, 150);

		manufacturedIntegerMap.put(Manufactured.RUM, 1000);
		manufacturedIntegerMap.put(Manufactured.LEATHER, 0);
		manufacturedIntegerMap.put(Manufactured.GLASS, 10);
		manufacturedIntegerMap.put(Manufactured.PLANK, 10);

		goalManager.checkGoals(resourceIntegerMap, manufacturedIntegerMap);

		assertEquals(3, goalManager.getCompletedGoals().size());
		assertEquals(4, goalManager.getUncompletedGoals().size());

		resourceIntegerMap.put(Resource.WOOD, 0);

		goalManager.checkGoals(resourceIntegerMap, manufacturedIntegerMap);

		assertEquals(2, goalManager.getCompletedGoals().size());
		assertEquals(5, goalManager.getUncompletedGoals().size());
	}

	@Test
	public void noGoalLeftTest() {
		assertFalse(goalManager.noGoalLeft());
		goalManager.getGoals().clear();
		assertTrue(goalManager.noGoalLeft());
	}
}
