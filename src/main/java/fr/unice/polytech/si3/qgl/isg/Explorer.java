package fr.unice.polytech.si3.qgl.isg;

import eu.ace_design.island.bot.IExplorerRaid;
import fr.unice.polytech.si3.qgl.isg.actions.Stop;
import fr.unice.polytech.si3.qgl.isg.bots.ActionSequence;
import fr.unice.polytech.si3.qgl.isg.bots.drone.Drone;
import fr.unice.polytech.si3.qgl.isg.bots.sailors.Sailors;
import fr.unice.polytech.si3.qgl.isg.map.CellMap;
import fr.unice.polytech.si3.qgl.isg.resources.ResourceCheck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Explorer class which works with
 * the game engine and the rest of
 * the project to fulfill contracts.
 */
public class Explorer implements IExplorerRaid {

	public static final Logger logger = LogManager.getLogger("Explorer");
	private final ExecutorService executorService;
	private final ActionSequence sequence;
	private final CellMap cellMap;
	private final Contract contract;
	private final Drone drone;
	private final Sailors sailors;
	private final ResourceCheck resourceCheck;
	private int budget;
	private boolean landPhase;
	private long startTime, endTime; // Only used for micro-benchmarking our execution time

	public Explorer() {
		this.executorService = Executors.newSingleThreadExecutor();
		this.sequence = new ActionSequence();
		this.cellMap = new CellMap();
		this.contract = new Contract();
		this.resourceCheck = new ResourceCheck();
		this.drone = new Drone(sequence, cellMap);
		this.sailors = new Sailors(sequence, cellMap, contract);
		this.landPhase = false;
	}

	@Override
	public void initialize(String contractToFulfill) {
		try {
			startTime = System.currentTimeMillis();
			logger.info("Initializing the Explorer");
			logger.info("Contract: " + contractToFulfill);
			contract.initialize(contractToFulfill);
			drone.setHeading(contract.getDirection());
			budget = contract.getBudget();
			sailors.getGoalManager().initialize(budget);
			sailors.getInventoryManager().updateInventoryNeeded(sailors.getGoalManager().getCompletedGoals(), sailors.getGoalManager().getUncompletedGoals());
			resourceCheck.setResourcesNeeded(sailors.getInventoryManager().getResourcesNeeded());
		} catch (Exception e) {
			logger.info("Error in initialize : " + e);
			Arrays.stream(e.getStackTrace()).forEach(b -> logger.info(b.toString()));
		}
	}

	@Override
	public String takeDecision() {
		logger.info("");
		logger.info("Taking a decision now");
		JSONObject decision;

		// The executor service is here to avoid
		// going over 2s of compute time for each action
		Future<JSONObject> future = executorService.submit(this::decisionMaking);
		try {
			decision = future.get(1500, TimeUnit.MILLISECONDS);  // wait 1,5 seconds to finish
		} catch (Exception e) {
			future.cancel(true);
			logger.info("Error in takeDecision : " + e);
			Arrays.stream(e.getStackTrace()).forEach(b -> logger.info(b.toString()));
			return new Stop().run().toString();
		}
		return decision.toString();
	}

	@Override
	public void acknowledgeResults(String results) {
		try {
			sequence.saveResults(new JSONObject(results));
			budget -= sequence.getLastAction().getCost();
		} catch (Exception e) {
			logger.info("Error in acknowledgeResults : " + e);
			Arrays.stream(e.getStackTrace()).forEach(b -> logger.info(b.toString()));
		}
	}

	@Override
	public String deliverFinalReport() {
		try {
			executorService.shutdownNow();
			endTime = System.currentTimeMillis();
			loggerReport();
			return "Hey boss we completed " + sailors.getGoalManager().getCompletedGoals().size() + " goal(s)!";
		} catch (Exception e) {
			logger.info("Error in delivery report : " + e);
			Arrays.stream(e.getStackTrace()).forEach(b -> logger.info(b.toString()));
			return "Nothing to report.";
		}
	}

	/**
	 * Method to check we are not running
	 * out of budget for the stop call
	 */
	private boolean enoughBudget() {
		return budget > 300;
	}

	/**
	 * @return true if the drone found at least one creek,
	 * and either it spent >25% of budget
	 * or we've scanned enough resources
	 */
	private boolean droneLandConditions() {
		return !cellMap.noCreeks() &&
				(budget < contract.getBudget() * 75.0 / 100
						|| resourceCheck.checkResourcesAmount(cellMap));
	}

	/**
	 * Decision method calling Drone and Sailors AI
	 *
	 * @return a json string with the requested action
	 */
	private JSONObject decisionMaking() {
		try {
			if (!enoughBudget()) {
				logger.info("No more budget.");
				return new Stop().run();
			}

			if (!landPhase) {
				drone.update();
			} else sailors.update();

			if (!landPhase) {
				landPhase = droneLandConditions();
			}

			while (!sequence.hasBuffer()) {
				if (!landPhase) {
					landPhase = drone.run();
				} else sailors.run(budget);
			}
		} catch (Exception e) {
			Arrays.stream(e.getStackTrace()).forEach(b -> logger.info(b.toString()));
			logger.info("Error in decisionMaking : " + e);
			sequence.emergencyStop();
		}
		return sequence.getNextAction().run();
	}

	/**
	 * Summarizes the exploration in order to evaluate our performance.
	 */
	private void loggerReport() {
		Logger log = LogManager.getLogger("Report");
		log.info("Explorer compute time : " + (endTime - startTime) + " milliseconds");
		log.info("Coefficient budget : " + (int) ((double) (contract.getBudget() - this.budget) / (double) sailors.getGoalManager().getGoals().get(0).getAmount() * 100.0));
		log.info("Budget : " + this.budget + "/" + contract.getBudget());
		sailors.getGoalManager().logGoalManager(log);
		sailors.getInventoryManager().logInventory(log);
		cellMap.logCellMap(log);
		resourceCheck.logResourceCheck(log);
	}
}
