package fr.unice.polytech.si3.qgl.isg.resources;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class IslandResourceTest {

	@Test
	public void validConditionsTest() {

		IslandResource islandResource = new IslandResource(IslandResource.Amount.HIGH, IslandResource.Condition.HARSH, Resource.WOOD);
		assertFalse(islandResource.validConditions());

		islandResource = new IslandResource(IslandResource.Amount.LOW, IslandResource.Condition.FAIR, Resource.WOOD);
		assertFalse(islandResource.validConditions());

		islandResource = new IslandResource(IslandResource.Amount.MEDIUM, IslandResource.Condition.EASY, Resource.WOOD);
		assertTrue(islandResource.validConditions());
	}
}
