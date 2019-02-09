package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceZone {
	private List<Cell> cellList;

	public ResourceZone(List<Cell> cellList) {
		this.cellList = cellList;
	}

	/**
	 * Creates a List of adjacent cell
	 * with the corresponding resource
	 */
	public List<Cell> createResourceZone(Cell cell, Resource resource) {
		if (cell == null || !cell.getResources().contains(resource)) {
			return new ArrayList<>();
		}
		List<Cell> cellZone = new ArrayList<>();
		cellZone.add(cell);
		fillZone(cellZone, resource, cell);
		return cellZone;
	}

	/**
	 * Helper method for createResourceZone
	 * Calls fillZoneHelper on each adjacent position
	 * from the @param cell
	 */
	private void fillZone(List<Cell> cellZone, Resource resource, Cell cell) {
		Position position = cell.getPosition();
		fillZoneHelper(cellZone, resource, position.getX() + 1, position.getY());
		fillZoneHelper(cellZone, resource, position.getX() - 1, position.getY());
		fillZoneHelper(cellZone, resource, position.getX(), position.getY() + 1);
		fillZoneHelper(cellZone, resource, position.getX(), position.getY() - 1);
	}

	/**
	 * If the cell exists, isn't yet in the list
	 * and contains the wanted resource,
	 * it's added to the list returned
	 * by createResourceZone
	 */
	private void fillZoneHelper(List<Cell> cellZone, Resource resource, int x, int y) {
		Cell cell = getCell(x, y);
		if (validCell(cellZone, resource, cell)) {
			cellZone.add(cell);
			fillZone(cellZone, resource, cell);
		}
	}

	/**
	 * @return the Cell corresponding to the (x,y) coordinates
	 */
	public Cell getCell(int x, int y) {
		return cellList.stream()
				.filter(cell -> cell.getPosition().equalsPosition(x, y))
				.findFirst().orElse(null);
	}

	/**
	 * @return true if the cell should be added
	 * to the cellZone list
	 */
	private boolean validCell(List<Cell> cellZone, Resource resource, Cell cell) {
		return cell != null
				&& !cell.isVisited()
				&& !cellZone.contains(cell)
				&& cell.getResources().contains(resource);
	}
}
