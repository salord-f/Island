package fr.unice.polytech.si3.qgl.isg.map.cellmapsubtests;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FillMapTest {
	private CellMap cellMap;
	private Set<Biome> biomes;

	@Before
	public void init() {
		cellMap = new CellMap();
		biomes = new HashSet<>();
	}

	@Test
	public void fillMapHorizontalTest() {
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(0, 2, biomes);
		assertEquals(2, cellMap.getMap().size());
		assertNull(cellMap.getCell(0, 1));

		cellMap.fillMap(true);
		assertEquals(3, cellMap.getMap().size());
		assertNotNull(cellMap.getCell(0, 1));

		cellMap.fillMap(true);
		assertEquals(3, cellMap.getMap().size());
	}

	@Test
	public void fillMapVerticalTest() {
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes);
		assertEquals(2, cellMap.getMap().size());
		assertNull(cellMap.getCell(1, 0));

		cellMap.fillMap(true);
		assertEquals(3, cellMap.getMap().size());
		assertNotNull(cellMap.getCell(1, 0));

		cellMap.fillMap(true);
		assertEquals(3, cellMap.getMap().size());
	}

	@Test
	public void fillMapVerticalAndHorizontalTest() {
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes);
		cellMap.addCell(0, 2, biomes);
		assertEquals(3, cellMap.getMap().size());
		cellMap.fillMap(true);
		assertEquals(5, cellMap.getMap().size());
		cellMap.fillMap(true);
		assertEquals(5, cellMap.getMap().size());
	}

	@Test
	public void fillMapBiomesTest() {
		biomes.add(Biome.TUNDRA);
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes);
		cellMap.fillMap(true);
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.TUNDRA));
		assertEquals(1, cellMap.getCell(1, 0).getBiomes().size());
	}

	@Test
	public void fillMapMultipleBiomesIntersectionTest() {
		biomes.add(Biome.TUNDRA);
		biomes.add(Biome.BEACH);
		Set<Biome> biomes1 = new HashSet<>();
		biomes1.add(Biome.BEACH);
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes1);

		cellMap.fillMap(true);
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.BEACH));
		assertEquals(1, cellMap.getCell(1, 0).getBiomes().size());
	}

	@Test
	public void fillMapEmptyIntersectionTest() {
		biomes.add(Biome.TUNDRA);
		Set<Biome> biomes1 = new HashSet<>();
		biomes1.add(Biome.BEACH);
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes1);

		// Union is used on empty intersection
		cellMap.fillMap(true);
		assertFalse(cellMap.getCell(1, 0).getBiomes().isEmpty());
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.TUNDRA));
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.BEACH));
	}

	@Test
	public void fillMapMultipleBiomesJoinTest() {
		biomes.add(Biome.TUNDRA);
		biomes.add(Biome.BEACH);
		Set<Biome> biomes1 = new HashSet<>();
		biomes1.add(Biome.BEACH);
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(2, 0, biomes1);

		cellMap.fillMap(false);
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.TUNDRA));
		assertTrue(cellMap.getCell(1, 0).getBiomes().contains(Biome.BEACH));
		assertEquals(2, cellMap.getCell(1, 0).getBiomes().size());
	}

	@Test
	public void fillMapHorizontalLineTest() {
		biomes.add(Biome.TEMPERATE_RAIN_FOREST);
		for (int i = 0; i < 10; i++) {
			cellMap.addCell(i, 0, biomes);
			cellMap.addCell(i, 2, biomes);
		}
		cellMap.fillMap(true);
		assertEquals(30, cellMap.getMap().size());
		cellMap.getMap().forEach(c -> assertTrue(c.getBiomes().contains(Biome.TEMPERATE_RAIN_FOREST)));
	}

	@Test
	public void fillMapVerticalLineTest() {
		biomes.add(Biome.OCEAN);
		for (int i = 0; i < 10; i++) {
			cellMap.addCell(0, i, biomes);
			cellMap.addCell(2, i, biomes);
		}
		cellMap.fillMap(true);
		assertEquals(30, cellMap.getMap().size());
		cellMap.getMap().forEach(c -> assertTrue(c.getBiomes().contains(Biome.OCEAN)));
	}
}
