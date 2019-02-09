package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;

import java.util.List;

import static fr.unice.polytech.si3.qgl.isg.Explorer.logger;

public class ChangingZone extends ActionAndPhase {
	private List<Cell> cellsToExploit;
	private CellMap cellMap;
	private InventoryManager inventoryManager;

	public ChangingZone(List<Cell> cellsToExploit, CellMap cellMap, InventoryManager inventoryManager) {
		super(SailorsPhase.CHANGINGZONE);
		this.cellsToExploit = cellsToExploit;
		this.cellMap = cellMap;
		this.inventoryManager = inventoryManager;
	}

	/**
	 * Stops if they are no cells to exploit or if there is no resource needed left.
	 * Otherwise have a new zone to exploit and move to it.
	 */
	public ActionAndPhase run() {
		cellsToExploit.clear();
		if (inventoryManager.nothingLeftNeeded() || !getCellsToExploit()) {
			super.phase = SailorsPhase.ENDEXPLORATION;
		} else {
			super.phase = SailorsPhase.MOVEBETWEENCELLS;
		}
		return this;
	}

	/**
	 * Get the closest zone of resources to exploit.
	 *
	 * @return false if the sailors can't find a new zone to explore.
	 */
	private boolean getCellsToExploit() {
		cellsToExploit.addAll(cellMap.getNearestResourceZone(inventoryManager.getResourcesNeeded()));
		if (!cellsToExploit.isEmpty()) {
			cellsToExploit.addAll(cellMap.sortCellZone(cellsToExploit));
			logger.info("New zone to exploit, go on : " + cellsToExploit.get(0).getPosition());
			return true;
		} else {
			logger.info("No more resource area to exploit... :(");
			return false;
		}
	}
}
