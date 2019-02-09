package fr.unice.polytech.si3.qgl.isg.bots.sailors;

import fr.unice.polytech.si3.qgl.isg.Contract;
import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.Exploit;
import fr.unice.polytech.si3.qgl.isg.actions.Move;
import fr.unice.polytech.si3.qgl.isg.actions.Transform;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.bots.Bot;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases.SailorsPhase;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.map.SailorsSubPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Sailors extends Bot {

	private InventoryManager inventoryManager;
	private GoalManager goalManager;
	private SailorsSubPosition subPosition;
	private List<Cell> cellsToExploit;
	private AtomicInteger worthlessExplore;
	private int budget;

	public Sailors(ActionSequence sequence, CellMap cellMap, Contract contract) {
		super(sequence, cellMap);
		this.inventoryManager = new InventoryManager();
		this.goalManager = new GoalManager(contract);
		this.phase = SailorsPhase.INITIALLAND;
		this.cellsToExploit = new ArrayList<>();
		this.worthlessExplore = new AtomicInteger(0);
	}

	public List<Cell> getCellsToExploit() {
		return cellsToExploit;
	}

	public SailorsSubPosition getSubPosition() {
		return subPosition;
	}

	public GoalManager getGoalManager() {
		return this.goalManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public AtomicInteger getWorthlessExplore() {
		return worthlessExplore;
	}

	public int getBudget() {
		return budget;
	}

	/**
	 * Run the phase to take a decision
	 */
	public void run(int budget) {
		this.budget = budget;
		while (!sequence.hasBuffer()) {
			ActionAndPhase actionAndPhase = super.phase.run(this);
			sequence.add(actionAndPhase.getActions().toArray(new Action[0]));
			setPhase(actionAndPhase.getPhase());
		}
	}

	/**
	 * After an action, update the sailors fields. If the last action is :
	 * - Explore : set the cell and the subcell at visited
	 * - Move : update the sailors position
	 * - Exploit : update the sailors inventory
	 * - Transform : update the sailors inventory
	 * - Land : update the sailors position and subPosition
	 */
	public void update() {
		setLastAction(sequence.getLastAction());

		switch (lastAction.getName()) {
			case EXPLORE:
				cellMap.setVisitedCell(position);
				cellMap.getCell(position).getPosition().setSubPositionVisited(subPosition.getX(), subPosition.getY());
				break;

			case MOVE:
				subPosition.updatePosition(((Move) lastAction).getDirection());
				break;

			case EXPLOIT:
				inventoryManager.addResource(((Exploit) lastAction).getResource(), ((Exploit) lastAction).getAmount());
				break;

			case TRANSFORM:
				inventoryManager.addManufactured(((Transform) lastAction).getProductsList(), ((Transform) lastAction).getResourcesList());
				break;

			case LAND:
				position.setPosition(cellMap.getCurrentCreekCell().getPosition());
				subPosition = new SailorsSubPosition(position, 0, 0);
				break;

			default:
				logger.info("Error in Sailors : phase unknown.");
		}

		goalManager.checkGoals(inventoryManager.getResourcesInventory(), inventoryManager.getManufacturedInventory());
		inventoryManager.updateInventoryNeeded(goalManager.getCompletedGoals(), goalManager.getUncompletedGoals());

		if (goalManager.noGoalLeft() || inventoryManager.nothingLeftNeeded()) {
			sequence.emergencyStop();
		}

		logStatus();
	}

	private void logStatus() {
		logger.info("Sailors cellPosition : " + position);
		logger.info("Sailors subPosition : " + subPosition);
		logger.info("Resources inventory: " + inventoryManager.getResourcesInventory().entrySet().stream()
				.filter(res -> res.getValue() != 0)
				.collect(Collectors.toList()));
		logger.info("Resources needed: " + inventoryManager.getResourcesNeeded());
		logger.info("Manufactured inventory : " + inventoryManager.getManufacturedInventory().entrySet().stream()
				.filter(man -> man.getValue() != 0)
				.collect(Collectors.toList()));
		logger.info("Manufactured needed : " + inventoryManager.getManufacturedNeeded());
	}
}