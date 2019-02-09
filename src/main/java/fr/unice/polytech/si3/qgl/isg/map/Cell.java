package fr.unice.polytech.si3.qgl.isg.map;

import fr.unice.polytech.si3.qgl.isg.resources.Resource;

import java.util.*;

public class Cell {
	private Position position;
	private Set<Biome> biomes;
	private List<String> creeks;
	private String site;
	private boolean visited;
	private boolean estimated;
	private Map<Resource, Double> estimatedResources;
	private Set<Resource> resources;

	public Cell(Position position, Set<Biome> biomes, List<String> creeks, String site) {
		this.position = position;
		this.biomes = biomes;
		this.creeks = creeks;
		this.site = site;
		this.visited = false;
		this.estimated = false;
		setResources();
		setEstimatedResources();
	}

	public Cell(int x, int y, Set<Biome> biomes) {
		this.position = new Position(x, y);
		this.biomes = biomes;
		this.creeks = new ArrayList<>();
		this.site = "";
		this.visited = false;
		setResources();
		setEstimatedResources();
	}

	public Position getPosition() {
		return position;
	}

	public Set<Biome> getBiomes() {
		return biomes;
	}

	public List<String> getCreeks() {
		return creeks;
	}

	public String getSite() {
		return site;
	}

	boolean containsCreek() {
		return !this.creeks.isEmpty();
	}

	boolean containsSite() {
		return !this.site.equals("");
	}

	/**
	 * @return the distance between two cells.
	 */
	public double distance(Cell other) {
		return this.getPosition().distance(other.getPosition());
	}

	public boolean containsResource(Resource resource) {
		return getResources().contains(resource);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isEstimated() {
		return estimated;
	}

	public void setEstimated() {
		this.estimated = true;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public Map<Resource, Double> getEstimatedResources() {
		return estimatedResources;
	}

	/**
	 * Sets all the different resources that can be found on the cell.
	 */
	private void setResources() {
		this.resources = new HashSet<>();
		biomes.stream().map(Biome::concernedResources).forEach(resources::addAll);
	}

	/**
	 * Sets the estimated amount of each resource on the cell.
	 */
	private void setEstimatedResources() {
		this.estimatedResources = new EnumMap<>(Resource.class);
		Arrays.stream(Resource.values()).forEach(resource -> estimatedResources.put(resource, 0.0));
		for (Resource resource : resources) {
			for (Biome biome : biomes) {
				if (resource.getConcernedBiomes().contains(biome)) {
					estimatedResources.merge(resource, resource.getCellAmountEstimation(biome) / biomes.size(), Double::sum);
				}
			}
		}
	}

}
