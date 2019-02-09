package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.actions.ActionName;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CraftManufacturedTest {
	private InventoryManager inventoryManager;

	@Before
	public void init() {
		inventoryManager = new InventoryManager();
		inventoryManager.addResource(Resource.QUARTZ, 9);
		inventoryManager.addResource(Resource.WOOD, 5);
		inventoryManager.addAmount(inventoryManager.getManufacturedNeeded(), Manufactured.GLASS, 1);
	}

	@Test
	public void cantCraftManufacturedTest() {
		CraftManufactured craftManufactured = new CraftManufactured();
		craftManufactured.run(inventoryManager);

		assertEquals(SailorsPhase.MOVEANDEXPLOIT, craftManufactured.getPhase());
	}

	@Test
	public void craftManufacturedTest() {
		inventoryManager.addResource(Resource.QUARTZ, 1);
		CraftManufactured craftManufactured = new CraftManufactured();
		craftManufactured.run(inventoryManager);

		assertTrue(craftManufactured.getActions().get(0).is(ActionName.TRANSFORM));
		assertEquals(1, craftManufactured.getActions().size());
		assertEquals(SailorsPhase.CRAFTMANUFACTURED, craftManufactured.getPhase());
	}

	@Test
	public void noNeededToCraftManufacturedTest() {
		CraftManufactured craftManufactured = new CraftManufactured();
		inventoryManager.getManufacturedNeeded().remove(Manufactured.GLASS);
		craftManufactured.run(inventoryManager);

		assertEquals(SailorsPhase.MOVEANDEXPLOIT, craftManufactured.getPhase());
	}
}
