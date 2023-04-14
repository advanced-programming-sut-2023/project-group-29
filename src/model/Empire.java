package model;

import model.buildings.Building;
import model.people.Soldier;
import model.people.Worker;

import java.util.ArrayList;

public class Empire {
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();//TODO expand this to its children
    private int population;
    private int growthRate;
    private int peopleSatisfaction;
    private int wealth;
    private int tax;
    private int fear;
    private ArrayList<Food> foods;
    private int foodRate;

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPeopleSatisfaction() {
        return peopleSatisfaction;
    }

    public void setPeopleSatisfaction(int peopleSatisfaction) {
        this.peopleSatisfaction = peopleSatisfaction;
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

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
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


}
