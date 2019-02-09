package fr.unice.polytech.si3.qgl.isg;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContractTest {
	private Contract contractToFulfill;

	@Before
	public void init() {
		contractToFulfill = new Contract();
		contractToFulfill.initialize("");
	}

	@Test
	public void NoJsonFileGetDirection() {
		String contract = "";
		contractToFulfill.initialize(contract);
		assertNull(contractToFulfill.getDirection());
	}

	@Test
	public void NoJsonFileGetBudget() {
		String contract = "";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getBudget());
	}

	@Test
	public void NoJsonFileGetGoals() {
		String contract = "";
		contractToFulfill.initialize(contract);
		assertTrue(contractToFulfill.getGoals().isEmpty());
	}

	@Test
	public void NoJsonFileGetMen() {
		String contract = "";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getMen());
	}

	@Test
	public void EmptyJsonFileGetDirection() {
		String contract = "{ }";
		contractToFulfill.initialize(contract);
		assertNull(contractToFulfill.getDirection());
	}

	@Test
	public void EmptyJsonFileGetBudget() {
		String contract = "{ }";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getBudget());
	}

	@Test
	public void EmptyJsonFileGetGoals() {
		String contract = "{ }";
		contractToFulfill.initialize(contract);
		assertTrue(contractToFulfill.getGoals().isEmpty());
	}

	@Test
	public void EmptyJsonFileGetMen() {
		String contract = "{ }";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getMen());
	}

	@Test
	public void MissingInformationJsonFileGetDirection() {
		String contract = "{ \n" +
				"  \"men\": ,\n" +
				"  \"budget\": ,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": , \"resource\": \"\" },\n" +
				"    { \"amount\": , \"resource\": \"\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertNull(contractToFulfill.getDirection());
	}

	@Test
	public void MissingInformationJsonFileGetBudget() {
		String contract = "{ \n" +
				"  \"men\": ,\n" +
				"  \"budget\": ,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": , \"resource\": \"\" },\n" +
				"    { \"amount\": , \"resource\": \"\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getBudget());
	}

	@Test
	public void MissingInformationJsonFileGetGoals() {
		String contract = "{ \n" +
				"  \"men\": ,\n" +
				"  \"budget\": ,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": , \"resource\": \"\" },\n" +
				"    { \"amount\": , \"resource\": \"\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertTrue(contractToFulfill.getGoals().isEmpty());
	}

	@Test
	public void MissingInformationJsonFileGetMen() {
		String contract = "{ \n" +
				"  \"men\": ,\n" +
				"  \"budget\": ,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": , \"resource\": \"\" },\n" +
				"    { \"amount\": , \"resource\": \"\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getMen());
	}

	@Test
	public void CorrectJsonFileGetDirection() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(Direction.WEST, contractToFulfill.getDirection());
	}

	@Test
	public void CorrectJsonFileGetBudget() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(10000, contractToFulfill.getBudget());
	}

	@Test
	public void CorrectJsonFileGetGoals() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertFalse(contractToFulfill.getGoals().isEmpty());
		assertEquals(600, contractToFulfill.getGoals().get(0).getAmount());
		assertEquals(Resource.WOOD, contractToFulfill.getGoals().get(0).getRequirement());
		assertEquals(200, contractToFulfill.getGoals().get(1).getAmount());
		assertEquals(Manufactured.GLASS, contractToFulfill.getGoals().get(1).getRequirement());
	}

	@Test
	public void CorrectJsonFileGetMen() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(12, contractToFulfill.getMen());
	}

	@Test
	public void IncorrectJsonFileGetGoals() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"ThisIsNotWood\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);

		assertEquals(200, contractToFulfill.getGoals().get(0).getAmount());
		assertEquals(Manufactured.GLASS, contractToFulfill.getGoals().get(0).getRequirement());
		assertEquals(1, contractToFulfill.getGoals().size());
	}

	@Test
	public void IncorrectJsonFileGetMen() {
		String contract = "{ \n" +
				"  \"men\": ThisIsNotANumber,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getMen());
	}

	@Test
	public void IncorrectJsonFileGetBudget() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": ThisIsNotANumber,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"W\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertEquals(0, contractToFulfill.getBudget());
	}

	@Test
	public void IncorrectJsonFileGetDirection() {
		String contract = "{ \n" +
				"  \"men\": 12,\n" +
				"  \"budget\": 10000,\n" +
				"  \"contracts\": [\n" +
				"    { \"amount\": 600, \"resource\": \"WOOD\" },\n" +
				"    { \"amount\": 200, \"resource\": \"GLASS\" }\n" +
				"  ],\n" +
				"  \"heading\": \"NotADirection\"\n" +
				"}";
		contractToFulfill.initialize(contract);
		assertNull(contractToFulfill.getDirection());
	}

	@Test
	public void testToString() {
		Goal goal = new Goal(Manufactured.GLASS, 20);
		assertEquals("Goal : GLASS, amount : 20", goal.toString());
	}
}
