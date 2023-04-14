package model.buildings;

public class BuildingType
{
    private int hp;
    private int numberOfWorkers;
    private int cost;

    public BuildingType(int hp,int numberOfWorkers,int cost)
    {
        this.cost=cost;
        this.hp=hp;
        this.numberOfWorkers=numberOfWorkers;
    }
    public int getHp()
    {
        return hp;
    }

    public int getNumberOfWorkers()
    {
        return numberOfWorkers;
    }

    public int getCost()
    {
        return cost;
    }
}
