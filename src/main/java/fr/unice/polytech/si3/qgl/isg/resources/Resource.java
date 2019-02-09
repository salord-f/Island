package fr.unice.polytech.si3.qgl.isg.resources;


import fr.unice.polytech.si3.qgl.isg.map.Biome;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * According to the biome, resources won't be in the same amount. As a result we have a coefficient for a given resource and a given biome.
 * The coefficient has been calculated by scanning all the biomes on all the maps and matching it to the total amount of resources on the given map.
 * By doing so, we extracted an average. To be sure the drone won't stop too early we divide the average resource by 2 to get empiric value.
 * Sometimes, it's not enough or it's too much so we have adjust the value manually.
 * this.concernedBiomes.put(Biome, averageAmount +- manual adjustment)
 */
public enum Resource {
	FISH(1.05) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.OCEAN, 291.00);
			this.concernedBiomes.put(Biome.LAKE, 291.00);
		}
	},
	FLOWER(32) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.ALPINE, 2.0);
			this.concernedBiomes.put(Biome.GLACIER, 2.0);
			this.concernedBiomes.put(Biome.MANGROVE, 2.125);//2.0
		}
	},
	FRUITS(6.44) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.TROPICAL_RAIN_FOREST, 20.0);
			this.concernedBiomes.put(Biome.TROPICAL_SEASONAL_FOREST, 12.0);
		}
	},
	FUR(0.518) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.TEMPERATE_RAIN_FOREST, 52.0);
			this.concernedBiomes.put(Biome.GRASSLAND, 52.0);
			this.concernedBiomes.put(Biome.TUNDRA, 52.0);
			this.concernedBiomes.put(Biome.SHRUBLAND, 52.0);
		}
	},
	ORE(0) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.ALPINE, 10.0);
			this.concernedBiomes.put(Biome.TEMPERATE_DESERT, 10.0);
			this.concernedBiomes.put(Biome.SUB_TROPICAL_DESERT, 10.0);
		}
	},
	QUARTZ(0) {
		protected void fillBiomes() {//10.8 - 1.5
			this.concernedBiomes.put(Biome.BEACH, 10.8 - 5.5); //Manual adjustment5.5
			this.concernedBiomes.put(Biome.TEMPERATE_DESERT, 10.8 - 5.5);
			this.concernedBiomes.put(Biome.SUB_TROPICAL_DESERT, 10.8 - 5.5);
		}
	},
	SUGAR_CANE(15) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.TROPICAL_SEASONAL_FOREST, 80.0);
			this.concernedBiomes.put(Biome.TROPICAL_RAIN_FOREST, 90.0);
		}
	},
	WOOD(1.16) {
		protected void fillBiomes() {
			this.concernedBiomes.put(Biome.MANGROVE, 230.0 + 10);
			this.concernedBiomes.put(Biome.TROPICAL_RAIN_FOREST, 140.0 + 40);
			this.concernedBiomes.put(Biome.TROPICAL_SEASONAL_FOREST, 135.0 + 40);
			this.concernedBiomes.put(Biome.TEMPERATE_DECIDUOUS_FOREST, 366.0);
			this.concernedBiomes.put(Biome.TAIGA, 200.0);
		}
	};

	protected Map<Biome, Double> concernedBiomes;
	private Type type;
	private double budgetEstimation; // Average of budget needed to exploit one of these resources.

	Resource(double budgetEstimation) {
		this.type = Type.PRIMARY;
		this.budgetEstimation = budgetEstimation;
		this.concernedBiomes = new EnumMap<>(Biome.class);
		fillBiomes();
	}

	public static Resource fromString(String text) {
		return Arrays.stream(values())
				.filter(b -> b.name().equalsIgnoreCase(text))
				.findFirst().orElse(null);
	}

	protected abstract void fillBiomes();

	public Type getType() {
		return type;
	}

	public Set<Biome> getConcernedBiomes() {
		return concernedBiomes.keySet();
	}

	public double getBudgetEstimation() {
		return budgetEstimation;
	}

	/**
	 * All the values are divided by 2 to have empiric values.
	 */
	public double getCellAmountEstimation(Biome biome) {
		return this.concernedBiomes.get(biome) / 2.0;
	}
}
