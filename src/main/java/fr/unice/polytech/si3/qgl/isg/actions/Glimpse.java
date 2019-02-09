package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Glimpse action
 *
 * @author Florian
 * @version 03/04/18
 */
public class Glimpse extends Action {
	private Direction direction;
	private int range;
	private Map<Integer, Map<Biome, Double>> biomes;

	public Glimpse(Direction direction, int range) {
		super(ActionName.GLIMPSE);
		this.direction = direction;
		if (range > 0 && range <= 4) {
			this.range = range;
		} else throw new IllegalArgumentException("Wrong glimpse range");
		this.biomes = new HashMap<>();
	}

	public Direction getDirection() {
		return this.direction;
	}

	public int getRange() {
		return this.range;
	}

	public Map<Integer, Map<Biome, Double>> getBiomes() {
		return this.biomes;
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Glimpse : direction " + direction + ", range " + range);
		JSONObject jsonObject = new JSONObject().put("action", "glimpse");
		JSONObject parameters = new JSONObject();
		parameters.put("direction", this.direction.getAcronym());
		parameters.put("range", this.range);
		jsonObject.put("parameters", parameters);
		return jsonObject;
	}

	/**
	 * Fills the class fields
	 * with the JSONObject content
	 * from the furthest cell to the closest
	 */
	@SuppressWarnings("fallthrough")
	void parseResults(JSONObject results) {
		logger.info("Results Glimpse : ");
		JSONArray reportArray = results.getJSONObject("extras").getJSONArray("report");

		//No break is required. The cells are analyzed from furthest to closest.
		switch (range) {
			case 4:
				Map<Biome, Double> range4 = new EnumMap<>(Biome.class);
				range4.put(Biome.valueOf(reportArray.getJSONArray(3).getString(0)), -1.0);
				biomes.put(4, range4);

			case 3:
				Map<Biome, Double> range3 = new EnumMap<>(Biome.class);
				for (int i = 0; i < reportArray.getJSONArray(2).length(); i++) {
					range3.put(Biome.valueOf(reportArray.getJSONArray(2).getString(i)), -1.0);
				}
				biomes.put(3, range3);

			case 2:
				Map<Biome, Double> range2 = new EnumMap<>(Biome.class);
				JSONArray range2Array = reportArray.getJSONArray(1);
				for (int i = 0; i < range2Array.length(); i++) {
					range2.put(Biome.valueOf(range2Array.getJSONArray(i).getString(0)), range2Array.getJSONArray(i).getDouble(1));
				}
				biomes.put(2, range2);

			case 1:
				Map<Biome, Double> range1 = new EnumMap<>(Biome.class);
				JSONArray range1Array = reportArray.getJSONArray(0);
				for (int i = 0; i < range1Array.length(); i++) {
					range1.put(Biome.valueOf(range1Array.getJSONArray(i).getString(0)), range1Array.getJSONArray(i).getDouble(1));
				}
				biomes.put(1, range1);
				break;

			default:
				logger.info("Error in Glimpse results");
		}
		biomes.forEach((i, m) -> m.forEach((biome, integer) -> logger.info("Distance " + i + "  : " + biome.name() + ", " + integer + "%")));
	}
}
