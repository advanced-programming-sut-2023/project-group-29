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

    public static String showPopularityFactors() {
        return null;
    }

    public static int showPopularity() {
        return 0;
    }

    public static String showFoodList() {
        return null;
    }

    public static String determinationOfFoodRate(Matcher matcher) {
        return null;
    }

    public static String foodRateShow() {
        return null;
    }

    public static String determinationOfTaxRate(Matcher matcher) {
        return null;
    }

    public static String TaxRateShow() {
        return null;
    }

    public static String determinationOfFareRate(Matcher matcher) {
        return null;
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
