package fr.unice.polytech.si3.qgl.isg.bots.drone;

import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Fly;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.actions.Scan;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DroneTest {

	private ActionSequence sequence;
	private Drone drone;
	private CellMap cellMap;

	@Before
	public void init() {
		sequence = new ActionSequence();
		cellMap = new CellMap();
		drone = new Drone(sequence, cellMap);
		drone.setHeading(Direction.EAST);
	}

	@Test
	public void updateFlyTest() {
		sequence.add(new Fly());
		sequence.getNextAction();
		drone.update();

		assertEquals(Direction.EAST, drone.getHeading());
	}

	@Test
	public void updateHeadingTest() {
		sequence.add(new Heading(Direction.SOUTH));
		sequence.getNextAction();
		drone.update();

		assertEquals(Direction.SOUTH, drone.getHeading());
		assertTrue(drone.getPosition().equalsPosition(1, -1));
	}

	@Test
	public void updateEchoGroundFoundTest() {
		drone.getPosition().setFirstDirection(Direction.EAST);
		Echo echo = new Echo(Direction.SOUTH);
		echo.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		sequence.add(echo);
		sequence.getNextAction();
		drone.update();

		assertEquals(Direction.SOUTH, drone.getPosition().getFirstDirection());
		assertEquals(Direction.EAST, drone.getPosition().getSecondDirection());
		assertEquals(Direction.SOUTH, drone.getGroundDirection());
		assertEquals(10, drone.getGroundDistance());
	}

	@Test
	public void updateScanTest() {
		Scan scan = new Scan(new Position(0, 1));
		scan.saveResults(new JSONObject("{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"], \"sites\": [\"id\"]}, \"status\": \"OK\"}"));
		sequence.add(scan);
		sequence.getNextAction();
		drone.update();

		assertTrue(cellMap.getCell(0, 0).getBiomes().contains(Biome.BEACH));
		assertEquals("id", cellMap.getCell(0, 0).getSite());
		assertEquals("id", cellMap.getCell(0, 0).getCreeks().get(0));
	}

	@Test
	public void checkOzTest() {
		Echo echo1 = new Echo(Direction.NORTH);
		Echo echo2 = new Echo(Direction.SOUTH);
		Echo echo3 = new Echo(Direction.WEST);

		echo1.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		echo2.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));
		echo3.saveResults(new JSONObject("{ \"cost\": 1, \"extras\": { \"range\": 10, \"found\": \"GROUND\" }, \"status\": \"OK\" }"));

		sequence.add(echo1, echo2, echo3);
		sequence.getNextAction();
		sequence.getNextAction();
		sequence.getNextAction();

		assertTrue(sequence.ozIsland());
	}
}
