package fr.unice.polytech.si3.qgl.isg.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.EmptyStackException;

/**
 * Action superclass
 * Each available action inherits from this class
 *
 * @author Florian
 * @version 03/04/2018
 */
public abstract class Action {
	protected Logger logger = LogManager.getLogger("Explorer");
	private ActionName name;
	private int cost;

	protected Action(ActionName name) {
		this.name = name;
		this.cost = 0;
	}

	public ActionName getName() {
		return this.name;
	}

	public int getCost() {
		return this.cost;
	}

	/**
	 * Checks if the object has the same name
	 */
	public boolean is(ActionName name) {
		return this.name == name;
	}

	/**
	 * Fills the class cost field
	 * and calls the parseResults method
	 * which fills the subclass fields
	 */
	public void saveResults(JSONObject results) {
		if (results.has("cost")) {
			try {
				this.cost = results.getInt("cost");
			} catch (Exception e) {
				logger.debug(e);
			}
		} else throw new EmptyStackException();

		parseResults(results);
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content.
	 * Implementation in subclasses
	 */
	abstract void parseResults(JSONObject action);

	/**
	 * Parses the action in json
	 * Implementation in subclasses
	 */
	public abstract JSONObject run();
}
