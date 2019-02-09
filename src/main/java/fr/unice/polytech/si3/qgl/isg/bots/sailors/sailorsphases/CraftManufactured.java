package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.actions.Transform;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.InventoryManager;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;

import java.util.Map;

public class CraftManufactured extends ActionAndPhase {

	public CraftManufactured() {
		super(SailorsPhase.CRAFTMANUFACTURED);
	}

	/**
	 * Crafts the first manufactured item the sailors can.
	 */
	public ActionAndPhase run(InventoryManager inventoryManager) {
		for (Map.Entry<Manufactured, Integer> entry : inventoryManager.getManufacturedNeeded().entrySet()) {
			if (inventoryManager.canCraft(entry.getKey(), entry.getValue())) {
				super.actions.add(new Transform(entry.getKey(), entry.getValue()));
				return this;
			}
		}
		super.phase = SailorsPhase.MOVEANDEXPLOIT;
		return this;
	}
}
