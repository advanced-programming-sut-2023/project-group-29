package model;

import controller.menucontrollers.GameController;
import model.buildings.Building;
import model.buildings.buildingClasses.*;
import model.buildings.buildingTypes.ProductProcessorType;
import model.dealing.*;
import model.map.Cell;
import model.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Empire {
    private final ArrayList<Trade> tradesHistory = new ArrayList<>();
    private final int[][] storage = new int[2][3]; //{food, productsAndResources, weapons} {0--> filled, 1--> capacity}
    private final HashMap<PopularityFactors, Integer> popularityChange = new HashMap<>();
    private final User user;
    private ArrayList<Trade> trades = new ArrayList<>();
    private ArrayList<Trade> newTrades = new ArrayList<>();
    private HashMap<Tradable, Integer> tradableAmounts;
    private int possiblePopulation;
    private int wealth = 500;
    private int taxRate = 0;
    private int fearRate = 0;
    private int foodRate = -2;
    private int popularity = 50;
    private int worklessPopulation = 20;
    private int numberOfUnits = 0;

    {
        popularityChange.put(PopularityFactors.RELIGION, 0);
        popularityChange.put(PopularityFactors.TAX, 0);
        popularityChange.put(PopularityFactors.FEAR, 0);
        popularityChange.put(PopularityFactors.FOOD, 0);
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
        popularity += popularityChange.get(PopularityFactors.RELIGION);
        popularity += popularityChange.get(PopularityFactors.TAX);
        popularity += popularityChange.get(PopularityFactors.FEAR);
        popularity += popularityChange.get(PopularityFactors.FOOD);
        if (popularity < 0) popularity = 0;
    }

    public int getPopulation() {
        return worklessPopulation + getNumberOfWorkers() + getNumberOfUnits();
    }

    private int getNumberOfWorkers() {
        int workers = 0;
        for (Building building : getBuildings()) {
            workers += building.getNumberOfWorkers();
        }
        return workers;
    }

    private int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
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

    public void changeWealth(int amount) {
        wealth += amount;
    }

    private void InitializeTradables() {
        tradableAmounts = new HashMap<>();
        for (Resource resource : Resource.values()) {
            tradableAmounts.put(resource, 50);
        }
        for (Product product : Product.values()) {
            tradableAmounts.put(product, 0);
        }
        for (Food food : Food.values()) {
            tradableAmounts.put(food, 0);
        }
        storage[0][1] = 200;
    }

    public void changeTradableAmount(Tradable tradable, int amount) {
        tradableAmounts.replace(tradable, tradableAmounts.get(tradable) + amount);
        if (tradable instanceof Food) {
            storage[0][0] += amount;
        }
        if (tradable instanceof Resource) {
            storage[0][1] += amount;
        }
        if (tradable instanceof Product product) {
            switch (product) {
                case BOW, SWORD, PIKE, ARMOUR -> storage[0][2] += amount;
                default -> storage[0][1] += amount;
            }
        }
    }

    public int getTradableAmount(Tradable tradable) {
        return tradableAmounts.get(tradable);
    }

    public int getPopularityChange(PopularityFactors cause) {
        return popularityChange.get(cause);
    }

    public void updateBuildings() {
        makeCapacitiesZero();
        makePossiblePopulationZero();
        for (Building building : getBuildings()) {
            if (building instanceof Store) building.update();
        }
        for (Building building : getBuildings()) {
            if (building instanceof Accommodation) building.update();
        }
        for (Building building : getBuildings()) {
            if (building instanceof ProductExtractor ||
                    building instanceof ResourceExtractor) building.update();
        }
        for (Building building : getBuildings()) {
            if (building instanceof ResourceProcessor) building.update();
        }
        for (Building building : getBuildings()) {
            if (building instanceof ProductProcessor productProcessor
                    && (productProcessor.getProductProcessorType().equals(ProductProcessorType.MILL)
                    || productProcessor.getProductProcessorType().equals(ProductProcessorType.BEER_BREWING))) {
                building.update();
            }
        }
        for (Building building : getBuildings()) {
            if (building instanceof ProductProcessor productProcessor
                    && (productProcessor.getProductProcessorType().equals(ProductProcessorType.BAKERY)
                    || productProcessor.getProductProcessorType().equals(ProductProcessorType.INN))) {
                building.update();
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
        for (Building building : getBuildings()) {
            if (building.getName().equals(buildingName)) count++;
        }
        return count;
    }

    private void removeEatenFood() {
        int numberOfFoodEaten = (foodRate + 2) / 2 * getPopulation();
        if (numberOfFoodEaten > storage[0][0]) {
            foodRate = -2;
            GameController.notify("Your food rate was automatically set on -2 because of lack of food!");
        }
        else {
            getRidOfFood(numberOfFoodEaten);
        }
    }

    private void commodityRemover(int[] foods, int remainedChange, int length) {
        for (int i = 0; remainedChange > 0; i++) {
            if (foods[i] != 0) {
                foods[i]--;
                remainedChange--;
            }
            if (i == length - 1) i -= length;
        }
    }

    public void affectDestructedAccommodations() {
        if (possiblePopulation < worklessPopulation) {
            worklessPopulation = possiblePopulation;
        }
    }

    public void affectDestructedStorerooms() {
        if (storage[0][0] > storage[1][0]) {
            getRidOfFood(storage[0][0] - storage[1][0]);
        }
        if (storage[0][1] > storage[1][1]) {
            getRidOfResourceOrProduct(storage[0][1] - storage[1][1]);
        }
        if (storage[0][2] > storage[1][2]) {
            getRidOfWeapon(storage[0][2] - storage[1][2]);
        }
    }

    private void getRidOfWeapon(int amount) {
        storage[0][2] -= amount;
        int[] weapons = new int[4];
        int counter = 0;
        for (Product product : Product.values()) {
            switch (product) {
                case ARMOUR, BOW, PIKE, SWORD -> {
                    weapons[counter] = tradableAmounts.get(product);
                    counter++;
                }
            }
        }
        commodityRemover(weapons, amount, 4);
        counter = 0;
        for (Product product : Product.values()) {
            switch (product) {
                case ARMOUR, BOW, PIKE, SWORD -> {
                    tradableAmounts.replace(product, weapons[counter]);
                    counter++;
                }
            }
        }
    }

    private void getRidOfResourceOrProduct(int amount) {
        storage[0][1] -= amount;
        int[] tradables = new int[9];
        int counter = 0;
        for (Resource resource : Resource.values()) {
            tradables[counter] = tradableAmounts.get(resource);
            counter++;
        }
        for (Product product : Product.values()) {
            switch (product) {
                case ARMOUR, BOW, PIKE, SWORD -> {
                    continue;
                }
            }
            tradables[counter] = tradableAmounts.get(product);
            counter++;
        }
        commodityRemover(tradables, amount, 9);
        counter = 0;
        for (Resource resource : Resource.values()) {
            tradableAmounts.replace(resource, tradables[counter]);
            counter++;
        }
        for (Product product : Product.values()) {
            switch (product) {
                case ARMOUR, BOW, PIKE, SWORD -> {
                    continue;
                }
            }
            tradableAmounts.replace(product, tradables[counter]);
            counter++;
        }
    }

    private void getRidOfFood(int amount) {
        storage[0][0] -= amount;
        int[] foods = new int[4];
        int counter = 0;
        for (Food food : Food.values()) {
            foods[counter] = tradableAmounts.get(food);
            counter++;
        }
        commodityRemover(foods, amount, 4);
        counter = 0;
        for (Food food : Food.values()) {
            tradableAmounts.replace(food, foods[counter]);
            counter++;
        }
    }

    public void buyBuilding(String buildingName) {
        changeWealth(-Building.getNeededResource(0, buildingName));
        changeTradableAmount(Resource.STONE, -Building.getNeededResource(1, buildingName));
        changeTradableAmount(Resource.WOOD, -Building.getNeededResource(2, buildingName));
        changeTradableAmount(Resource.IRON, -Building.getNeededResource(3, buildingName));
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
        changePopularityFactor(PopularityFactors.RELIGION, this.getEffectOfReligiousBuildings());
        changePopularityFactor(PopularityFactors.FOOD, foodRate * 4 + varietyOfFood - 1);
        changePopularityFactor(PopularityFactors.TAX, getChangePopularityByTaxRate(taxRate));
        changePopularityFactor(PopularityFactors.FEAR, -fearRate);
    }

    private int getEffectOfReligiousBuildings() {
        int effect = 0;
        for (Building building : getBuildings()) {
            if (building instanceof UnitCreator) {
                switch (((UnitCreator) building).getUnitCreatorType()) {
                    case CATHEDRAL, CHURCH -> effect += 2;
                }
            }
        }
        return effect;
    }

    private ArrayList<Building> getBuildings() {
        ArrayList<Building> buildings = new ArrayList<>();
        Map map = GameController.getGameData().getMap();
        for (int i = 1; i <= map.getWidth(); i++) {
            for (int j = 1; j <= map.getWidth(); j++) {
                Cell cell = map.getCells()[i][j];
                Building building = cell.getBuilding();
                if (cell.hasBuilding() && building.getOwnerEmpire().equals(this)) buildings.add(building);
            }
        }
        return buildings;
    }

    private int getVarietyOfFood() {
        int variety = 0;
        for (Food food : Food.values()) {
            if (tradableAmounts.get(food) > 0) variety++;
        }
        return variety;
    }

    private void changePopularityFactor(PopularityFactors factor, int amount) {
        popularityChange.replace(factor, amount);
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
        }
        else if (rate == 0) {
            changeByPerson = 0;
        }
        else {
            changeByPerson = (float) (rate * 0.2 + 0.4);
        }
        return (int) Math.floor(changeByPerson * getPopulation());
    }

    public int getWorklessPopulation() {
        return worklessPopulation;
    }

    public void growPopulation() {
        int growAccordingToFood, growAccordingToSpace;
        if (storage[0][0] * 2 >= getPopulation()) {
            growAccordingToFood = storage[0][0] * 2 - getPopulation();
        }
        else {
            growAccordingToFood = 0;
        }
        growAccordingToSpace = possiblePopulation - worklessPopulation;
        int growthRate = Math.min(growAccordingToFood, growAccordingToSpace);
        if (growthRate > 10) growthRate = 10;
        addPopulation(growthRate);
    }

    private void addPopulation(int growthRate) {
        worklessPopulation += growthRate;
    }

    public void changeWorklessPopulation(int change) {
        worklessPopulation += change;
    }

    public Integer getMeasureOfFactor(PopularityFactors factor) {
        return switch (factor) {
            case RELIGION -> getEffectOfReligiousBuildings();
            case FOOD -> getFoodRate();
            case FEAR -> getFearRate();
            case TAX -> getTaxRate();
        };
    }

    public enum PopularityFactors {
        RELIGION,
        TAX,
        FEAR,
        FOOD
    }
}
