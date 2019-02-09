package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Fly;
import fr.unice.polytech.si3.qgl.isg.actions.Scan;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;

public class ScanIsland extends ActionAndPhase {

	public ScanIsland() {
		super(DronePhase.SCANISLAND);
	}

	/**
	 * Scan and fly until the drone is over ocean.
	 */
	public ActionAndPhase run(boolean lastScanOverOcean, Direction heading, Position position) {
		if (lastScanOverOcean) {
			super.actions.add(new Echo(heading));
			super.phase = DronePhase.ECHOANDTURN;
		} else {
			super.actions.add(new Fly());
			super.actions.add(new Scan(position));
		}
		return this;
	}
}