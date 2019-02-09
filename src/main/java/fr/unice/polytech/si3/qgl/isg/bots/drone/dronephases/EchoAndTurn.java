package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.*;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;

import java.util.concurrent.atomic.AtomicBoolean;

public class EchoAndTurn extends ActionAndPhase {
	private Position position;
	private AtomicBoolean oneWay;
	private boolean noCreek;

	public EchoAndTurn(Position position, AtomicBoolean oneWay, boolean noCreek) {
		super(DronePhase.ECHOANDTURN);
		this.position = position;
		this.oneWay = oneWay;
		this.noCreek = noCreek;
	}

	/**
	 * At the end of a line, the drone checks if they is no ground in front of it.
	 * It then checks if it is on the good height to turn, to miss nothing.
	 */
	public ActionAndPhase run(Action lastAction, Direction heading, Direction oldHeading, boolean shouldTurn) {
		if (lastAction.is(ActionName.ECHO)) {
			Echo echo = (Echo) lastAction;
			if (echo.getDirection() == heading) {
				if (echo.groundFound()) {
					super.phase = DronePhase.FLYTO;
				} else if (shouldTurn) {
					turnAbout();
				} else super.actions.add(new Echo(position.getSecondDirection()));
			} else {
				if (echo.groundFound() && echo.getRange() <= 1) {
					super.actions.add(new Fly());
				} else {
					turn(oldHeading);
				}
			}
		} else if (lastAction.is(ActionName.FLY)) {
			super.actions.add(new Echo(position.getSecondDirection()));
		}
		return this;
	}

	/**
	 * Makes the drone turn around.
	 */
	private void turn(Direction oldHeading) {
		super.actions.add(new Heading(position.getSecondDirection()));
		super.actions.add(new Heading(oldHeading.getReverse()));
		super.phase = DronePhase.SCANISLAND;
	}

	/**
	 * If no creek were found or oneWay is false, the drone makes the turn to scan all the island.
	 */
	private void turnAbout() {
		if (!oneWay.get() || noCreek) {
			oneWay.set(true);
			super.actions.add(new Heading(position.getSecondDirection().getReverse()));
			super.actions.add(new Fly());
			super.actions.add(new Heading(position.getFirstDirection()));
			super.actions.add(new Heading(position.getSecondDirection()));
			super.actions.add(new Heading(position.getFirstDirection().getReverse()));
			position.reverseFirstDirection();
			position.reverseSecondDirection();
			super.phase = DronePhase.SCANISLAND;
		} else super.phase = DronePhase.LANDPHASE;
	}
}