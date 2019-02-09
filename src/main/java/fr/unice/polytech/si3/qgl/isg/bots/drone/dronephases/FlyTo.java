package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Fly;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;

public class FlyTo extends ActionAndPhase {

	public FlyTo() {
		super(DronePhase.FLYTO);
	}

	/**
	 * Makes the drone fly straight to the ground
	 * and switch to scanning the island.
	 */
	public ActionAndPhase run(Direction heading, Direction groundDirection, int groundDistance) {
		if (heading != groundDirection) {
			super.actions.add(new Heading(groundDirection));
		}
		if (groundDistance != 0) {
			for (int i = 0; i < groundDistance; i++) {
				super.actions.add(new Fly());
			}
		}
		super.phase = DronePhase.SCANISLAND;
		return this;
	}
}
