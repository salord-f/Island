package fr.unice.polytech.si3.qgl.isg.actions;

import fr.unice.polytech.si3.qgl.isg.map.Biome;
import fr.unice.polytech.si3.qgl.isg.map.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Scan action
 *
 * @author Florian
 * @version 03/04/2018
 */
public class Scan extends Action {
	private Position position;
	private Set<Biome> biomeList;
	private List<String> creeks;
	private String site;

	public Scan(Position position) {
		super(ActionName.SCAN);
		this.position = new Position(position.getX(), position.getY());
		this.biomeList = new HashSet<>();
		this.creeks = new ArrayList<>();
		this.site = "";
	}

	public Set<Biome> getBiomeList() {
		return this.biomeList;
	}

	public List<String> getCreeks() {
		return this.creeks;
	}

	public String getSite() {
		return this.site;
	}

	public void setPosition(Position position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
	}

	/**
	 * Parses the action in json
	 */
	public JSONObject run() {
		logger.info("Action Scan");
		return new JSONObject().put("action", "scan");
	}

	/**
	 * Fills a list with the biomes present
	 * on the scanned cell, changes the booleans
	 * creeks and/or site if there is one present on the cell.
	 */
	public void parseResults(JSONObject results) {
		logger.info("Results Scan :");
		JSONObject extras = results.getJSONObject("extras");

		JSONArray biomeArray = extras.getJSONArray("biomes");
		for (int i = 0; i < biomeArray.length(); i++) {
			this.biomeList.add(Biome.valueOf(biomeArray.getString(i)));
		}

		JSONArray creekArray = extras.getJSONArray("creeks");
		for (int i = 0; i < creekArray.length(); i++) {
			this.creeks.add(creekArray.getString(i));
		}

		JSONArray siteArray = extras.getJSONArray("sites");
		if (siteArray.length() != 0) {
			this.site = siteArray.getString(0);
		}

		logResults();
	}

	private void logResults() {
		biomeList.forEach(b -> logger.info("Biomes : " + b));
		creeks.forEach(c -> logger.info("Creek : " + c));
		if (!site.equals("")) {
			logger.info("Site : " + site);
		}
	}

	/**
	 * Determinate if the drone is above water.
	 *
	 * @return true the drone is above, otherwise false.
	 */
	public boolean overOcean() {
		return this.biomeList.contains(Biome.OCEAN) && this.biomeList.size() == 1;
	}
}
