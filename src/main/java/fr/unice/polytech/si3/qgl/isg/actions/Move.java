package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONObject;

/**
 * Heading action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Move extends Action {
	private Direction direction;

	public Move(Direction direction) {
		super(ActionName.MOVE);
		this.direction = direction;
	}

	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Move : direction " + direction);
		return new JSONObject().put("action", "move_to")
				.put("parameters", new JSONObject().put("direction", direction.getAcronym()));
	}

	public void parseResults(JSONObject results) {
		//Method is empty as no result is expected.
	}
}
