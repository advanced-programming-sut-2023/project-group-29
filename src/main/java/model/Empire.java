package model;

import controller.menucontrollers.GameMenuController;
import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.buildings.buildingTypes.UnitCreatorType;
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
    private final HashMap<Building, Integer> buildings = new HashMap<>();
    private int[][] storage = new int[2][3]; //{food, productsAndResources, weapons} {0--> filled, 1--> capacity}
    private final HashMap<String, Integer> popularityChange = new HashMap<>();
    private HashMap<Tradable, Integer> tradableAmounts;
    private User user;
    private int possiblePopulation;
    private int population = 20;
    private int wealth = 500;
    private int taxRate = 0;
    private int fearRate = 0;
    private int foodRate = -2;
    private int popularity = 50;
    private int worklessPopulation = 20; //TODO jasbi we should be aware of it when a troop is killed!!!!

    {
        popularityChange.put("religion", 0);
        popularityChange.put("tax", 0);
        popularityChange.put("fear", 0);
        popularityChange.put("food", 0);
        InitializeTradables();
    }

    public Empire(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity() {
        popularity += popularityChange.get("religion");
        popularity += popularityChange.get("tax");
        popularity += popularityChange.get("fear");
        popularity += popularityChange.get("food");
        if (popularity < 0) popularity = 0;
    }

    public int getPopulation() {
        return population;
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

    public void addBuilding(Building building, int groupNumber) {
        buildings.put(building, groupNumber);
    }

    public void updateBuildings() {
        makeCapacitiesZero();
        makePossiblePopulationZero();
        for (Building building : buildings.keySet()) {
            switch (buildings.get(building)) {
                case 1 -> ((Accommodation) building).update();
                case 3 -> ((OtherBuildings) building).update();
                case 4 -> ((ProductExtractor) building).update();
                case 5 -> ((ProductProcessor) building).update();
                case 6 -> ((ResourceExtractor) building).update();
                case 7 -> ((ResourceProcessor) building).update();
                case 8 -> ((Store) building).update();
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
        for (Building building : buildings.keySet()) {
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
        changeWealth(Building.getNeededResource(0, buildingName));
        changeTradableAmount(Resource.STONE, Building.getNeededResource(1, buildingName));
        changeTradableAmount(Resource.WOOD, Building.getNeededResource(2, buildingName));
        changeTradableAmount(Resource.IRON, Building.getNeededResource(3, buildingName));
    }

    public boolean canBuyBuilding(String buildingName) {
        return Building.getNeededResource(0, buildingName) <= wealth
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

    public HashMap<Tradable, Integer> getTradableAmounts() {
        return tradableAmounts;
    }

    public void setFields() {
        setPopularityFactors();
        setPopularity();
    }

    private void setPopularityFactors() {
        int varietyOfFood = getVarietyOfFood();
        wealth += getChangeWealthByTaxRate(taxRate);
        removeEatenFood();
        changePopularityFactor("religion", this.getEffectOfReligiousBuildings());
        changePopularityFactor("food", foodRate * 4 + varietyOfFood - 1);
        changePopularityFactor("tax", getChangePopularityByTaxRate(taxRate));
        changePopularityFactor("fear", -fearRate);
    }

    private int getEffectOfReligiousBuildings() {
        int effect = 0;
        for (Building building : buildings.keySet()) {
            if (buildings.get(building) == 9) {
                switch (((UnitCreator) building).getUnitCreatorType()) {
                    case CATHEDRAL,CHURCH -> effect += 2;
                }
            }
        }
        return effect;
    }

    private int getVarietyOfFood() {
        int variety = 0;
        for (Food food : Food.values()) {
            if (tradableAmounts.get(food) > 0) variety++;
        }
        return variety;
    }

    private void removeEatenFood() {
        int numberOfFoodEaten = (int) ((foodRate + 2) / 2 * population);
        if (numberOfFoodEaten > storage[0][0]) {
            foodRate = -2;
            GameMenuController.notify("Your food rate was automatically set on -2 because of lack of food!");
        } else {
            storage[0][0] -= numberOfFoodEaten;
            int foods[] = new int[4];
            int counter = 0;
            for (Food food : Food.values()) {
                foods[counter] = tradableAmounts.get(food);
                counter++;
            }
            foodRemover(foods, numberOfFoodEaten);
            counter = 0;
            for (Food food : Food.values()) {
                tradableAmounts.replace(food, foods[counter]);
                counter++;
            }
        }
    }

    private void foodRemover(int[] foods, int remainedChange) {
        for (int i = 0; remainedChange > 0; i++) {
            if (foods[i] != 0) {
                foods[i]--;
                remainedChange--;
            }
            if (i == 3) i -= 4;
        }
    }

    private void changePopularityFactor(String factor, int change) {
        popularityChange.replace(factor, popularityChange.get(factor) + change);
    }

    private int getChangePopularityByTaxRate(int rate) {
        int change = -rate * 2;
        if (rate <= 0) change++;
        if (rate > 4) change -= (rate - 4) * 2;
        return change;
    }

    private int getChangeWealthByTaxRate(int rate) {
        float changeByPerson;
        if (rate < 0) {
            changeByPerson = (float) (rate * 0.2 - 0.4);
        } else if (rate == 0) {
            changeByPerson = 0;
        } else {
            changeByPerson = (float) (rate * 0.2 + 0.4);
        }
        return (int) Math.floor(changeByPerson * population);
    }

    public int getWorklessPopulation() {
        return worklessPopulation;
    }

    public void growPopulation() {
        int growAccordingToFood, growAccordingToSpace;
        if (storage[0][0] * 2 >= population) {
            growAccordingToFood = storage[0][0] * 2 - population;
        } else {
            growAccordingToFood = 0;
        }
        growAccordingToSpace = possiblePopulation - population;
        int growthRate = Math.min(growAccordingToFood, growAccordingToSpace);
        if (growthRate > 10) growthRate = 10;
        addPopulation(growthRate);
    }

    private void addPopulation(int growthRate) {
        population += growthRate;
        worklessPopulation += growthRate;
    }
}
