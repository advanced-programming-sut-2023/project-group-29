package model.buildings;

public class Building {
    protected int hp;
    protected int numberOfWorkers;
    protected int cost;

    protected Building(BuildingType buildingType)
    {
        this.cost=buildingType.getCost();
        this.hp= buildingType.getHp();
        this.numberOfWorkers= buildingType.getNumberOfWorkers();
    }

}
