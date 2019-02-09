package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.bots.Phase;
import fr.unice.polytech.si3.qgl.isg.bots.drone.Drone;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.Sailors;

/**
 * The different sailors phases.
 * Going from one phase to another is done
 * inside the class associated to the phase.
 */
public enum SailorsPhase implements Phase {
	INITIALLAND("Initial land") {
		public ActionAndPhase run(Sailors sailors) {
			return new InitialLand()
					.run(sailors.getCellMap(),
							sailors.getGoalManager(),
							sailors.getInventoryManager(), sailors.getBudget());
		}
	},
	CRAFTMANUFACTURED("Crafting manufactured") {
		public ActionAndPhase run(Sailors sailors) {
			return new CraftManufactured().run(sailors.getInventoryManager());
		}
	},
	MOVEANDEXPLOIT("Moving or exploring") {
		public ActionAndPhase run(Sailors sailors) {
			return new MoveAndExploit(sailors.getInventoryManager(),
					sailors.getCellMap(),
					sailors.getPosition(),
					sailors.getWorthlessExplore())
					.run(sailors.getCellsToExploit(),
							sailors.getLastAction(),
							sailors.getSubPosition());
		}
	},
	CHANGINGZONE("Changing exploit zone") {
		public ActionAndPhase run(Sailors sailors) {
			return new ChangingZone(sailors.getCellsToExploit(),
					sailors.getCellMap(),
					sailors.getInventoryManager()).run();
		}
	},
	MOVEBETWEENCELLS("Move between cells") {
		public ActionAndPhase run(Sailors sailors) {
			return new MoveBetweenCells()
					.run(sailors.getCellsToExploit(), sailors.getPosition());
		}
	},
	ENDEXPLORATION("Exploration is finished") {
		public ActionAndPhase run(Sailors sailors) {
			// This method throws an exception as
			// it is never invoked, we stop before
			throw new UnsupportedOperationException();
		}
	};

	private final String phaseName;

	SailorsPhase(String phase) {
		this.phaseName = phase;
	}

	public abstract ActionAndPhase run(Sailors sailors);

	public ActionAndPhase run(Drone drone) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return phaseName;
	}
}
