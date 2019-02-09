package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;

/**
 * Transform action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Transform extends Action {
	private Map<Resource, Integer> resourcesList;
	private Map<Manufactured, Integer> productsList;
	private int wantedAmount;

	public Transform(Manufactured manufactured, int wantedAmount) {
		super(ActionName.TRANSFORM);
		this.resourcesList = new EnumMap<>(Resource.class);
		this.wantedAmount = wantedAmount;
		manufactured.getRequirements().forEach((key, value) -> resourcesList.put(key, manufactured.getRealResourcesAmount(value, wantedAmount)));
		this.productsList = new EnumMap<>(Manufactured.class);
	}

	public int getWantedAmount() {
		return wantedAmount;
	}

	public int getTransformedAmount() {
		try {
			return (int) this.productsList.values().toArray()[0];
		} catch (Exception e) {
			return 0;
		}
	}

	public Map<Manufactured, Integer> getProductsList() {
		return productsList;
	}

	public Map<Resource, Integer> getResourcesList() {
		return resourcesList;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Transform : resources " + resourcesList);
		JSONObject jsonObject = new JSONObject().put("action", "transform").put("parameters", new JSONObject());
		resourcesList.forEach((resource, integer) -> jsonObject.getJSONObject("parameters").put(resource.name(), integer));
		return jsonObject;
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content
	 */
	void parseResults(JSONObject results) {
		this.productsList.put(Manufactured.valueOf(results.getJSONObject("extras").getString("kind")), results.getJSONObject("extras").getInt("production"));
		this.productsList.forEach((manufactured, amount) -> logger.info("Results Transform : " + manufactured + ": " + amount));
	}
}
