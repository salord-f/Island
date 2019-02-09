package fr.unice.polytech.si3.qgl.isg.bots.sailors.sailorsphases;

import fr.unice.polytech.si3.qgl.isg.actions.Move;
import fr.unice.polytech.si3.qgl.isg.bots.ActionAndPhase;
import fr.unice.polytech.si3.qgl.isg.map.Cell;
import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.map.Position;

import java.util.List;

public class MoveBetweenCells extends ActionAndPhase {

	public MoveBetweenCells() {
		super(SailorsPhase.MOVEBETWEENCELLS);
	}

	/**
	 * Moves between the cell one subcell by subcell, vertically first, horizontally second
	 */
	public ActionAndPhase run(List<Cell> cellsToExploit, Position position) {
		Position cellPosition = cellsToExploit.get(0).getPosition();

		int posDiffX = position.getX() - cellPosition.getX();
		int posDiffY = position.getY() - cellPosition.getY();

		if (position.distance(cellPosition) != 0.0) {
			if (posDiffY > 0) {
				super.actions.add(new Move(Direction.SOUTH));
			} else if (posDiffY < 0) {
				super.actions.add(new Move(Direction.NORTH));
			}

			if (posDiffX > 0) {
				super.actions.add(new Move(Direction.WEST));
			} else if (posDiffX < 0) {
				super.actions.add(new Move(Direction.EAST));
			}
		} else {
			super.phase = SailorsPhase.MOVEANDEXPLOIT;
		}
		return this;
	}
}
