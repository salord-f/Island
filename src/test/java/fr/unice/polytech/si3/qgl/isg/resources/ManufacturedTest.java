package fr.unice.polytech.si3.qgl.isg.resources;

import org.junit.Test;

import static org.junit.Assert.*;

public class ManufacturedTest {
	@Test
	public void fromStringTest() {
		for (Manufactured manufactured : Manufactured.values()) {
			assertNotNull(Manufactured.fromString(manufactured.name()));
		}
	}

	@Test
	public void TypeTest() {
		for (Manufactured manufactured : Manufactured.values()) {
			assertEquals(Type.MANUFACTURED, manufactured.getType());
		}
	}

	@Test
	public void RequirementsTest() {
		for (Manufactured manufactured : Manufactured.values()) {
			assertFalse(manufactured.getRequirements().isEmpty());
		}
	}

	@Test
	public void amountCreatedTest() {
		for (Manufactured manufactured : Manufactured.values()) {
			assertTrue(manufactured.getAmountCreated() >= 1);
		}
	}

	@Test
	public void getRealResourcesAmountTest() {
		assertEquals(10, Manufactured.PLANK.getRealResourcesAmount(1, 39));
		assertEquals(99, Manufactured.LEATHER.getRealResourcesAmount(3, 33));
		assertEquals(0, Manufactured.GLASS.getRealResourcesAmount(5, 0));
	}

	@Test
	public void getRealManufacturedAmountTest() {
		assertEquals(37, Manufactured.PLANK.getRealManufacturedAmount(33));
		assertEquals(33, Manufactured.RUM.getRealManufacturedAmount(30));
		assertEquals(2, Manufactured.GLASS.getRealManufacturedAmount(1));
		assertEquals(2, Manufactured.PLANK.getRealManufacturedAmount(1));
	}
}
