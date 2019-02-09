package fr.unice.polytech.si3.qgl.isg;

import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GoalTest {
	private Goal goalResource;
	private Goal goalManufactured;

	@Before
	public void init() {
		goalResource = new Goal(Resource.FUR, 10);
		goalManufactured = new Goal(Manufactured.GLASS, 10);
	}

	@Test
	public void getRequirementTest() {
		for (Manufactured manufactured : Manufactured.values()) {
			goalManufactured = new Goal(manufactured, 10);
			assertFalse(((Manufactured) goalManufactured.getRequirement()).getRequirements().isEmpty());
		}
	}

	@Test
	public void isValidTest() {
		assertTrue(goalResource.isValid(10));
		assertFalse(goalResource.isValid(9));
		goalResource.setCompleted(true);
		assertFalse(goalResource.isValid(10));
		goalResource.setCompleted(false);
		assertTrue(goalResource.isValid(10));
		goalResource.setImpossible(true);
		assertFalse(goalResource.isValid(10));
		goalResource.setCompleted(true);
		assertFalse(goalResource.isValid(10));
	}
}
