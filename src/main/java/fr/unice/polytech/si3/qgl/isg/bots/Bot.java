package fr.unice.polytech.si3.qgl.isg.bots;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Bot superclass used to create both the Drone and the Sailors
 */
public abstract class Bot {

	protected static Logger logger = LogManager.getLogger("Explorer"); // Used to avoid importing it in the different subclasses
	protected ActionSequence sequence;
	protected CellMap cellMap;
	protected Position position;
	protected Action lastAction;
	protected Phase phase;

	protected Bot(ActionSequence sequence, CellMap cellMap) {
		this.sequence = sequence;
		this.cellMap = cellMap;
		this.position = new Position(0, 0);
	}

	public ActionSequence getActionSequence() {
		return sequence;
	}

	public CellMap getCellMap() {
		return cellMap;
	}

	public Action getLastAction() {
		return lastAction;
	}

	protected void setLastAction(Action lastAction) {
		this.lastAction = lastAction;
	}

	public Phase getPhase() {
		return this.phase;
	}

	public void setPhase(Phase phase) {
		if (this.phase != phase) {
			logger.info("Old phase : " + this.phase + ". New phase : " + phase);
		}
		this.phase = phase;
	}

	public abstract void update();

	public Position getPosition() {
		return position;
	}
}
