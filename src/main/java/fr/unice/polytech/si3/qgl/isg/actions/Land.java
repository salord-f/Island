package fr.unice.polytech.si3.qgl.isg.actions;

import org.json.JSONObject;

/**
 * Land action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Land extends Action {
	private String creek;
	private int sailors;

	public Land(String creekId, int sailorNumber) {
		super(ActionName.LAND);
		this.creek = creekId;
		this.sailors = sailorNumber;
		if (this.sailors < 0) {
			throw new IllegalArgumentException("Negative sailor number");
		}
	}

	public String getCreek() {
		return this.creek;
	}

	public int getSailors() {
		return this.sailors;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Land : creek " + creek + " with " + sailors + " sailors");
		JSONObject jsonObject = new JSONObject().put("action", "land");
		JSONObject parameters = new JSONObject();
		parameters.put("creek", this.creek);
		parameters.put("people", this.sailors);
		jsonObject.put("parameters", parameters);
		return jsonObject;
	}

	void parseResults(JSONObject results) {
		//Method is empty as no result is expected.
	}
}