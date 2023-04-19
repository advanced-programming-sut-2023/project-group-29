package model.buildings;

import model.Asset;
import model.PlayerNumber;

public class Building extends Asset {
    protected int hp;
    protected int numberOfWorkers;
    protected int cost;
    protected int maxHp;

    protected Building(BuildingType buildingType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(playerNumber, positionX, positionY);
        this.cost = buildingType.getCost();
        this.hp = buildingType.getHp();
        this.numberOfWorkers = buildingType.getNumberOfWorkers();
        maxHp = hp;
    }

    public static boolean isBuildingTypeValid(String BuildingType) {
        //TODO: complete
        return false;
    }

    public boolean isEnemyNearIt() {
        //TODO: complete
        return false;
    }

    public void repair() {
        //TODO: decrease stone equal to maxHp - hp;
        hp = maxHp;
    }
}
