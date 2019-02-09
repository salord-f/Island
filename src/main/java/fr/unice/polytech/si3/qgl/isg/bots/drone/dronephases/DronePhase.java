package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.Phase;
import fr.unice.polytech.si3.qgl.isg.bots.drone.Drone;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.Sailors;

/**
 * The different drone phases.
 * Going from one phase to another is done
 * inside the class associated to the phase.
 */
public enum DronePhase implements Phase {

	INITIALECHO("Initial echo") {
		public ActionAndPhase run(Drone drone) {
			return new InitialEcho().run(drone.getHeading());
		}
	},
	SEARCHDIRECTION("Search a direction") {
		public ActionAndPhase run(Drone drone) {
			return new SearchDirection(drone.getPosition(), drone.getHeading())
					.run(drone.getActionSequence().goingRight(), drone.getGroundDirection(), drone.getActionSequence().ozIsland());
		}
	},
	SEARCHISLAND("Search an island") {
		public ActionAndPhase run(Drone drone) {
			return new SearchIsland(drone.getPosition(), drone.getHeading())
					.run(drone.getGroundDirection());
		}
	},
	FLYTO("Flying to the island") {
		public ActionAndPhase run(Drone drone) {
			return new FlyTo()
					.run(drone.getHeading(),
							drone.getGroundDirection(),
							drone.getGroundDistance());
		}
	},
	SCANISLAND("Scanning the island") {
		public ActionAndPhase run(Drone drone) {
			return new ScanIsland()
					.run(drone.getActionSequence().lastScanOverOcean(),
							drone.getHeading(),
							drone.getPosition());
		}
	},
	ECHOANDTURN("Turn at the end of the island") {
		public ActionAndPhase run(Drone drone) {
			return new EchoAndTurn(drone.getPosition(), drone.isOneWay(), drone.getCellMap().noCreeks())
					.run(drone.getLastAction(),
							drone.getHeading(),
							drone.getOldHeading(),
							drone.getActionSequence().shouldTurn());
		}
	},
	LANDPHASE("Drone scanning finished, time to explore!") {
		// This method throws an exception as
		// it is never invoked, we switch to sailors before
		public ActionAndPhase run(Drone drone) {
			throw new UnsupportedOperationException();
		}
	};

	private final String phaseName;

	DronePhase(String phase) {
		this.phaseName = phase;
	}

	public abstract ActionAndPhase run(Drone drone);

	public ActionAndPhase run(Sailors sailors) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return phaseName;
	}
}
