package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.actions.Explore;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases.SailorsPhase;
import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SailorsTest {
	private CellMap cellMap;
	private Contract contract;
	private List<String> creek;
	private Set<Biome> biomes;
	private Sailors sailors;
	private ActionSequence sequence;

	@Before
	public void init() {
		cellMap = new CellMap();
		contract = new Contract();
		creek = new ArrayList<>();
		biomes = new HashSet<>();

		String json = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 2, \"resource\": \"WOOD\" },\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contract.initialize(json);
		creek.add("creek");
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		cellMap.addCell(0, 0, biomes, creek, null);
		cellMap.addCell(0, 1, biomes);

		sailors = new Sailors(new ActionSequence(), cellMap, contract);
		sequence = sailors.getActionSequence();
	}

	@Test
	public void moveAndExploitTestEmergency() { //TODO Cas with resources needed empty.
		//INITIALAND PHASE
		sailors.run(10000);
		sequence.getNextAction();
		sailors.update();
	}

	@Test
	public void moveAndExploitTest() {
		//INITIALAND PHASE
		sailors.run(10000);
		sequence.getNextAction();
		sailors.update();

		sailors.setPhase(SailorsPhase.MOVEANDEXPLOIT);
		Action explore = new Explore();
		explore.saveResults(new JSONObject("{\n" +
				"    \"cost\": 5,\n" +
				"    \"extras\": {\n" +
				"      \"resources\": [{ \n" +
				"        \"amount\": \"HIGH\"," +
				"        \"resource\": \"WOOD\"," +
				"        \"cond\":\"FAIR\"" +
				"      }],\n" +
				"      \"pois\": []\n" +
				"    },\n" +
				"  }"));
		sequence.getSequence().add(explore);
		sequence.getNextAction();
		sailors.update();

		sailors.run(10000);
		assertEquals(ActionName.MOVE, sequence.getNextAction().getName());
		sailors.update();

		sailors.run(10000);
		assertEquals(ActionName.MOVE, sequence.getNextAction().getName());
		sailors.update();

		sailors.run(10000);
		assertEquals(ActionName.MOVE, sequence.getNextAction().getName());
		sailors.update();

		sailors.run(10000);
		assertEquals(ActionName.EXPLORE, sequence.getNextAction().getName());
		sailors.update();

		sequence.getSequence().add(explore);
		sequence.getNextAction();
		sailors.update();

		sailors.run(10000);
		assertEquals(ActionName.EXPLOIT, sequence.getNextAction().getName());
		sailors.update();
	}
}
