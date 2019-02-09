package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CellMapTest {
	private CellMap cellMap;
	private Set<Biome> biomes;
	private List<String> creeks;

	@Before
	public void init() {
		cellMap = new CellMap();
		biomes = new HashSet<>();
		creeks = new ArrayList<>();
	}

	@Test
	public void initializeTest() {
		biomes.add(Biome.OCEAN);
		creeks.add("creekId001");

		cellMap.addCell(0, 0, biomes, creeks, "");
		assertFalse(cellMap.getMap().isEmpty());
	}

	@Test
	public void addCellTest() {
		biomes.add(Biome.TAIGA);
		creeks.add("creekId001");

		cellMap.addCell(0, 1, biomes, creeks, "");
		Cell cell = cellMap.getCell(0, 1);
		assertTrue(cell.getBiomes().contains(Biome.TAIGA));
		assertTrue(cell.getCreeks().contains("creekId001"));
		assertTrue(cell.getSite().contains(""));
		assertEquals("creekId001", cellMap.getCreeks().get(0).getCreeks().get(0));
	}

	@Test
	public void getCellByCreekIdTest() {
		cellMap.addCell(0, 1, biomes);
		cellMap.addCell(0, 2, biomes);
		creeks.add("creekId001");
		cellMap.addCell(0, 3, biomes, creeks, "");
		assertEquals(cellMap.getCell(0, 3).getCreeks().get(0), cellMap.getCellByCreek("creekId001").getCreeks().get(0));
	}

	@Test
	public void getCreeksTest() {
		cellMap.addCell(0, 1, biomes);
		cellMap.addCell(0, 2, biomes);
		assertEquals(0, cellMap.getCreeks().size());
		creeks.add("creekId001");
		cellMap.addCell(0, 3, biomes, creeks, "");
		assertEquals(1, cellMap.getCreeks().size());
	}

	@Test
	public void getSiteTest() {
		cellMap.addCell(0, 1, biomes);
		cellMap.addCell(0, 2, biomes);
		cellMap.addCell(0, 3, biomes);
		assertNull(cellMap.getSiteCell());
		cellMap.addCell(0, 4, biomes, new ArrayList<>(), "site");
		assertNotNull(cellMap.getSiteCell());
	}

	@Test
	public void nearestCreekToSiteTest() {
		List<String> cell1 = new ArrayList<>();
		cell1.add("cell1");
		List<String> cell2 = new ArrayList<>();
		cell2.add("cell2");
		List<String> cell3 = new ArrayList<>();
		cell3.add("cell3");

		cellMap.addCell(0, 10, biomes, cell1, "");
		cellMap.addCell(0, 20, biomes, cell2, "");
		cellMap.addCell(10, 30, biomes, cell3, "");
		cellMap.addCell(10, 40, biomes, new ArrayList<>(), "site");

		assertFalse(cellMap.getCell(0, 10).containsSite());
		assertFalse(cellMap.getCell(0, 20).containsSite());
		assertFalse(cellMap.getCell(10, 30).containsSite());
		assertEquals("cell1", (cellMap.getCell(0, 10)).getCreeks().get(0));
		assertEquals("cell2", (cellMap.getCell(0, 20)).getCreeks().get(0));
		assertEquals("cell3", (cellMap.getCell(10, 30)).getCreeks().get(0));
		assertEquals("cell3", cellMap.nearestCreekToSite());
	}


	@Test
	public void nearestCreekToResourceTest() {

		assertNull(cellMap.getCurrentCreek());
		List<String> cell1 = new ArrayList<>();
		cell1.add("cell1");
		List<String> cell2 = new ArrayList<>();
		cell2.add("cell2");
		List<String> cell3 = new ArrayList<>();
		cell3.add("cell3");

		Set<Biome> cell4 = new HashSet<>();
		cell4.add(Biome.OCEAN);
		Set<Biome> cell5 = new HashSet<>();
		cell5.add(Biome.TROPICAL_RAIN_FOREST);
		Set<Biome> cell6 = new HashSet<>();
		cell6.add(Biome.SUB_TROPICAL_DESERT);

		Map<Resource, Integer> resourcesNeeded = new HashMap<>();
		resourcesNeeded.put(Resource.WOOD, 1000);
		resourcesNeeded.put(Resource.FISH, 10);
		resourcesNeeded.put(Resource.ORE, 10);


		cellMap.addCell(0, 10, biomes, cell1, "");
		cellMap.addCell(0, 26, biomes, cell2, "");
		cellMap.addCell(0, 30, biomes, cell3, "");
		cellMap.addCell(0, 9, cell4);
		cellMap.addCell(0, 50, cell5);
		cellMap.addCell(0, 25, cell6);

		cellMap.setCurrentCreekToNearestResources(resourcesNeeded);

		assertEquals("cell2", cellMap.getCurrentCreek());
	}

	@Test
	public void nearestCreekToResourceErrorTest() {
		Map<Resource, Integer> resourcesNeeded = new HashMap<>();
		resourcesNeeded.put(Resource.WOOD, 1000);
		cellMap.setCurrentCreekToNearestResources(resourcesNeeded);
		Arrays.stream(Resource.values())
				.map(resource -> cellMap.getCurrentCreek())
				.forEach(Assert::assertNull);
	}

	@Test
	public void setVisitedCellTest() {
		cellMap.addCell(0, 0, biomes);
		cellMap.addCell(0, 1, biomes);
		cellMap.setVisitedCell(new Position(0, 0));

		// new Position to test getCell(Position)
		assertTrue(cellMap.getCell(new Position(0, 0)).isVisited());
		assertFalse(cellMap.getCell(new Position(0, 1)).isVisited());
	}

	@Test
	public void getNearestResourceZoneEmptyTest() {
		Map<Resource, Integer> resourcesNeeded = new HashMap<>();
		resourcesNeeded.put(Resource.WOOD, 1000);
		cellMap.setCurrentCreekToNearestResources(resourcesNeeded);
		assertTrue(cellMap.getNearestResourceZone(new HashMap<>()).isEmpty());
	}

	@Test
	public void getNearestResourceZoneOneCellTest() {
		biomes.add(Biome.TUNDRA);
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, 0, biomes, creeks, null);
		cellMap.setCurrentCreek("MyCreek");

		Map<Resource, Integer> resourceMap = new HashMap<>();
		resourceMap.put(Resource.FUR, -1);

		assertEquals(1, cellMap.getNearestResourceZone(resourceMap).size());
		assertTrue(cellMap.getNearestResourceZone(resourceMap).contains(cellMap.getCell(0, 0)));

	}

	@Test
	public void getNearestResourceZoneOneCellWrongConditionsTest() {
		biomes.add(Biome.TUNDRA);
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, 0, biomes, creeks, null);
		cellMap.setCurrentCreek("MyCreek");
		Map<Resource, Integer> resourceMap = new HashMap<>();
		resourceMap.put(Resource.WOOD, -1);

		assertTrue(cellMap.getNearestResourceZone(resourceMap).isEmpty());
	}

	@Test
	public void getNearestResourceZoneTwoDistantCellsTest() {
		biomes.add(Biome.TUNDRA);
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, 0, new HashSet<>(), creeks, null);
		cellMap.setCurrentCreek("MyCreek");
		cellMap.addCell(0, 2, biomes);
		cellMap.addCell(0, 3, biomes);

		Map<Resource, Integer> resourceMap = new HashMap<>();
		resourceMap.put(Resource.FUR, -1);

		assertTrue(cellMap.getNearestResourceZone(resourceMap).contains(cellMap.getCell(0, 2)));
		assertFalse(cellMap.getNearestResourceZone(resourceMap).contains(cellMap.getCell(0, 4)));
	}

	@Test
	public void getNearestResourceZoneTwoAdjacentCellsTest() {
		biomes.add(Biome.TUNDRA);
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, 0, new HashSet<>(), creeks, null);
		cellMap.setCurrentCreek("MyCreek");
		cellMap.addCell(0, 1, biomes);
		cellMap.addCell(0, 2, biomes);

		Map<Resource, Integer> resourceMap = new HashMap<>();
		resourceMap.put(Resource.FUR, -1);

		assertTrue(cellMap.getNearestResourceZone(resourceMap).contains(cellMap.getCell(0, 1)));
		assertTrue(cellMap.getNearestResourceZone(resourceMap).contains(cellMap.getCell(0, 2)));
	}

	@Test
	public void getNearestResourceZoneTwoEquidistantCellsTest() {
		biomes.add(Biome.TUNDRA);
		ArrayList<String> creeks = new ArrayList<>();
		creeks.add("MyCreek");
		cellMap.addCell(0, 0, new HashSet<>(), creeks, null);
		cellMap.setCurrentCreek("MyCreek");
		cellMap.addCell(0, 1, biomes);
		cellMap.addCell(0, -1, biomes);

		Map<Resource, Integer> resourceMap = new HashMap<>();
		resourceMap.put(Resource.FUR, -1);

		assertEquals(1, cellMap.getNearestResourceZone(resourceMap).size());
	}

	@Test
	public void estimateAndGetResourcesEmptyTest() {
		cellMap.estimateAndGetResources().forEach((resource, amount) -> assertEquals(0.0, amount, 0.0));
	}

	@Test
	public void estimateAndGetOneResourceTest() {
		biomes.add(Biome.TUNDRA);
		cellMap.addCell(0, 0, biomes);
		assertFalse(cellMap.getCell(0, 0).isEstimated());
		assertTrue(cellMap.estimateAndGetResources().get(Resource.FUR) != 0.0);
		assertTrue(cellMap.getCell(0, 0).isEstimated());
	}

	@Test
	public void estimateAndGetResourcesAllTest() {
		biomes.addAll(Arrays.asList(Biome.values()));
		cellMap.addCell(0, 0, biomes);
		assertFalse(cellMap.getCell(0, 0).isEstimated());
		List<Double> amounts = new ArrayList<>();
		cellMap.estimateAndGetResources().forEach((resource, amount) -> {
			assertTrue(0.0 != amount);
			amounts.add(amount);
		});

		assertTrue(cellMap.getCell(0, 0).isEstimated());
		List<Double> amountsCheck = new ArrayList<>(amounts);
		cellMap.estimateAndGetResources();
		assertTrue(amounts.containsAll(amountsCheck));
	}
}