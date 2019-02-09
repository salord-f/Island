package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.actions.Land;
import fr.unice.polytech.si3.qgl.isg.actions.Stop;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.GoalManager;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;

public class InitialLand extends ActionAndPhase {

	public InitialLand() {
		super(SailorsPhase.INITIALLAND);
	}

	/**
	 * Initializes the different parameters required for the sailors
	 * and lands one sailor on the creek nearest to our first zone to exploit.
	 */
	public ActionAndPhase run(CellMap cellMap, GoalManager goalManager, InventoryManager inventoryManager, int budget) {
		cellMap.fillMap(true);
		goalManager.initialize(budget);
		if (!goalManager.noGoalLeft()) {
			inventoryManager.updateInventoryNeeded(goalManager.getCompletedGoals(), goalManager.getUncompletedGoals());
			cellMap.setCurrentCreekToNearestResources(inventoryManager.getResourcesNeeded());
			super.actions.add(new Land(cellMap.getCurrentCreek(), 1));
			super.phase = SailorsPhase.MOVEANDEXPLOIT;
		} else super.actions.add(new Stop());
		return this;
	}
}
