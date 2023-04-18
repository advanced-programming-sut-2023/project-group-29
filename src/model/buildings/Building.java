package model.buildings;

import model.Asset;
import model.PlayerNumber;

public class Building extends Asset {
    protected int hp;
    protected int numberOfWorkers;
    protected int cost;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber,int positionX,int positionY) {
        super(playerNumber,positionX,positionY);
        this.cost = buildingType.getCost();
        this.hp = buildingType.getHp();
        this.numberOfWorkers = buildingType.getNumberOfWorkers();
    }

}
