package controller;

import model.*;
import model.buildings.Building;
import view.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenuController {
    private static final Map map;
    private static GameData gameData;

    static {
        map = gameData.getMap();
    }

    public static ArrayList<Cell> showMap(Matcher matcher) {
        return null;
    }

    public static String showPopularityFactors(Empire empire) {
        String output = "popularity factors:\n";
        output += "factor 1: religion -> " + empire.getPopularityChange("religion") + "\n";
        output += "factor 2: tax rate -> " + empire.getPopularityChange("tax") + "\n";
        output += "factor 3: fear rate -> " + empire.getPopularityChange("fear") + "\n";
        output += "factor 4: food variation -> " + empire.getPopularityChange("foodRate");
        return output;
    }

    public static String showPopularity(Empire empire) {
        return "Your popularity: " + empire.getPopularity();
    }

    public static String showFoodList(Empire empire) {
        String output = "food list:\n";
        output += "food 1 -> count: " + empire.getFoodsCount(1) + "\n";
        output += "food 2 -> count: " + empire.getFoodsCount(2) + "\n";
        output += "food 3 -> count: " + empire.getFoodsCount(3) + "\n";
        output += "food 4 -> count: " + empire.getFoodsCount(4);
        return output;
    }

    public static GameMenuMessages determinationOfFoodRate(Empire empire, int foodRate) {
        empire.setFoodRate(foodRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String foodRateShow(Empire empire) {
        return "Your food rate: " + empire.getFoodRate();
    }

    public static GameMenuMessages determinationOfTaxRate(Empire empire, int taxRate) {
        empire.setTaxRate(taxRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String TaxRateShow(Empire empire) {
        return "Your tax rate: " + empire.getTaxRate();
    }

    public static GameMenuMessages determinationOfFareRate(Empire empire, int fareRate) {
        empire.setFearRate(fareRate);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages dropBuilding(int x, int y, String buildingName, PlayerNumber playerNumber) {
        if (positionIsInvalid(x, y)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = map.getCell(x, y);
        if (!Building.isBuildingNameValid(buildingName)) {
            return GameMenuMessages.INVALID_TYPE;
        } else if (!chosenCell.isAbleToBuildOn(buildingName)) {
            return GameMenuMessages.IMPROPER_CELL_TYPE;
        } else if (chosenCell.getBuilding() != null) {
            return GameMenuMessages.FULL_CELL;
        }
        chosenCell.makeBuilding(buildingName, playerNumber);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages selectBuilding(int x, int y, Empire currentPlayerEmpire) {
        Building building;
        if (positionIsInvalid(x, y)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = map.getCell(x, y);
        if ((building = chosenCell.getBuilding()) == null) {
            return GameMenuMessages.EMPTY_CELL;
        } else if (!building.getOwnerEmpire().equals(currentPlayerEmpire)) {
            return GameMenuMessages.OTHERS_BUILDINGS;
        }
        return GameMenuMessages.SUCCESS;
    }

    private static boolean positionIsInvalid(int x, int y) {
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static void selectUnit() {
    }

    public static void trade() {
    }

    public static void showTradeList() {
    }

    public static void tradeAccept() {
    }

    public static void tradeHistory() {
    }

    public static GameData getGameData() {
        return gameData;
    }
}
