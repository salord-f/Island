package fr.unice.polytech.si3.qgl.isg;

import fr.unice.polytech.si3.qgl.isg.map.Direction;
import fr.unice.polytech.si3.qgl.isg.resources.Manufactured;
import fr.unice.polytech.si3.qgl.isg.resources.Resource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import static fr.unice.polytech.si3.qgl.isg.Explorer.logger;

public class Contract {
	private int men;
	private int budget;
	private List<Goal> goals;
	private Direction direction;

	public Contract() {
		this.goals = new ArrayList<>();
	}

	public void initialize(String jsonFile) {
		JSONObject jsonObject;
		try {
			this.men = 0;
			this.budget = 0;
			this.goals.clear();
			this.direction = null;
			jsonObject = new JSONObject(jsonFile);
			fillCrew(jsonObject);
			fillBudget(jsonObject);
			fillContracts(jsonObject);
			fillDroneHeading(jsonObject);
		} catch (Exception e) {
			this.men = 0;
			this.budget = 0;
			this.goals.clear();
			this.direction = null;
			logger.debug(e);
		}
	}

	public int getMen() {
		return men;
	}

	public int getBudget() {
		return budget;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public Direction getDirection() {
		return direction;
	}

	private void fillCrew(JSONObject jsonObject) {
		if (jsonObject.has("men")) {
			try {
				this.men = jsonObject.getInt("men");
			} catch (Exception e) {
				logger.debug(e);
			}
		} else {
			throw new EmptyStackException();
		}
	}

	private void fillBudget(JSONObject jsonObject) {
		if (jsonObject.has("budget")) {
			try {
				this.budget = jsonObject.getInt("budget");

			} catch (Exception e) {
				logger.debug(e);
			}
		} else {
			throw new EmptyStackException();
		}
	}

	private void fillContracts(JSONObject jsonObject) {
		if (jsonObject.has("contracts")) {
			try {
				JSONArray jsonArray = jsonObject.getJSONArray("contracts");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonSubObject = jsonArray.getJSONObject(i);
					String contractResource = jsonSubObject.getString("resource");
					if (Resource.fromString(contractResource) != null) {
						goals.add(new Goal(Resource.fromString(contractResource), jsonSubObject.getInt("amount")));
					} else if (Manufactured.fromString(contractResource) != null) {
						goals.add(new Goal(Manufactured.fromString(contractResource), jsonSubObject.getInt("amount")));
					}
				}
			} catch (Exception e) {
				logger.debug(e);
			}
		} else {
			throw new EmptyStackException();
		}
	}

	private void fillDroneHeading(JSONObject jsonObject) {
		if (jsonObject.has("heading")) {
			try {
				this.direction = Direction.getDirection(jsonObject.getString("heading"));
			} catch (Exception e) {
				logger.debug(e);
			}
		} else {
			throw new EmptyStackException();
		}
	}
}
