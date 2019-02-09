package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResourceZoneTest {
	private ResourceZone resourceZone;
	private List<Cell> cellList;
	private Set<Biome> biomes;
	private Random random = new Random();

	@Before
	public void init() {
		cellList = new ArrayList<>();
		resourceZone = new ResourceZone(cellList);
		biomes = new HashSet<>();
	}

	@Test
	public void createResourceZoneTest() {
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cellList.add(new Cell(i, j, biomes));
			}
		}
		for (int i = 0; i < 10; i++) {
			int a = random.nextInt(10);
			int b = random.nextInt(10);
			assertEquals(100, resourceZone.createResourceZone(resourceZone.getCell(a, b), Resource.WOOD).size());
			assertEquals(100, resourceZone.createResourceZone(resourceZone.getCell(b, a), Resource.WOOD).size());
		}
	}

	@Test
	public void createResourceZoneCrossTest() {
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		for (int i = 0; i <= 5; i++) {
			cellList.add(new Cell(i, 0, biomes));
		}
		for (int j = 1; j <= 3; j++) {
			cellList.add(new Cell(j, 1, biomes));
			cellList.add(new Cell(j, -1, biomes));
		}
		cellList.add(new Cell(2, 2, biomes));
		cellList.add(new Cell(2, -2, biomes));

		for (int i = 0; i < 10; i++) {
			// 6 + 3*2 + 1 + 1 = 14
			assertEquals(14, resourceZone.createResourceZone(resourceZone.getCell(random.nextInt(6), 0), Resource.WOOD).size());
			assertEquals(14, resourceZone.createResourceZone(resourceZone.getCell(random.nextInt(3) + 1, 1), Resource.WOOD).size());
			assertEquals(14, resourceZone.createResourceZone(resourceZone.getCell(2, 2), Resource.WOOD).size());
		}
	}

	@Test
	public void createResourceZoneWithHolesTest() {
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		Set<Biome> wrongBiome = new HashSet<>();
		wrongBiome.add(Biome.BEACH);

		// Creates a square with correct biomes on the edges only
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == 0 || i == 9 || j == 0 || j == 9) {
					cellList.add(new Cell(i, j, biomes));
				} else cellList.add(new Cell(i, j, wrongBiome));
			}
		}
		// 10 + 10 + 8 + 8 = 36
		assertEquals(36, resourceZone.createResourceZone(resourceZone.getCell(0, 0), Resource.WOOD).size());
	}

	@Test
	public void createResourceZoneLineWithHole() {
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		Set<Biome> wrongBiome = new HashSet<>();
		wrongBiome.add(Biome.BEACH);

		for (int i = 0; i < 10; i++) {
			if (i != 5) {
				cellList.add(new Cell(i, 0, biomes));
			} else cellList.add(new Cell(i, 0, wrongBiome));
		}

		assertEquals(5, resourceZone.createResourceZone(resourceZone.getCell(0, 0), Resource.WOOD).size());
		assertEquals(4, resourceZone.createResourceZone(resourceZone.getCell(7, 0), Resource.WOOD).size());
		assertTrue(resourceZone.createResourceZone(resourceZone.getCell(5, 0), Resource.WOOD).isEmpty());
	}

	@Test
	public void createResourceZoneOneCellTest() {
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		Set<Biome> wrongBiome = new HashSet<>();
		wrongBiome.add(Biome.BEACH);

		for (int i = 0; i < 10; i++) {
			if (i == 5) {
				cellList.add(new Cell(i, 0, biomes));
			} else cellList.add(new Cell(i, 0, wrongBiome));
		}

		assertTrue(resourceZone.createResourceZone(resourceZone.getCell(0, 0), Resource.WOOD).isEmpty());
		assertTrue(resourceZone.createResourceZone(resourceZone.getCell(7, 0), Resource.WOOD).isEmpty());
		assertEquals(1, resourceZone.createResourceZone(resourceZone.getCell(5, 0), Resource.WOOD).size());
	}
}
