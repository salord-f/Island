package fr.unice.polytech.si3.qgl.isg.resources;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceCheckTest {
	private ResourceCheck resourceCheck;
	private Map<Resource, Integer> resourcesNeeded;
	private CellMap cellMap;

	@Before
	public void init() {
		resourceCheck = new ResourceCheck();
		resourcesNeeded = new HashMap<>();
		Arrays.stream(Resource.values()).forEach(resource -> resourcesNeeded.put(resource, 0));
		cellMap = new CellMap();
	}

	@Test
	public void checkResourcesAmountEmptyTest() {
		assertTrue(resourceCheck.checkResourcesAmount(cellMap));
	}

	@Test
	public void checkResourcesAmountOneResourceFalseTest() {
		resourcesNeeded.put(Resource.FUR, 1);
		resourceCheck.setResourcesNeeded(resourcesNeeded);
		assertFalse(resourceCheck.checkResourcesAmount(cellMap));
	}

	@Test
	public void checkResourcesAmountOneResourceTrueTest() {
		resourcesNeeded.put(Resource.FUR, 1);
		resourceCheck.setResourcesNeeded(resourcesNeeded);
		Set<Biome> biomes = new HashSet<>();
		biomes.add(Biome.TUNDRA);
		assertFalse(resourceCheck.checkResourcesAmount(cellMap));
		cellMap.addCell(0, 0, biomes);
		assertTrue(resourceCheck.checkResourcesAmount(cellMap));
	}

	@Test
	public void checkResourcesAmountMultipleResourcesFalseTest() {
		resourcesNeeded.put(Resource.FUR, 1);
		resourcesNeeded.put(Resource.WOOD, 1);
		resourceCheck.setResourcesNeeded(resourcesNeeded);
		Set<Biome> biomes = new HashSet<>();
		biomes.add(Biome.TUNDRA);
		assertFalse(resourceCheck.checkResourcesAmount(cellMap));
		cellMap.addCell(0, 0, biomes);
		assertFalse(resourceCheck.checkResourcesAmount(cellMap));
	}

	@Test
	public void checkResourcesAmountMultipleResourcesTrueTest() {
		resourcesNeeded.put(Resource.FUR, 1);
		resourcesNeeded.put(Resource.WOOD, 1);
		resourceCheck.setResourcesNeeded(resourcesNeeded);
		Set<Biome> biomes = new HashSet<>();
		biomes.add(Biome.TUNDRA);
		biomes.add(Biome.TROPICAL_RAIN_FOREST);
		assertFalse(resourceCheck.checkResourcesAmount(cellMap));
		cellMap.addCell(0, 0, biomes);
		assertTrue(resourceCheck.checkResourcesAmount(cellMap));
	}
}
