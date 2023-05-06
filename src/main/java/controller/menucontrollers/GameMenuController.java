package controller.menucontrollers;

import model.Empire;
import model.GameData;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.StoreType;
import model.map.Cell;
import model.map.Map;
import view.messages.GameMenuMessages;

public class GameMenuController {
    private static GameData gameData = null;

    public static void setGameData(GameData gameData) {
        GameMenuController.gameData = gameData;
    }


    public static GameData getGameData() {
        return gameData;
    }

    public static String showPopularityFactors() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        String output = "popularity factors:\n";
        output += "factor 1: religion -> " + empire.getPopularityChange("religion") + "\n";
        output += "factor 2: tax rate -> " + empire.getPopularityChange("tax") + "\n";
        output += "factor 3: fear rate -> " + empire.getPopularityChange("fear") + "\n";
        output += "factor 4: food variation -> " + empire.getPopularityChange("foodRate");
        return output;
    }

    public static String showPopularity() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your popularity: " + empire.getPopularity();
    }

    public static String showFoodList() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        String output = "food list:\n";
        output += "food 1 -> count: " + empire.getFoodsCount(1) + "\n";
        output += "food 2 -> count: " + empire.getFoodsCount(2) + "\n";
        output += "food 3 -> count: " + empire.getFoodsCount(3) + "\n";
        output += "food 4 -> count: " + empire.getFoodsCount(4);
        return output;
    }

    public static GameMenuMessages determinationOfFoodRate(int foodRate) {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFoodRate(foodRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String showFoodRate() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your food rate: " + empire.getFoodRate();
    }

    public static GameMenuMessages determinationOfTaxRate(int taxRate) {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setTaxRate(taxRate);
        return GameMenuMessages.SUCCESS;
    }

    public static String showTaxRate() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        return "Your tax rate: " + empire.getTaxRate();
    }

    public static GameMenuMessages determinationOfFearRate(int fareRate) {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFearRate(fareRate);
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages selectBuilding(int xPosition, int yPosition) {
        Building building;
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire currentPlayerEmpire = gameData.getEmpireByPlayerNumber(playerNumber);
        if (positionIsInvalid(xPosition, yPosition)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = gameData.getMap().getCells()[xPosition][yPosition];
        if ((building = chosenCell.getBuilding()) == null) {
            return GameMenuMessages.EMPTY_CELL;
        } else if (!building.getOwnerEmpire().equals(currentPlayerEmpire)) {
            return GameMenuMessages.OTHERS_BUILDINGS;
        }

        gameData.setSelectedCell(xPosition,yPosition);
        SelectBuildingMenuController.setSelectedBuilding(building);
        return GameMenuMessages.SUCCESS;
    }

    private static boolean positionIsInvalid(int x, int y) {
        Map map = gameData.getMap();
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static GameMenuMessages selectUnit(int xPosition,int yPosition) {

        //todo abbasfar error handle
        gameData.setSelectedCell(xPosition,yPosition);
        return GameMenuMessages.SUCCESS;
    }

    public static void nextTurn() {
        gameData.changePlayingPlayer();
        Empire empire = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn());
        empire.updateBuildings();
        empire.affectDestructedStorerooms();
        empire.affectDestructedAccommodations();
        //TODO JASBI: some functions. This function should be called in the beginning of the game
    }
}
