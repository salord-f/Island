package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.GoalManager;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InitialLandTest {

	private Contract contract;

	@Before
	public void init() {
		contract = new Contract();
	}

	@Test
	public void initialLand() {
		String json = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 2, \"resource\": \"WOOD\" },\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contract.initialize(json);
		List<String> creek = new ArrayList<>();
		creek.add("creek");
		Set<Biome> biome = new HashSet<>();
		biome.add(Biome.TROPICAL_RAIN_FOREST);

		CellMap cellMap = new CellMap();
		cellMap.addCell(0, 0, biome, creek, "site");
		cellMap.addCell(0, 1, biome);

		InitialLand initialLand = new InitialLand();
		initialLand.run(cellMap, new GoalManager(contract), new InventoryManager(), 100000);

		assertTrue(initialLand.getActions().get(0).is(ActionName.LAND));
		assertEquals(SailorsPhase.MOVEANDEXPLOIT, initialLand.getPhase());
	}

	@Test
	public void stopInitialLand() {
		String json = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contract.initialize(json);
		InitialLand initialLand = new InitialLand();
		initialLand.run(new CellMap(), new GoalManager(contract), new InventoryManager(), 0);

		assertTrue(initialLand.getActions().get(0).is(ActionName.STOP));
	}
}
