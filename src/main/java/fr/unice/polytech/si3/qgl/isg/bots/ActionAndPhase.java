package fr.unice.polytech.si3.qgl.isg.bots;

import fr.unice.polytech.si3.qgl.isg.actions.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Action and Phase object used by the different
 * phases to send actions to the decision making method
 * and/or change the current phase.
 */
public abstract class ActionAndPhase {
	protected List<Action> actions;
	protected Phase phase;

	public ActionAndPhase(Phase phase) {
		this.actions = new ArrayList<>();
		this.phase = phase;
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
