package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Scout action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Scout extends Action {
	private int altitude;
	private Direction direction;
	private Set<Resource> resources;

	public Scout(Direction direction) {
		super(ActionName.SCOUT);
		this.altitude = 0;
		this.direction = direction;
		this.resources = new HashSet<>();
	}

	public int getAltitude() {
		return this.altitude;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public Set<Resource> getResources() {
		return this.resources;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Scout : direction " + direction);
		return new JSONObject().put("action", "scout")
				.put("parameters", new JSONObject().put("direction", this.direction.getAcronym()));
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content
	 */
	void parseResults(JSONObject results) {
		this.altitude = results.getJSONObject("extras").getInt("altitude");
		JSONArray jsonArray = results.getJSONObject("extras").getJSONArray("resources");
		for (int i = 0; i < jsonArray.length(); i++) {
			resources.add(Resource.valueOf(jsonArray.getString(i)));
		}
		logger.info("Results Scout : altitude " + altitude + ", resources : " + resources);
	}

	public boolean containsResource(Resource resource) {
		return resources.stream().anyMatch(r -> r == resource);
	}

	/**
	 * @return true if the exploration was made over Ocean or Lake biomes
	 */
	public boolean overOcean() {
		return resources.size() == 1 && resources.contains(Resource.FISH);
	}
}
