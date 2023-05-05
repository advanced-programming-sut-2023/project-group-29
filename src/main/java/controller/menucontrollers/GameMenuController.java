package controller.menucontrollers;

import model.Empire;
import model.GameData;
import model.PlayerNumber;
import model.buildings.Building;
import model.map.Cell;
import model.map.Map;
import view.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameMenuController {
    private static Map map;
    private static GameData gameData = null;

    /*static {
        map = gameData.getMap();
    }*///TODO: caused bug

    public static void setGameData(GameData gameData) {
        GameMenuController.gameData = gameData;
    }

//    public static ArrayList<Cell> showMap(Matcher matcher) {
//        return null;
//    }

    public static String showPopularityFactors(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        String output = "popularity factors:\n";
        output += "factor 1: religion -> " + empire.getPopularityChange("religion") + "\n";
        output += "factor 2: tax rate -> " + empire.getPopularityChange("tax") + "\n";
        output += "factor 3: fear rate -> " + empire.getPopularityChange("fear") + "\n";
        output += "factor 4: food variation -> " + empire.getPopularityChange("foodRate");
        return output;
    }

    public static String showPopularity(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your popularity: " + empire.getPopularity();
    }

    public static String showFoodList(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        String output = "food list:\n";
        output += "food 1 -> count: " + empire.getFoodsCount(1) + "\n";
        output += "food 2 -> count: " + empire.getFoodsCount(2) + "\n";
        output += "food 3 -> count: " + empire.getFoodsCount(3) + "\n";
        output += "food 4 -> count: " + empire.getFoodsCount(4);
        return output;
    }

    public static GameMenuMessages determinationOfFoodRate(PlayerNumber playerNumber, int foodRate) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFoodRate(foodRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String showFoodRate(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your food rate: " + empire.getFoodRate();
    }

    public static GameMenuMessages determinationOfTaxRate(PlayerNumber playerNumber, int taxRate) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setTaxRate(taxRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String showTaxRate(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your tax rate: " + empire.getTaxRate();
    }

    public static GameMenuMessages determinationOfFearRate(PlayerNumber playerNumber, int fareRate) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFearRate(fareRate);
        return GameMenuMessages.SUCCESS;
    }

    //todo moved to map menu

//    public static GameMenuMessages dropBuilding(int x, int y, String buildingName, PlayerNumber playerNumber) {
//        if (positionIsInvalid(x, y)) {
//            return GameMenuMessages.INVALID_POSITION;
//        }
//        Cell chosenCell = map.getCells()[x][y];
//        if (!Building.isBuildingNameValid(buildingName)) {
//            return GameMenuMessages.INVALID_TYPE;
//        }
//        else if (!chosenCell.isAbleToBuildOn(buildingName)) {
//            return GameMenuMessages.IMPROPER_CELL_TYPE;
//        }
//        else if (chosenCell.getBuilding() != null) {
//            return GameMenuMessages.FULL_CELL;
//        }
//        chosenCell.makeBuilding(buildingName, playerNumber);
//        return GameMenuMessages.SUCCESS;
//    }

    public static GameMenuMessages selectBuilding(int x, int y, PlayerNumber playerNumber) {
        Building building;
        Empire currentPlayerEmpire = gameData.getEmpireByPlayerNumber(playerNumber);
        if (positionIsInvalid(x, y)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = map.getCells()[x][y];
        if ((building = chosenCell.getBuilding()) == null) {
            return GameMenuMessages.EMPTY_CELL;
        }
        else if (!building.getOwnerEmpire().equals(currentPlayerEmpire)) {
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

    public static void nextTurn() {
        gameData.changePlayingPlayer();
        Empire empire = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn());
        empire.updateBuildings();
        //some functions TODO
    }
}
