package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class ChangingZoneTest {

	@Test
	public void nothingLeftNeeded() {
		ChangingZone changingZone = new ChangingZone(new ArrayList<>(), new CellMap(), new InventoryManager());
		changingZone.run();

		assertEquals(SailorsPhase.ENDEXPLORATION, changingZone.getPhase());
	}

	@Test
	public void noCellsToExploit() {
		CellMap cellMap = new CellMap();
		Set<Biome> biomes = new HashSet<>();
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		List<String> creek = new ArrayList<>();
		creek.add("creek");
		cellMap.addCell(0, 0, biomes, creek, "");
		cellMap.getCell(0, 0).setVisited(true);
		cellMap.setCurrentCreek("creek");
		InventoryManager inventoryManager = new InventoryManager();
		inventoryManager.addAmount(inventoryManager.getResourcesNeeded(), Resource.WOOD, 10);

		ChangingZone changingZone = new ChangingZone(new ArrayList<>(), cellMap, inventoryManager);
		changingZone.run();

		assertEquals(SailorsPhase.ENDEXPLORATION, changingZone.getPhase());
	}

	@Test
	public void moveBetweenCells() {
		CellMap cellMap = new CellMap();
		Set<Biome> biomes = new HashSet<>();
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		List<String> creek = new ArrayList<>();
		creek.add("creek");
		cellMap.addCell(0, 0, biomes, creek, "");
		cellMap.addCell(0, 1, biomes);
		cellMap.setCurrentCreek("creek");
		List<Cell> cellToExploit = new ArrayList<>();
		cellToExploit.add(cellMap.getMap().get(1));
		InventoryManager inventoryManager = new InventoryManager();
		inventoryManager.addAmount(inventoryManager.getResourcesNeeded(), Resource.WOOD, 10);

		ChangingZone changingZone = new ChangingZone(cellToExploit, cellMap, inventoryManager);
		changingZone.run();

		assertEquals(SailorsPhase.MOVEBETWEENCELLS, changingZone.getPhase());
	}

}
