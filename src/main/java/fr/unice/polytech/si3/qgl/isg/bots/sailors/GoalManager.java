package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.Goal;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import fr.unice.polytech.si3.qgl.isg.resources.Type;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoalManager {
	private List<Goal> goals;
	private Contract contract;
	private int budget;

	public GoalManager(Contract contract) {
		this.contract = contract;
	}

	public void initialize(int budget) {
		this.budget = budget;
		this.goals = new ArrayList<>(contract.getGoals());
		budgetCheck();
	}

	public List<Goal> getGoals() {
		return goals;
	}

	/**
	 * @return all completed goals
	 */
	public List<Goal> getCompletedGoals() {
		return goals.stream()
				.filter(Goal::isCompleted)
				.collect(Collectors.toList());
	}

	/**
	 * @return all impossible goals
	 */
	public List<Goal> getImpossibleGoals() {
		return goals.stream()
				.filter(Goal::isImpossible)
				.collect(Collectors.toList());
	}

	/**
	 * @return all uncompleted goals
	 */
	public List<Goal> getUncompletedGoals() {
		return goals.stream()
				.filter(goal -> !goal.isCompleted() && !goal.isImpossible())
				.collect(Collectors.toList());
	}

	/**
	 * Validate all the goals that have fulfilled their requirements
	 */
	public void checkGoals(Map<Resource, Integer> resourcesInventory, Map<Manufactured, Integer> manufacturedInventory) {
		goals.forEach(goal -> {
			if (goal.getType() == Type.MANUFACTURED) {
				Manufactured manufactured = (Manufactured) goal.getRequirement();
				if (goal.isValid(manufacturedInventory.get(manufactured))) {
					goal.setCompleted(true);
				}
			} else {
				Resource resource = (Resource) goal.getRequirement();
				if (goal.isValid(resourcesInventory.get(resource))) {
					goal.setCompleted(true);
				} else if (resourcesInventory.get(resource) < goal.getAmount() && goal.isCompleted() && !goal.isImpossible()) {
					// This case can happen when crafting as it can use resources needed for another goal.
					goal.setCompleted(false);
				}
			}
		});
	}

	public boolean noGoalLeft() {
		return getUncompletedGoals().isEmpty();
	}

	/**
	 * Sets goals as impossible if the budget left is not enough to complete it.
	 */
	public void budgetCheck() {
		goals.stream()
				.filter(goal -> goal.getTheoreticalBudget() >= budget)
				.forEach(goal -> goal.setImpossible(true));
	}

	public void logGoalManager(Logger log) {
		log.info("");
		log.info("Goals : " + getCompletedGoals().size() + "/" + contract.getGoals().size());
		if (getUncompletedGoals().isEmpty()) {
			log.info("Uncompleted goals : None");
		} else {
			log.info("Uncompleted goals : ");
			getUncompletedGoals().forEach(goal -> log.info(goal.toString()));
		}

		if (getImpossibleGoals().isEmpty()) {
			log.info("Impossible goals : None");
		} else {
			log.info("Impossible goals : ");
			getImpossibleGoals().forEach(goal -> log.info(goal.toString()));
		}

		if (getCompletedGoals().isEmpty()) {
			log.info("Completed goals : None");
		} else {
			log.info("Completed goals : ");
			getCompletedGoals().forEach(goal -> log.info(goal.toString()));
		}
	}
}
