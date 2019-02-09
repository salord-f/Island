package fr.unice.polytech.si3.qgl.isg;

import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import fr.unice.polytech.si3.qgl.isg.resources.Type;

public class Goal {
	private int amount;
	private Type type;
	private Object requirement;
	private boolean completed;
	private boolean impossible;
	private int theoreticalBudget;

	public Goal(Manufactured manufactured, int amount) {
		this.amount = amount;
		this.requirement = manufactured;
		this.type = Type.MANUFACTURED;
		this.completed = false;
		this.theoreticalBudget = initTheoreticalBudget(manufactured, amount);
		this.impossible = false;
	}

	public Goal(Resource resource, int amount) {
		this.amount = amount;
		this.requirement = resource;
		this.type = Type.PRIMARY;
		this.completed = false;
		this.theoreticalBudget = initTheoreticalBudget(resource, amount);
		this.impossible = false;
	}

	public int getAmount() {
		return amount;
	}

	public Type getType() {
		return type;
	}

	public Object getRequirement() {
		return requirement;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Goal : " + requirement.toString() + ", amount : " + amount;
	}

	public int getTheoreticalBudget() {
		return theoreticalBudget;
	}

	public boolean isImpossible() {
		return impossible;
	}

	public void setImpossible(boolean impossible) {
		this.impossible = impossible;
	}

	private int initTheoreticalBudget(Resource resource, int amount) {
		return (int) (Math.round(amount * resource.getBudgetEstimation()));
	}

	private int initTheoreticalBudget(Manufactured manufactured, int amount) {
		return (int) (Math.round(amount * manufactured.getBudgetEstimation()));
	}

	public boolean isValid(int amount) {
		return amount >= getAmount() && !isCompleted() && !isImpossible();
	}

}
