package fr.unice.polytech.si3.qgl.isg.bots.drone;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.actions.Scan;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.bots.Bot;
import fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases.DronePhase;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.map.Direction;

import java.util.concurrent.atomic.AtomicBoolean;

public class Drone extends Bot {

	private Direction groundDirection;
	private int groundDistance;
	private AtomicBoolean oneWay;
	private Direction heading;
	private Direction oldHeading;


	public Drone(ActionSequence sequence, CellMap cellMap) {
		super(sequence, cellMap);
		this.phase = DronePhase.INITIALECHO;
		this.groundDistance = 0;
		this.oneWay = new AtomicBoolean(true);
		this.heading = null;
		this.oldHeading = null;
	}

	public Direction getGroundDirection() {
		return groundDirection;
	}

	public int getGroundDistance() {
		return groundDistance;
	}

	public AtomicBoolean isOneWay() {
		return oneWay;
	}

	public Direction getHeading() {
		return heading;
	}

	/**
	 * Save the current direction in oldHeading
	 * and update it with the parameter
	 */
	public void setHeading(Direction direction) {
		if (oldHeading == null) {
			heading = direction;
		}
		this.oldHeading = this.heading;
		this.heading = direction;
	}

	public Direction getOldHeading() {
		return oldHeading;
	}

	private void updatePosition(Direction direction) {
		position.updatePosition(direction, this.heading);
	}

	/**
	 * Run the phase to take a decision
	 *
	 * @return true if the sailors have to land
	 */
	public boolean run() {
		while (!sequence.hasBuffer()) {
			ActionAndPhase actionAndPhase = super.phase.run(this);
			sequence.add(actionAndPhase.getActions().toArray(new Action[0]));
			setPhase(actionAndPhase.getPhase());
			if (this.phase == DronePhase.LANDPHASE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * After an action, update the drone fields. If the last action is :
	 * - Fly : update the position
	 * - Heading : update the position and the heading
	 * - Echo : update the direction and the distance of the ground
	 * - Scan : update the map
	 */
	public void update() {
		if (!sequence.getSequence().isEmpty()) {
			setLastAction(sequence.getLastAction());

			switch (lastAction.getName()) {
				case FLY:
					updatePosition(heading);
					break;

				case HEADING:
					updatePosition(((Heading) lastAction).getDirection());
					setHeading(((Heading) lastAction).getDirection());
					break;

				case ECHO:
					Echo echo = (Echo) lastAction;
					if (echo.groundFound()) {
						if (groundDirection == null && position.getFirstDirection() != echo.getDirection()) {
							position.setSecondDirection(position.getFirstDirection());
							position.setFirstDirection(echo.getDirection());
						}
						groundDirection = echo.getDirection();
						groundDistance = echo.getRange();
					}
					break;

				case SCAN:
					Scan scan = (Scan) lastAction;
					scan.setPosition(position);
					cellMap.addCell(position.getX(), position.getY(), scan.getBiomeList(), scan.getCreeks(), scan.getSite());
					cellMap.fillMap(true);
					break;

				default:
					logger.info("Last action is unknown");
			}
			setHeading(heading);
			logger.info("Drone position : " + position);
		}
	}
}