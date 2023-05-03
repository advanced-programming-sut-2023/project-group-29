package model;

import model.buildings.Building;
import model.buildings.buildingClasses.ProductExtractor;
import model.buildings.buildingClasses.ProductProcessor;
import model.buildings.buildingClasses.ResourceExtractor;
import model.buildings.buildingClasses.ResourceProcessor;
import model.dealing.Product;
import model.dealing.Resource;
import model.people.humanClasses.Soldier;
import model.people.humanClasses.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Empire {
    private final ArrayList<Soldier> soldiers = new ArrayList<>();
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final HashMap<Building, Integer> buildings = new HashMap<>();//TODO expand this to its children
    private final int[] foods = new int[4];
    private final HashMap<String, Integer> popularityChange = new HashMap<>();
    private HashMap<Resource, Integer> resourceAmounts;
    private HashMap<Product, Integer> productAmounts;
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
        InitializeResourceAndProduct();
    }
    public void changeNumberOfReligiousBuildings(int change) {
        numberOfReligiousBuildings += change;
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

    public int getFoodsCount(int type) {
        return foods[type - 1];
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
        wealth += amount;
    }

    private void InitializeResourceAndProduct() {
        resourceAmounts = new HashMap<>();
        for (Resource resource : Resource.values()) {
            resourceAmounts.put(resource, 0);
            //TODO: initialize correctly!
        }
        productAmounts = new HashMap<>();
        for (Product product : Product.values()) {
            productAmounts.put(product, 0);
        }
    }

    public void changeResourceAmount(Resource resource, int amount) {
        resourceAmounts.replace(resource, resourceAmounts.get(resource) + amount);
    }

    public int getResourceAmount(Resource resource) {
        return resourceAmounts.get(resource);
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

    public void addBuilding(Building building, int groupNumber) {
        buildings.put(building, groupNumber);
    }

    public void updateBuildings() {
        for (Building building: buildings.keySet()) {
            switch (buildings.get(building)) {
                case 1://TODO: functions for accommodation type
                case 2://TODO: functions for attackingBuilding type
                case 3://TODO: functions for other building type
                case 4: ((ProductExtractor)building).update(this);
                case 5:((ProductProcessor)building).update(this);
                case 6:((ResourceExtractor)building).update(this);
                case 7:((ResourceProcessor)building).update(this);
                case 8://TODO: functions for store type --> {
                    //church and popularity
                    //dog cage
                    //draw building//siege tent
                case 9://TODO: functions for unit creator type
            }
        }
    }

    public void changeProduct(Product product, int amount) {
        productAmounts.replace(product, productAmounts.get(product) + amount);
    }

    public int getProductAmount(Product product) {
        return productAmounts.get(product);
    }

    //TODO: modify equal function
}
