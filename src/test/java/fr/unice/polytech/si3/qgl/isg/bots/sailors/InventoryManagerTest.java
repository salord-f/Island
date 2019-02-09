package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Goal;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class InventoryManagerTest {

	private InventoryManager inventoryManager;
	private Map<Resource, Integer> resources;
	private Map<Manufactured, Integer> manufactured;
	private List<Goal> completedGoals;
	private List<Goal> uncompletedGoals;
	private Random rand = new Random();
	private int amount;

	@Before
	public void init() {
		inventoryManager = new InventoryManager();
		resources = inventoryManager.getResourcesInventory();
		manufactured = inventoryManager.getManufacturedInventory();
		completedGoals = new ArrayList<>();
		uncompletedGoals = new ArrayList<>();
		setRandomAmount();
	}

	private void setRandomAmount() {
		amount = rand.nextInt();
		amount = amount < 0 ? -amount : amount;
	}

	@Test
	public void addAmountResourceTest() {
		assertEquals(0, inventoryManager.getResourceAmount(Resource.WOOD));
		inventoryManager.addAmount(resources, Resource.WOOD, amount);
		assertEquals(amount, inventoryManager.getResourceAmount(Resource.WOOD));
	}

	@Test
	public void addAmountManufacturedTest() {
		assertEquals(0, inventoryManager.getManufacturedAmount(Manufactured.GLASS));
		inventoryManager.addAmount(manufactured, Manufactured.GLASS, amount);
		assertEquals(amount, inventoryManager.getManufacturedAmount(Manufactured.GLASS));
	}

	@Test
	public void canCraftOneResourceNeededTest() {
		for (int i = 0; i < 100; i++) {
			setRandomAmount();
			inventoryManager.addAmount(resources, Resource.FUR, amount);
			assertTrue(inventoryManager.canCraft(Manufactured.LEATHER, amount / 3));
			inventoryManager.removeAmount(resources, Resource.FUR, amount);
		}
	}

	@Test
	public void canCraftMultipleResourcesNeededTest() {
		for (int i = 0; i < 100; i++) {
			setRandomAmount();
			assertFalse(inventoryManager.canCraft(Manufactured.GLASS, amount / 10));
			inventoryManager.addAmount(resources, Resource.QUARTZ, amount);
			inventoryManager.addAmount(resources, Resource.WOOD, amount / 2);
			// amount/10 as we need 10 Quartz for one glass
			assertTrue(inventoryManager.canCraft(Manufactured.GLASS, amount / 10));
			assertFalse(inventoryManager.canCraft(Manufactured.GLASS, amount / 10 + 1));
			inventoryManager.removeAmount(resources, Resource.QUARTZ, amount);
			inventoryManager.removeAmount(resources, Resource.WOOD, amount / 2);
		}
	}

	@Test
	public void nothingLeftNeededTest() {
		assertTrue(inventoryManager.nothingLeftNeeded());

		uncompletedGoals.add(new Goal(Resource.ORE, 250));
		inventoryManager.updateInventoryNeeded(uncompletedGoals, completedGoals);
		assertFalse(inventoryManager.nothingLeftNeeded());

		completedGoals.add(new Goal(Resource.WOOD, 10));
		inventoryManager.updateInventoryNeeded(uncompletedGoals, completedGoals);
		assertFalse(inventoryManager.nothingLeftNeeded());

		completedGoals.clear();
		inventoryManager.updateInventoryNeeded(uncompletedGoals, completedGoals);
		assertFalse(inventoryManager.nothingLeftNeeded());
	}

	@Test
	public void updateInventoryNeededTest() {
		uncompletedGoals.add(new Goal(Resource.WOOD, 20));
		uncompletedGoals.add(new Goal(Resource.FUR, 200));
		uncompletedGoals.add(new Goal(Manufactured.RUM, 15));
		uncompletedGoals.add(new Goal(Resource.ORE, 250));
		uncompletedGoals.add(new Goal(Manufactured.LEATHER, 10));
		uncompletedGoals.add(new Goal(Manufactured.PLANK, 75));

		completedGoals.add(new Goal(Manufactured.INGOT, 75));
		completedGoals.add(new Goal(Resource.FRUITS, 75));

		inventoryManager.updateInventoryNeeded(completedGoals, uncompletedGoals);

		Map<Resource, Integer> resourcesNeeded = inventoryManager.getResourcesNeeded();
		Map<Manufactured, Integer> manufacturedNeeded = inventoryManager.getManufacturedNeeded();

		assertEquals(5, resourcesNeeded.size());
		assertEquals(3, manufacturedNeeded.size());

		assertEquals(250, (int) resourcesNeeded.get(Resource.ORE));
		assertEquals(233, (int) resourcesNeeded.get(Resource.FUR));
		assertEquals(92, (int) resourcesNeeded.get(Resource.FRUITS));
		assertEquals(41, (int) resourcesNeeded.get(Resource.WOOD));
		assertEquals(170, (int) resourcesNeeded.get(Resource.SUGAR_CANE));

		assertEquals(76, (int) manufacturedNeeded.get(Manufactured.PLANK));
		assertEquals(11, (int) manufacturedNeeded.get(Manufactured.LEATHER));
		assertEquals(16, (int) manufacturedNeeded.get(Manufactured.RUM));
	}

	@Test
	public void addManufacturedTest() {
		resources.put(Resource.FRUITS, 2);
		resources.put(Resource.SUGAR_CANE, 20);
		manufactured.put(Manufactured.RUM, 2);

		inventoryManager.addManufactured(manufactured, resources);

		assertEquals(4, (int) inventoryManager.getManufacturedInventory().get(Manufactured.RUM));
		assertEquals(0, (int) inventoryManager.getResourcesInventory().get(Resource.FRUITS));
		assertEquals(0, (int) inventoryManager.getResourcesInventory().get(Resource.SUGAR_CANE));
	}

	@Test
	public void isNeededTest() {
		uncompletedGoals.add(new Goal(Resource.WOOD, 20));
		uncompletedGoals.add(new Goal(Resource.FUR, 200));

		inventoryManager.updateInventoryNeeded(completedGoals, uncompletedGoals);
		assertFalse(inventoryManager.isNeeded(Resource.QUARTZ));
		assertTrue(inventoryManager.isNeeded(Resource.FUR));
	}
}
