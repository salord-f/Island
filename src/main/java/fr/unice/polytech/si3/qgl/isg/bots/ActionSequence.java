package fr.unice.polytech.si3.qgl.isg.bots;

import fr.unice.polytech.si3.qgl.isg.actions.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.unice.polytech.si3.qgl.isg.Explorer.logger;

/**
 * Sequence of actions used as a buffer
 * to add different actions without having to add
 * them one by one at each game turn.
 */
public class ActionSequence {
	private List<Action> sequence;
	private int currentAction;

	public ActionSequence() {
		this.sequence = new ArrayList<>();
		this.currentAction = 0;
	}

	public List<Action> getSequence() {
		return sequence;
	}

	/**
	 * Adds actions to the sequence.
	 */
	public void add(Action... actions) {
		sequence.addAll(Arrays.asList(actions));
	}

	/**
	 * Increments the currentAction counter
	 * and returns the next action in buffer.
	 */
	public Action getNextAction() {
		Action nextAction = sequence.get(currentAction);
		this.currentAction++;
		return nextAction;
	}

	/**
	 * Returns the last action.
	 */
	public Action getLastAction() {
		return getPreviousAction(1);
	}

	/**
	 * Returns an old action. If turnsAhead is 0, it's the current, 1 is the last, etc.
	 */
	public Action getPreviousAction(int turnsAhead) {
		return this.sequence.get(currentAction - turnsAhead);
	}

	/**
	 * Saves the json results inside the last action object.
	 */
	public void saveResults(JSONObject results) {
		if (!getLastAction().is(ActionName.STOP)) {
			getLastAction().saveResults(results);
		}
	}

	/**
	 * Deletes all actions and adds a stop.
	 */
	public void emergencyStop() {
		this.sequence.clear();
		this.currentAction = 0;
		this.sequence.add(new Stop());
	}

	/**
	 * @return true if there are actions in the buffer
	 */
	public boolean hasBuffer() {
		return sequence.size() > currentAction;
	}

	/**
	 * Compares the range of the two echos
	 *
	 * @return true if the drone have to make a right diagonal
	 */
	public boolean goingRight() {
		try {
			return ((Echo) getPreviousAction(3)).getRange() < ((Echo) getPreviousAction(2)).getRange();
		} catch (IndexOutOfBoundsException e) {
			logger.info("Error while computing goingRight : " + e);
			return false;
		}
	}

	/**
	 * Determinate if the drone already made a turn.
	 */
	public boolean shouldTurn() {
		try {
			return getPreviousAction(4).is(ActionName.HEADING)
					&& !getPreviousAction(6).is(ActionName.HEADING);
		} catch (IndexOutOfBoundsException e) {
			logger.info("Error while computing shouldTurn : " + e);
			return false;
		}
	}

	/**
	 * @return true if the last scan is over ocean
	 */
	public boolean lastScanOverOcean() {
		try {
			return getLastAction().is(ActionName.SCAN) && ((Scan) getLastAction()).overOcean();
		} catch (IndexOutOfBoundsException e) {
			logger.info("Error while computing lastScanOverOcean : " + e);
			return false;
		}
	}

	public boolean ozIsland() {
		try {
			Echo echo1 = (Echo) getPreviousAction(3);
			Echo echo2 = (Echo) getPreviousAction(2);
			Echo echo3 = (Echo) getPreviousAction(1);
			return echo1.groundFound() && echo2.groundFound() && echo3.groundFound();
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
}
