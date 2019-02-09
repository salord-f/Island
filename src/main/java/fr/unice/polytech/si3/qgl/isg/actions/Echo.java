package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;

/**
 * Echo action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Echo extends Action {

	private int range;
	private Direction direction;
	private String found;

	public Echo(Direction direction) {
		super(ActionName.ECHO);
		this.direction = direction;
		this.found = "";
	}

	/**
	 * returns in which direction the drone
	 * was facing when doing this action
	 */
	public Direction getDirection() {
		return this.direction;
	}

	public int getRange() {
		return this.range;
	}

	public String getFound() {
		return this.found;
	}

	/**
	 * @return true if we found some ground
	 * with the echo
	 */
	public boolean groundFound() {
		return this.found.equals("GROUND");
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Echo : direction " + direction);
		return new JSONObject().put("action", "echo")
				.put("parameters", new JSONObject().put("direction", this.direction.getAcronym()));
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content
	 */
	void parseResults(JSONObject results) {
		this.range = results.getJSONObject("extras").getInt("range");
		this.found = results.getJSONObject("extras").getString("found");
		logger.info("Results Echo : found " + found + ", distance : " + range);
	}
}
