package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellTest {

	private Set<Biome> biomes;
	private Cell cell;

	@Before
	public void init() {
		biomes = new HashSet<>();
		biomes.add(Biome.OCEAN);
		biomes.add(Biome.ALPINE);
		List<String> testCreekId = new ArrayList<>();
		String testSiteId = "siteId";
		testCreekId.add("creekId");
		cell = new Cell(new Position(0, 0), biomes, testCreekId, testSiteId);
	}

	@Test
	public void testMap() {
		assertTrue(cell.getCreeks().contains("creekId"));
		assertTrue(cell.getSite().contains("siteId"));
		assertTrue(cell.getBiomes().contains(Biome.ALPINE));
		assertTrue(cell.getBiomes().contains(Biome.OCEAN));
		assertFalse(cell.getBiomes().contains(Biome.BEACH));
	}

	@Test
	public void containsResourceTest() {
		assertFalse(cell.containsResource(Resource.FUR));
		assertTrue(cell.containsResource(Resource.FISH));
	}

}
