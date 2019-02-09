package fr.unice.polytech.si3.qgl.isg.resources;

public class IslandResource {
	private Amount amount;
	private Condition condition;
	private Resource resource;

	public IslandResource(Amount amount, Condition condition, Resource resource) {
		this.amount = amount;
		this.condition = condition;
		this.resource = resource;
	}

	public Amount getAmount() {
		return amount;
	}

	public Condition getCondition() {
		return condition;
	}

	public Resource getResource() {
		return resource;
	}

	@Override
	public String toString() {
		return "Resource: " + resource.name() + ", amount: " + amount + ", condition: " + condition;
	}

	public boolean validConditions() {
		return amount != Amount.LOW && condition != Condition.HARSH;
	}

	public enum Amount {
		LOW,
		MEDIUM,
		HIGH
	}

	public enum Condition {
		EASY,
		FAIR,
		HARSH
	}
}
