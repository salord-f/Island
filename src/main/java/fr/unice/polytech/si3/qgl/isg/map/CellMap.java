package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static fr.unice.polytech.si3.qgl.isg.Explorer.logger;

/**
 * This is the map created with the drone's scanning information.
 */
public class CellMap {
	private List<Cell> map;
	private ResourceZone resourceZone;
	private String currentCreek;
	private Map<Resource, Double> estimatedResources;

	public CellMap() {
		this.map = new ArrayList<>();
		this.resourceZone = new ResourceZone(map);
		this.currentCreek = null;
		this.estimatedResources = new EnumMap<>(Resource.class);
		Arrays.stream(Resource.values()).forEach(resource -> estimatedResources.put(resource, 0.0));
	}

	public String getCurrentCreek() {
		return currentCreek;
	}

	public void setCurrentCreek(String currentCreek) {
		this.currentCreek = currentCreek;
	}

	public void setCurrentCreekToNearestResources(Map<Resource, Integer> resources) {
		this.currentCreek = creekWithAllResourcesNeededAround(resources);
	}

	public List<Cell> getMap() {
		return map;
	}

	/**
	 * Creates a cell defined by a position and Cell object that contain its biomes, creekId and siteId
	 */
	public void addCell(int x, int y, Set<Biome> biomes, List<String> creekId, String siteId) {
		Cell cell = getCell(x, y);
		if (cell != null) {
			map.remove(cell);
			logger.info("WARNING : A cell in cellMap was overridden : " + cell.toString());
		}
		map.add(new Cell(new Position(x, y), biomes, creekId, siteId));
	}

	public void addCell(int x, int y, Set<Biome> biomes) {
		this.addCell(x, y, biomes, new ArrayList<>(), "");
	}

	/**
	 * @return the cell corresponding to the position.
	 */
	public Cell getCell(Position position) {
		return this.getCell(position.getX(), position.getY());
	}

