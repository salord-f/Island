package fr.unice.polytech.si3.qgl.isg.bots.drone.dronephases;

import fr.unice.polytech.si3.qgl.isg.actions.Action;
import fr.unice.polytech.si3.qgl.isg.actions.Echo;
import fr.unice.polytech.si3.qgl.isg.actions.Heading;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;

import java.util.ArrayList;
import java.util.List;

public class SearchIsland extends ActionAndPhase {
	private Position position;
	private Direction heading;

	public SearchIsland(Position position, Direction heading) {
		super(DronePhase.SEARCHISLAND);
		this.position = position;
		this.heading = heading;
	}

	/**
	 * Searches ground with a staircase move.
	 */
	public ActionAndPhase run(Direction groundDirection) {
		if (groundDirection != null) {
			super.phase = DronePhase.FLYTO;
			return this;
		}
		if (heading == position.getFirstDirection()) {
			super.actions = searchActions(position.getSecondDirection());
		} else {
			super.actions = searchActions(position.getFirstDirection());
		}
		return this;
	}

	/**
	 * One heading and one echo to find ground.
	 */
	private List<Action> searchActions(Direction direction) {
		List<Action> actions = new ArrayList<>();
		actions.add(new Heading(direction));
		actions.add(new Echo(direction));
		return actions;
	}
}
