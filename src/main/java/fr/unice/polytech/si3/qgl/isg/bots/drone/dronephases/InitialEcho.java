package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;

/**
 * Initial Echo
 *
 * @author Florian
 * @version 03/04/2018
 */
public class InitialEcho extends ActionAndPhase {

	public InitialEcho() {
		super(DronePhase.INITIALECHO);
	}

	/**
	 * Returns 3 Echo actions, one in each possible direction for the Drone
	 */
	public ActionAndPhase run(Direction heading) {
		super.actions.add(new Echo(heading.getLeft()));
		super.actions.add(new Echo(heading.getRight()));
		super.actions.add(new Echo(heading));
		super.phase = DronePhase.SEARCHDIRECTION;
		return this;
	}
}
