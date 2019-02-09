package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Goal;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import fr.unice.polytech.si3.qgl.isg.resources.Type;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
	private Map<Resource, Integer> resourcesInventory;
	private Map<Resource, Integer> resourcesNeeded;
	private Map<Manufactured, Integer> manufacturedInventory;
	private Map<Manufactured, Integer> manufacturedNeeded;

	public InventoryManager() {
		this.resourcesInventory = new EnumMap<>(Resource.class);
		this.resourcesNeeded = new EnumMap<>(Resource.class);
		this.manufacturedInventory = new EnumMap<>(Manufactured.class);
		this.manufacturedNeeded = new EnumMap<>(Manufactured.class);
		Arrays.stream(Resource.values()).forEach(resource -> resourcesInventory.put(resource, 0));
		Arrays.stream(Manufactured.values()).forEach(manufactured -> manufacturedInventory.put(manufactured, 0));
	}

	public Map<Manufactured, Integer> getManufacturedNeeded() {
		return manufacturedNeeded;
	}

	public Map<Resource, Integer> getResourcesNeeded() {
		return resourcesNeeded;
	}

	public boolean isNeeded(Resource resource) {
		return resourcesNeeded.containsKey(resource);
	}

	public Map<Manufactured, Integer> getManufacturedInventory() {
		return manufacturedInventory;
	}

	public Map<Resource, Integer> getResourcesInventory() {
		return resourcesInventory;
	}

	public int getResourceAmount(Resource resource) {
		return resourcesInventory.get(resource);
	}

	public int getManufacturedAmount(Manufactured manufactured) {
		return manufacturedInventory.get(manufactured);
	}

	public void addAmount(Map<Resource, Integer> map, Resource resource, int amount) {
		map.merge(resource, amount, Integer::sum);
	}

	public void addAmount(Map<Manufactured, Integer> map, Manufactured manufactured, int amount) {
		map.merge(manufactured, amount, Integer::sum);
	}

	public void removeAmount(Map<Resource, Integer> map, Resource resource, int amount) {
		addAmount(map, resource, -amount);
	}

	/**
	 * Initialize all resources and manufactured needed at 0
	 * Fill resourcesNeeded and manufacturedNeeded+1 from uncompleted goals
	 * Then compare with the inventory, to subtract with resources needed
	 * At the end delete all negative number
	 */
	public void updateInventoryNeeded(List<Goal> completedGoals, List<Goal> uncompletedGoals) {
		Arrays.stream(Resource.values()).forEach(resource -> resourcesNeeded.put(resource, 0));
		Arrays.stream(Manufactured.values()).forEach(manufactured -> manufacturedNeeded.put(manufactured, 0));

		for (Goal goal : uncompletedGoals) {
			if (goal.getType() == Type.MANUFACTURED) {
				Manufactured manufactured = (Manufactured) goal.getRequirement();
				addAmount(manufacturedNeeded, manufactured, goal.getAmount() - manufacturedInventory.get(manufactured) + 1);
				addManufacturedRequirements(manufactured, manufactured.getRealManufacturedAmount(goal.getAmount() - manufacturedInventory.get(manufactured)));
			} else {
				addAmount(resourcesNeeded, (Resource) goal.getRequirement(), goal.getAmount());
			}
		}

		resourcesInventory.forEach((resource, amount) -> removeAmount(resourcesNeeded, resource, amount));

		for (Goal goal : completedGoals) {
			if (goal.getType() == Type.PRIMARY) {
				addAmount(resourcesNeeded, (Resource) goal.getRequirement(), goal.getAmount());
			}
		}

		resourcesNeeded.entrySet().removeIf(object -> object.getValue() <= 0);
		manufacturedNeeded.entrySet().removeIf(object -> object.getValue() <= 0);
	}

	/**
	 * After a TRANSFORM, update the inventory : add manufactured resources and remove natural resources used for it.
	 */
	public void addManufactured(Map<Manufactured, Integer> manufactured, Map<Resource, Integer> resources) {
		resources.forEach((resource, amount) -> removeAmount(resourcesInventory, resource, amount));
		manufactured.forEach((man, amount) -> addAmount(manufacturedInventory, man, amount));
	}

	/**
	 * After an EXPLOIT, update the inventory : add natural resources in the inventory.
	 */
	public void addResource(Resource resource, int amount) {
		addAmount(resourcesInventory, resource, amount);
	}

	/**
	 * Add manufactured requirements in resourcesNeeded
	 */
	private void addManufacturedRequirements(Manufactured manufactured, int amount) {
		manufactured.getRequirements()
				.forEach((key, value) -> addAmount(resourcesNeeded, key, manufactured.getRealResourcesAmount(value, amount)));
	}

	/**
	 * Return true if they are enough natural resources to craft a amount of manufactured resource
	 */
	public boolean canCraft(Manufactured manufactured, int wantedAmount) {
		return manufactured.getRequirements().entrySet().stream()
				.noneMatch(requirement -> manufactured.getRealResourcesAmount(requirement.getValue(), wantedAmount) > resourcesInventory.get(requirement.getKey()));
	}

	/**
	 * Return true if we can craft something from all manufactured needed and resources inventory
	 */
	public boolean canCraftSomething() {
		return manufacturedNeeded.entrySet().stream()
				.anyMatch(entry -> canCraft(entry.getKey(), entry.getValue()));
	}

	public boolean nothingLeftNeeded() {
		return resourcesNeeded.isEmpty() && manufacturedNeeded.isEmpty();
	}

	public void logInventory(Logger log) {
		log.info("");
		log.info("Sailors inventory :");
		Arrays.stream(Resource.values())
				.filter(resources -> getResourceAmount(resources) != 0)
				.forEach(resources -> log.info(resources.toString() + " : " + getResourceAmount(resources)));
		Arrays.stream(Manufactured.values())
				.filter(manufactured -> getManufacturedAmount(manufactured) != 0)
				.forEach(manufactured -> log.info(manufactured.toString() + " : " + getManufacturedAmount(manufactured)));
	}
}