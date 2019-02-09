package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;

/**
 * Stop action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Stop extends Action {

	public Stop() {
		super(ActionName.STOP);
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Stop");
		return new JSONObject().put("action", "stop");
	}

	void parseResults(JSONObject results) {
		//Method is empty as no result is expected.
	}
}