	/**
	 * @return the cell corresponding to the coordinates.
	 */
	public Cell getCell(int x, int y) {
		// Using a for each loop is more optimized than a stream
		for (Cell cell : map) {
			if (cell.getPosition().equalsPosition(x, y)) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * Return a Cell that contains the creek with the param id.
	 *
	 * @param creek the id of a creek
	 * @return the cell that contains the concerned creek
	 */
	public Cell getCellByCreek(String creek) {
		return map.stream()
				.filter(cell -> cell.getCreeks().contains(creek))
				.findFirst().orElse(null);
	}

	public Cell getCurrentCreekCell() {
		return getCellByCreek(currentCreek);
	}

	/**
	 * the cell is visited
	 */
	public void setVisitedCell(Position position) {
		Cell cell = getCell(position);
		if (cell != null) {
			cell.setVisited(true);
		}
	}

	/**
	 * @return a list of cells that contains creeks.
	 */
	public List<Cell> getCreeks() {
		return map.stream()
				.filter(Cell::containsCreek)
				.collect(Collectors.toList());
	}

	/**
	 * @return true if no creek was found
	 */
	public boolean noCreeks() {
		return getCreeks().isEmpty();
	}

	/**
	 * @return the cell that contains the emergency site.
	 */
	public Cell getSiteCell() {
		return map.stream()
				.filter(Cell::containsSite)
				.findFirst().orElse(null);
	}

	/**
	 * Return the nearest cell from another one in a list of cells.
	 */
	public Cell nearestFromCell(List<Cell> cellList, Cell cell) {
		try {
			cellList.sort(Comparator.comparingDouble(cell::distance));
			return cellList.get(0);
		} catch (Exception e) {
			logger.info("Error while computing nearestFromCell: " + e);
			return null;
		}
	}

	public String nearestCreekToSite() {
		if (getSiteCell() != null && !noCreeks()) {
			return Objects.requireNonNull(nearestFromCell(getCreeks(), getSiteCell())).getCreeks().get(0);
		} else return "No creek found near the site.";
	}

	/**
	 * Return the creek with best sum of distance between the nearest cell of each resources needed and the creek.
	 * Return null and an exception if there is no creek.
	 */
	private String creekWithAllResourcesNeededAround(Map<Resource, Integer> resourcesNeeded) {
		try {
			Map<Cell, Double> creekAndSumDistance = new HashMap<>();
			Cell nearestCell;
			for (Cell cellCreek : getCreeks()) {
				creekAndSumDistance.put(cellCreek, 0.0);
				for (Resource resource : resourcesNeeded.keySet()) {
					nearestCell = nearestFromCell(getCellsWithResource(resource), cellCreek);
					if (nearestCell != null) {
						creekAndSumDistance.merge(cellCreek, cellCreek.distance(nearestCell), Double::sum);
					}
				}
			}
			return Collections.min(creekAndSumDistance.entrySet(), Comparator.comparingDouble(Map.Entry::getValue))
					.getKey().getCreeks().get(0);
		} catch (Exception e) {
			logger.info("Error in creekWithAllResourcesNeededAround" + e);
			return null;
		}
	}

	/**
	 * Get a list of cell not visited with only the good resource
	 */
	private List<Cell> getCellsWithResource(Resource resource) {
		return map.stream()
				.filter(cell -> !cell.isVisited() && cell.getResources().contains(resource))
				.collect(Collectors.toList());
	}

	/**
	 * Create a list of cell from the nearest valid Cell of resources
	 */
	public List<Cell> getNearestResourceZone(Map<Resource, Integer> resources) {
		Map<Cell, Resource> closestCellsByResource = new HashMap<>();
		for (Resource resource : resources.keySet()) {
			closestCellsByResource.put(nearestFromCell(getCellsWithResource(resource), getCurrentCreekCell()), resource);
		}
		if (closestCellsByResource.isEmpty()) {
			return new ArrayList<>();
		}
		Cell cell = closestCellsByResource.keySet().stream()
				.filter(Objects::nonNull)
				.min(Comparator.comparingDouble(getCurrentCreekCell()::distance))
				.orElse(null);

		return resourceZone.createResourceZone(cell, closestCellsByResource.get(cell));
	}

	/**
	 * Fill the missing Cells in the map
	 * with information from adjacent ones
	 */
	public void fillMap(boolean intersection) {
		List<Cell> mapCopy = new ArrayList<>(map);
		for (Cell cell : mapCopy) {
			int x = cell.getPosition().getX();
			int y = cell.getPosition().getY();
			fillCell(cell, getCell(x + 2, y), x + 1, y, intersection);
			fillCell(cell, getCell(x, y + 2), x, y + 1, intersection);
		}
	}

	/**
	 * Helper method for fillMap to fill each missing Cell.
	 * It works either by using the intersection of biomes found
	 * in the two adjacent cells, or the union.
	 * If the intersection is empty, it uses the union.
	 */
	private void fillCell(Cell cell1, Cell cell2, int x, int y, boolean biomeIntersection) {
		if (cell2 != null && getCell(x, y) == null) {
			Set<Biome> biomes = new HashSet<>(cell1.getBiomes());
			if (biomeIntersection) {
				biomes.retainAll(cell2.getBiomes());
				if (biomes.isEmpty()) {
					biomes.addAll(cell1.getBiomes());
					biomes.addAll(cell2.getBiomes());
				}
			} else biomes.addAll(cell2.getBiomes());
			addCell(x, y, biomes);
		}
	}

	/**
	 * Get all adjacent cells of the position from a list of cell.
	 */
	private List<Cell> getAdjacentCells(List<Cell> cellZone, Position position) {
		return cellZone.stream()
				.filter(cell -> position.isAdjacent(cell.getPosition()))
				.collect(Collectors.toList());
	}

	/**
	 * Sort the cellZone to optimize sailors path.
	 * it's the nearest cell not visited from him between all adjacent cell.
	 * if there is no adjacent cell, go on the nearest cell not visited from him.
	 */
	public List<Cell> sortCellZone(List<Cell> cellZone) {
		List<Cell> sortedCellZone = new ArrayList<>();
		Cell nearestCell;
		Cell currentCell = nearestFromCell(cellZone, getCurrentCreekCell());
		if (currentCell == null) {
			logger.info("cellZone or creek is empty");
			return cellZone;
		}
		cellZone.remove(currentCell);
		sortedCellZone.add(currentCell);
		while (!cellZone.isEmpty()) {
			nearestCell = nearestFromCell(getAdjacentCells(cellZone, currentCell.getPosition()), getCurrentCreekCell());
			if (nearestCell == null) {
				nearestCell = nearestFromCell(cellZone, currentCell);
				logger.info("No more adjacent while sorting the CellZone, sailors will need to walk.");
			}
			if (nearestCell == null) {
				logger.info("Error in sortedCellZone : cellZone is empty");
				return sortedCellZone;
			}
			currentCell = nearestCell;
			cellZone.remove(currentCell);
			sortedCellZone.add(currentCell);
		}
		return sortedCellZone;
	}

	/**
	 * The estimated resources amount on all the map.
	 */
	public Map<Resource, Double> estimateAndGetResources() {
		map.stream()
				.filter(cell -> !cell.isEstimated())
				.forEach(cell -> {
					cell.getEstimatedResources().forEach((key, value) -> estimatedResources.merge(key, value, Double::sum));
					cell.setEstimated();
				});
		return estimatedResources;
	}

	public void logCellMap(Logger log) {
		log.info("");
		String site = (getSiteCell() != null) ? getSiteCell().getSite() : "No site found.";
		log.info("Emergency site : " + site);
		log.info("Nearest creek : " + nearestCreekToSite());
		log.info("All creeks found : ");
		getCreeks().forEach(creek -> creek.getCreeks().forEach(log::info));
		log.info("");
		log.info("Estimated resources : ");
		estimateAndGetResources().forEach((resource, amount) -> log.info(resource + " : " + Math.round(amount)));
	}
}
