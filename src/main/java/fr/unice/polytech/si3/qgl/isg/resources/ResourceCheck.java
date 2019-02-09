package fr.unice.polytech.si3.qgl.isg.resources;

import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class ResourceCheck {
	private Map<Resource, Integer> resourcesNeeded;

	public ResourceCheck() {
		this.resourcesNeeded = new EnumMap<>(Resource.class);
	}

	public void setResourcesNeeded(Map<Resource, Integer> resourcesNeeded) {
		this.resourcesNeeded = new EnumMap<>(resourcesNeeded);
	}

	/**
	 * Return true if the drone discovered enough biomes for the resources needed
	 */
	public boolean checkResourcesAmount(CellMap cellMap) {
		return resourcesNeeded.entrySet().stream().noneMatch(entry -> entry.getValue() > cellMap.estimateAndGetResources().get(entry.getKey()));
	}

	public void logResourceCheck(Logger log) {
		log.info("");
		log.info("Resources needed : ");
		Arrays.stream(Resource.values())
				.filter(resource -> resourcesNeeded.get(resource) != null)
				.map(resource -> resource.name() + " : " + resourcesNeeded.get(resource))
				.forEach(log::info);
	}
}