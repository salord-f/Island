package fr.unice.polytech.si3.qgl.isg.map.cellmapsubtests;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortCellZoneTest {

	private CellMap cellMap;
	private Set<Biome> biomes;
	private List<Cell> cellZone;

	@Before
	public void init() {
		cellMap = new CellMap();
		biomes = new HashSet<>();
		cellZone = new ArrayList<>();
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, -1, biomes, creeks, null);
		cellMap.setCurrentCreek("MyCreek");
	}

	/**
	 * Helper method to remove some code duplication
	 */
	private void fillY() {
		for (int i = 2; i >= 0; i--) {
			cellMap.addCell(0, i, biomes);
			cellMap.addCell(1, i, biomes);
			cellZone.add(cellMap.getCell(0, i));
			cellZone.add(cellMap.getCell(1, i));
		}
	}

	@Test
	public void sortedCellZoneEmptyTest() {
		cellZone = cellMap.sortCellZone(cellZone);
		assertTrue(cellZone.isEmpty());
	}

	@Test
	public void sortedCellZoneOneLineRightTest() {
		for (int i = 4; i >= 0; i--) {
			cellMap.addCell(i, 0, biomes);
			cellZone.add(cellMap.getCell(i, 0));
		}

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(5, cellZone.size());

		for (int i = 0; i < 5; i++) {
			assertTrue(cellZone.contains(cellMap.getCell(i, 0)));
			assertEquals(cellZone.get(i), cellMap.getCell(i, 0));
		}
	}

	@Test
	public void sortedCellZoneOneLineLeftTest() {
		for (int i = 4; i >= 0; i--) {
			cellMap.addCell(-i, 0, biomes);
			cellZone.add(cellMap.getCell(-i, 0));
		}

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(5, cellZone.size());

		for (int i = 0; i < 5; i++) {
			assertTrue(cellZone.contains(cellMap.getCell(-i, 0)));
			assertEquals(cellZone.get(i), cellMap.getCell(-i, 0));
		}
	}

	@Test
	public void sortedCellZoneTwoLineRightTest() {
		for (int i = 2; i >= 0; i--) {
			cellMap.addCell(i, 0, biomes);
			cellMap.addCell(i, 1, biomes);
			cellZone.add(cellMap.getCell(i, 0));
			cellZone.add(cellMap.getCell(i, 1));
		}

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(6, cellZone.size());

		assertEquals(cellZone.get(0), cellMap.getCell(0, 0));
		assertEquals(cellZone.get(1), cellMap.getCell(1, 0));
		assertEquals(cellZone.get(2), cellMap.getCell(2, 0));
		assertEquals(cellZone.get(3), cellMap.getCell(2, 1));
		assertEquals(cellZone.get(4), cellMap.getCell(1, 1));
		assertEquals(cellZone.get(5), cellMap.getCell(0, 1));
	}

	@Test
	public void sortedCellZoneTwoLineLeftTest() {
		for (int i = 2; i >= 0; i--) {
			cellMap.addCell(-i, 0, biomes);
			cellZone.add(cellMap.getCell(-i, 0));
			cellMap.addCell(-i, 1, biomes);
			cellZone.add(cellMap.getCell(-i, 1));
		}

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(6, cellZone.size());

		assertEquals(cellZone.get(0), cellMap.getCell(0, 0));
		assertEquals(cellZone.get(1), cellMap.getCell(-1, 0));
		assertEquals(cellZone.get(2), cellMap.getCell(-2, 0));
		assertEquals(cellZone.get(3), cellMap.getCell(-2, 1));
		assertEquals(cellZone.get(4), cellMap.getCell(-1, 1));
		assertEquals(cellZone.get(5), cellMap.getCell(0, 1));
	}

	@Test
	public void sortedCellZoneTreeLineLeftTest() {
		fillY();

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(6, cellZone.size());

		assertEquals(cellZone.get(0), cellMap.getCell(0, 0));
		assertEquals(cellZone.get(1), cellMap.getCell(1, 0));
		assertEquals(cellZone.get(2), cellMap.getCell(1, 1));
		assertEquals(cellZone.get(3), cellMap.getCell(0, 1));
		assertEquals(cellZone.get(4), cellMap.getCell(0, 2));
		assertEquals(cellZone.get(5), cellMap.getCell(1, 2));
	}

	@Test
	public void sortedCellZoneTreeLineLeftTestWithOneCellAloneAtTheEnd() {
		fillY();
		cellMap.addCell(2, 1, biomes);
		cellZone.add(cellMap.getCell(2, 1));

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(7, cellZone.size());

		assertEquals(cellZone.get(0), cellMap.getCell(0, 0));
		assertEquals(cellZone.get(1), cellMap.getCell(1, 0));
		assertEquals(cellZone.get(2), cellMap.getCell(1, 1));
		assertEquals(cellZone.get(3), cellMap.getCell(0, 1));
		assertEquals(cellZone.get(4), cellMap.getCell(0, 2));
		assertEquals(cellZone.get(5), cellMap.getCell(1, 2));
		assertEquals(cellZone.get(6), cellMap.getCell(2, 1));
	}

	@Test
	public void sortedCellZoneTreeLineLeftTestWithOneCellAloneAtStart() {
		fillY();
		cellMap.addCell(-1, 1, biomes);
		cellZone.add(cellMap.getCell(-1, 1));

		cellZone = cellMap.sortCellZone(cellZone);
		assertEquals(7, cellZone.size());

		assertEquals(cellZone.get(0), cellMap.getCell(0, 0));
		assertEquals(cellZone.get(1), cellMap.getCell(1, 0));
		assertEquals(cellZone.get(2), cellMap.getCell(1, 1));
		assertEquals(cellZone.get(3), cellMap.getCell(0, 1));
		assertEquals(cellZone.get(4), cellMap.getCell(-1, 1));
		assertEquals(cellZone.get(5), cellMap.getCell(0, 2));
		assertEquals(cellZone.get(6), cellMap.getCell(1, 2));
	}


}
