package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Biome {
	OCEAN,
	LAKE,
	BEACH,
	GRASSLAND,
	MANGROVE,
	TROPICAL_RAIN_FOREST,
	TROPICAL_SEASONAL_FOREST,
	TEMPERATE_DECIDUOUS_FOREST,
	TEMPERATE_RAIN_FOREST,
	TEMPERATE_DESERT,
	TAIGA,
	SNOW,
	TUNDRA,
	ALPINE,
	GLACIER,
	SHRUBLAND,
	SUB_TROPICAL_DESERT;

	/**
	 * Return all the resources found in a given biome.
	 */
	public Set<Resource> concernedResources() {
		return Arrays.stream(Resource.values())
				.filter(resource -> resource.getConcernedBiomes().contains(this))
				.collect(Collectors.toSet());
	}
}
