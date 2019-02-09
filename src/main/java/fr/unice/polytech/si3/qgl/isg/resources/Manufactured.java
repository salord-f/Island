package fr.unice.polytech.si3.qgl.isg.resources;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public enum Manufactured {
	GLASS(1) {
		protected void fillRequirements() {
			this.requirements.put(Resource.QUARTZ, 10);
			this.requirements.put(Resource.WOOD, 5);
		}
	},
	INGOT(1) {
		protected void fillRequirements() {
			this.requirements.put(Resource.ORE, 10);
			this.requirements.put(Resource.WOOD, 5);
		}
	},
	LEATHER(1) {
		protected void fillRequirements() {
			this.requirements.put(Resource.FUR, 3);
		}
	},
	PLANK(4) {
		protected void fillRequirements() {
			this.requirements.put(Resource.WOOD, 1);
		}
	},
	RUM(1) {
		protected void fillRequirements() {
			this.requirements.put(Resource.SUGAR_CANE, 10);
			this.requirements.put(Resource.FRUITS, 1);
		}
	};

	protected Map<Resource, Integer> requirements;
	private Type type;
	private int amountCreated;
	private double lostCoefficient;
	private double budgetEstimation;

	Manufactured(int amountCreated) {
		this.requirements = new EnumMap<>(Resource.class);
		this.type = Type.MANUFACTURED;
		this.amountCreated = amountCreated;
		this.lostCoefficient = 1.1;
		this.budgetEstimation = 1.5;
		this.fillRequirements();
	}

	public static Manufactured fromString(String text) {
		return Arrays.stream(values())
				.filter(b -> b.name().equalsIgnoreCase(text))
				.findFirst().orElse(null);
	}

	protected abstract void fillRequirements();

	public Map<Resource, Integer> getRequirements() {
		return requirements;
	}

	public Type getType() {
		return type;
	}

	public int getAmountCreated() {
		return amountCreated;
	}

	/**
	 * The amount of a resource needed for a manufactured amount, rounded up.
	 */
	public int getRealResourcesAmount(int resourceAmount, int manufacturedAmount) {
		return (int) Math.ceil((double) (resourceAmount * manufacturedAmount) / amountCreated);
	}

	/**
	 * The amount with the lost coefficient of craft
	 *
	 * @param manufacturedAmount the real goal amount
	 */
	public int getRealManufacturedAmount(int manufacturedAmount) {
		return (int) Math.ceil(manufacturedAmount * lostCoefficient);
	}

	public double getBudgetEstimation() {
		return budgetEstimation;
	}
}
