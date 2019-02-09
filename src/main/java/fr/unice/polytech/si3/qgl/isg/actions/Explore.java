package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.resources.IslandResource;
import fr.unice.polytech.si3.qgl.isg.resources.IslandResource.Amount;
import fr.unice.polytech.si3.qgl.isg.resources.IslandResource.Condition;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Explore action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Explore extends Action {
	private List<IslandResource> resources;
	private List<String> creeks;
	private List<String> sites;

	public Explore() {
		super(ActionName.EXPLORE);
		this.resources = new ArrayList<>();
		this.creeks = new ArrayList<>();
		this.sites = new ArrayList<>();
	}

	public List<IslandResource> getResources() {
		return resources;
	}

	public List<String> getCreeks() {
		return creeks;
	}

	public List<String> getSites() {
		return sites;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Explore");
		return new JSONObject().put("action", "explore");
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content
	 */
	void parseResults(JSONObject results) {
		JSONArray resourcesArray = results.getJSONObject("extras").getJSONArray("resources");
		for (int i = 0; i < resourcesArray.length(); i++) {
			JSONObject currentObject = resourcesArray.getJSONObject(i);
			Amount amount = Amount.valueOf(currentObject.getString("amount"));
			Condition condition = Condition.valueOf(currentObject.getString("cond"));
			Resource name = Resource.valueOf(currentObject.getString("resource"));
			resources.add(new IslandResource(amount, condition, name));
		}

		JSONArray poisArray = results.getJSONObject("extras").getJSONArray("pois");
		for (int i = 0; i < poisArray.length(); i++) {
			String kind = poisArray.getJSONObject(i).getString("kind");
			if (kind.equals("Creek")) {
				creeks.add(poisArray.getJSONObject(i).getString("id"));
			} else if (kind.equals("Site")) {
				sites.add(poisArray.getJSONObject(i).getString("id"));
			}
		}
		logResults();
	}

	private void logResults() {
		if (resources.isEmpty() && creeks.isEmpty() && sites.isEmpty()) {
			logger.info("Results Explore : Nothing found");
		} else {
			logger.info("Results Explore :");
			resources.forEach(r -> logger.info("Resource(s) " + r));
			creeks.forEach(c -> logger.info("Creek(s) : " + c));
			sites.forEach(s -> logger.info("Site(s) : " + s));
		}
	}

	/**
	 * @return true if the exploration was made over Ocean or Lake biomes
	 */
	public boolean overOcean() {
		return !resources.isEmpty()
				&& resources.get(0).getResource() == Resource.FISH
				&& resources.size() == 1;
	}
}
