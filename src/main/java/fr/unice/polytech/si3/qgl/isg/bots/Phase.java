package fr.unice.polytech.si3.qgl.isg.bots;

import fr.unice.polytech.si3.qgl.isg.bots.drone.Drone;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.Sailors;

/**
 * Interface used to have the bot superclass and avoid code duplication.
 * Used for the different enums for the Sailors and Drone phases.
 */
public interface Phase {

	ActionAndPhase run(Drone drone);

	ActionAndPhase run(Sailors sailors);

	String toString();
}
