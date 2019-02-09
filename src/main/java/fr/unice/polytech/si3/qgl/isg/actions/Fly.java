package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;

/**
 * Fly action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Fly extends Action {

	public Fly() {
		super(ActionName.FLY);
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Fly");
		return new JSONObject().put("action", "fly");
	}

	public void parseResults(JSONObject results) {
		//Method is empty as no result is expected.
	}
}