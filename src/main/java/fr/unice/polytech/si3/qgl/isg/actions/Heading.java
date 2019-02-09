package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;

/**
 * Heading action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Heading extends Action {
	private Direction direction;

	public Heading(Direction direction) {
		super(ActionName.HEADING);
		this.direction = direction;
	}

	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Heading : direction " + direction);
		return new JSONObject().put("action", "heading")
				.put("parameters", new JSONObject().put("direction", direction.getAcronym()));
	}

	public void parseResults(JSONObject results) {
		//Method is empty as no result is expected.
	}
}
