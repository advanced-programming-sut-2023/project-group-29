package model.buildings;

import model.Asset;
import model.PlayerNumber;

public class Building extends Asset {
    protected int hp;
    protected int numberOfWorkers;
    protected int cost;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber) {
        super(playerNumber);
        this.cost = buildingType.getCost();
        this.hp = buildingType.getHp();
        this.numberOfWorkers = buildingType.getNumberOfWorkers();
    }

}
