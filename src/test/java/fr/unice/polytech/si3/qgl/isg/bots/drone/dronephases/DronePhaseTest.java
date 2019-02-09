package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.bots.drone.Drone;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.Sailors;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DronePhaseTest {
	private Drone drone;
	private ActionSequence sequence;

	@Before
	public void init() {
		sequence = new ActionSequence();
		drone = new Drone(sequence, new CellMap());
		drone.setHeading(Direction.NORTH);
		sequence.add(new Echo(Direction.NORTH), new Echo(Direction.EAST));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void dronePhaseWrongTest() {
		Sailors sailors = new Sailors(new ActionSequence(), new CellMap(), new Contract());
		for (DronePhase phase : DronePhase.values()) {
			phase.run(sailors);
		}
	}

	@Test
	public void dronePhasesNameTest() {
		for (DronePhase phase : DronePhase.values()) {
			assertNotNull(phase.toString());
		}
	}

	@Test
	public void dronePhaseTest() {
		sequence.add(new Echo(Direction.NORTH));
		sequence.getNextAction();
		drone.update();
		for (DronePhase phase : DronePhase.values()) {
			ActionAndPhase actionAndPhase;
			if (phase != DronePhase.LANDPHASE) {
				actionAndPhase = phase.run(drone);
				assertTrue(!actionAndPhase.getActions().isEmpty() || actionAndPhase.getPhase() != phase);
			}
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void dronePhaseLandPhaseError() {
		DronePhase.LANDPHASE.run(drone);
	}
}
