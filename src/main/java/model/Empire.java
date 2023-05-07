package model;

import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.dealing.*;
import model.people.humanClasses.Soldier;
import model.people.humanClasses.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Empire {
    private ArrayList<Trade> trades = new ArrayList<>();
    private ArrayList<Trade> newTrades = new ArrayList<>();
    private ArrayList<Trade> tradesHistory = new ArrayList<>();
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final HashMap<Building, Integer> buildings = new HashMap<>();//TODO expand this to its children
    private int[][] storage = new int[2][3]; //{food, productsAndResources, weapons} {0--> filled, 1--> capacity}
    private final HashMap<String, Integer> popularityChange = new HashMap<>();
    private HashMap<Tradable, Integer> tradableAmounts;
    private User user;
    private int possiblePopulation;
    private int population;
    private int growthRate;
    private int wealth;
    private int taxRate;
    private int fearRate;
    private int foodRate;
    private int popularity = 0;

    private int numberOfReligiousBuildings = 0;

    //TODO: initialize correctly
    //initialize popularity:
    {
        //TODO: initialize correctly
        popularityChange.put("religion", 0);
        popularityChange.put("tax", 0);
        popularityChange.put("fear", 0);
        popularityChange.put("foodRate", 0);
        InitializeTradables();
    }

    public Empire(User user) {
        this.user = user;
    }

    public void changeNumberOfReligiousBuildings(int change) {
        numberOfReligiousBuildings += change;
    }

    public User getUser() {
        return user;
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

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getFoodsCount(Food food) {
        return tradableAmounts.get(food);
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

    public void changeWealth(int amount) {
        //TODO JASBI: wealth or coin
        wealth += amount;
    }

    private void InitializeTradables() {
        tradableAmounts = new HashMap<>();
        for (Resource resource : Resource.values()) {
            tradableAmounts.put(resource, 50);
        }
        for (Product product : Product.values()) {
            tradableAmounts.put(product, 20);
        }
        for (Food food : Food.values()) {
            tradableAmounts.put(food, 0);
        }
    }

    public void changeTradableAmount(Tradable tradable, int amount) {
        tradableAmounts.replace(tradable, tradableAmounts.get(tradable) + amount);
    }

    public int getTradableAmount(Tradable tradable) {
        return tradableAmounts.get(tradable);
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

    public void addBuilding(Building building, int groupNumber) {
        buildings.put(building, groupNumber);
    }

    public void updateBuildings() {
        makeCapacitiesZero();
        makePossiblePopulationZero();
        for (Building building : buildings.keySet()) {
            switch (buildings.get(building)) {
                case 1:((Accommodation) building).update();
                    break;
                case 2://TODO JASBI: functions for attackingBuilding type
                    break;
                case 3://TODO JASBI: functions for other building type
                    break;
                case 4:
                    ((ProductExtractor) building).update();
                    break;
                case 5:
                    ((ProductProcessor) building).update();
                    break;
                case 6:
                    ((ResourceExtractor) building).update();
                    break;
                case 7:
                    ((ResourceProcessor) building).update();
                    break;
                case 8:((Store) building).update();
                    break;
                case 9://TODO JASBI: functions for unit creator type
                    //church and popularity
                    //dog cage
                    //draw bridge//siege tent
                    break;
            }
        }
    }

    private void makePossiblePopulationZero() {
        possiblePopulation = 0;
    }

    private void makeCapacitiesZero() {
        storage[1][0] = 0;
        storage[1][1] = 0;
        storage[1][2] = 0;
    }

    public void addPossiblePopulation(int possiblePopulation) {
        this.possiblePopulation += possiblePopulation;
    }

    public void addStorage(int capacity, int switcher) {
        storage[1][switcher] += capacity;
    }

    public void fillStorage(int switcher, int change) {
        storage[0][switcher] += change;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Empire) {
            return ((Empire) obj).user.equals(this.user);
        }
        return false;
    }

    public int getEmptySpace(int switcher) {
        return storage[1][switcher] - storage[0][switcher];
    }

    public int getNumberOfBuildingType(String buildingName) {
        int count = 0;
        for (Building building: buildings.keySet()) {
            if (building.getName().equals(buildingName)) count++;
        }
        return count;
    }

    public void affectDestructedStorerooms() {
        //TODO JASBI: complete. somehow pointive
    }

    public void affectDestructedAccommodations() {
        //TODO JASBI: complete. somehow pointive
    }

    public void buyBuilding(String buildingName) {
        changeTradableAmount(Resource.COINS,Building.getNeededResource(0, buildingName));
        changeTradableAmount(Resource.STONE,Building.getNeededResource(1, buildingName));
        changeTradableAmount(Resource.WOOD,Building.getNeededResource(2, buildingName));
        changeTradableAmount(Resource.IRON,Building.getNeededResource(3, buildingName));
    }

    public boolean canBuyBuilding(String buildingName) {
        return Building.getNeededResource(0, buildingName) <= tradableAmounts.get(Resource.COINS)
                && Building.getNeededResource(1, buildingName) <= tradableAmounts.get(Resource.STONE)
                && Building.getNeededResource(2, buildingName) <= tradableAmounts.get(Resource.WOOD)
                && Building.getNeededResource(3, buildingName) <= tradableAmounts.get(Resource.IRON);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public ArrayList<Trade> getNewTrades() {
        return newTrades;
    }

    public void setNewTrades(ArrayList<Trade> trades) {
        this.newTrades = trades;
    }

    public void addNewTrade(Trade trade) {
        newTrades.add(trade);
    }

    public ArrayList<Trade> getTradesHistory() {
        return tradesHistory;
    }

    public void setTradesHistory(ArrayList<Trade> trades) {
        this.tradesHistory = trades;
    }

    public void addTradeHistory(Trade trade) {
        tradesHistory.add(trade);
    }
}
