package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.actions.Stop;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;

public class SearchDirection extends ActionAndPhase {
	private Position position;
	private Direction heading;

	public SearchDirection(Position position, Direction heading) {
		super(DronePhase.SEARCHDIRECTION);
		this.position = position;
		this.heading = heading;
	}

	/**
	 * Chooses the direction with which the drone will search the ground.
	 * firstDirection is the opposite of the out of range 0 echo, most of the time the current drone heading.
	 *
	 * @param goingRight      set the secondDirection : it is the longest range found right or left of firstDirection.
	 * @param groundDirection if it is not null, a ground was found during the three echos. So firstDirection is equal to groundDirection
	 */
	public ActionAndPhase run(boolean goingRight, Direction groundDirection, boolean ozIsland) {
		if (ozIsland) {
			super.actions.add(new Stop());
			return this;
		}
		setSecondDirection(goingRight);
		if (groundDirection == null) {
			position.setFirstDirection(heading);
			super.actions.add(new Heading(position.getSecondDirection()));
			super.actions.add(new Echo(position.getSecondDirection()));
			super.phase = DronePhase.SEARCHISLAND;
		} else super.phase = DronePhase.FLYTO;
		return this;
	}

	/**
	 * Sets the second direction on the largest range side.
	 */
	private void setSecondDirection(boolean goingRight) {
		position.setSecondDirection(goingRight ? heading.getRight() : heading.getLeft());
	}
}

