package model;

import model.buildings.Building;
import model.people.humanClasses.Soldier;
import model.people.humanClasses.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Empire {
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();//TODO expand this to its children
    private final int[] foods = new int[4];
    private int population;
    private int growthRate;
    private HashMap<String, Integer> popularityChange = new HashMap<>();
    private int wealth;
    private int tax;
    private int fear;
    private int foodRate;
    private int popularity = 0;

    //TODO: initialize correctly
    //initialize popularity:
    {
        //TODO: initialize correctly
        popularityChange.put("religion", 0);
        popularityChange.put("tax", 0);
        popularityChange.put("fear", 0);
        popularityChange.put("foodRate", 0);
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public int[] getFoods() {
        return foods;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(int growthRate) {
        this.growthRate = growthRate;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public int getPopularityChange(String cause) {
        return popularityChange.get(cause);
    }

    public void adjustPopularity() {
        //TODO: adjust numbers correctly:
        popularityChange.replace("religion", 0);
        popularityChange.replace("tax", 0);
        popularityChange.replace("fear", 0);
        popularityChange.replace("foodRate", foodRate * 4);
        //TODO: affect it to real popularity.
    }

    public boolean hasEnoughStoneToRepair(Building building) {
        //TODO: complete
        return false;
    }

    public void decreaseStone(int amount) {
        //TODO: complete
    }

    //TODO: modify equal function
}
