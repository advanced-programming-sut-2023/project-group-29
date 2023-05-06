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

    public static GameMenuMessages dropBuilding(int x, int y, String buildingName, boolean isAdmin) {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Building building;
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        if (positionIsInvalid(x, y)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        if (buildingName.equals("mainKeep")) {
            return GameMenuMessages.TWO_MAIN_KEEP;
        } else if (!Building.isBuildingNameValid(buildingName)) {
            return GameMenuMessages.INVALID_TYPE;
        } else if (!chosenCell.isAbleToBuildOn(buildingName)) {
            return GameMenuMessages.IMPROPER_CELL_TYPE;
        } else if ((building = chosenCell.getBuilding()) != null) {
            return GameMenuMessages.FULL_CELL;
        } else if (buildingTypeIsStore(buildingName)
                && IsAnotherStore(empire, buildingName)
                && !isConnectedToOthers(x, y, buildingName, empire)) {
            return GameMenuMessages.UNCONNECTED_STOREROOMS;
        } /*else if (!empire.canBuyBuilding(buildingName)) {
            return GameMenuMessages.LACK_OF_RESOURCES;
        }*/
        if (!isAdmin) empire.buyBuilding(building);
        chosenCell.makeBuilding(buildingName, playerNumber);
        return GameMenuMessages.SUCCESS;
    }

    private static boolean IsAnotherStore(Empire empire, String buildingName) {
        return empire.getNumberOfBuildingType(buildingName) > 0;
    }

    private static boolean buildingTypeIsStore(String buildingName) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.getName().equals(buildingName)) return true;
        }
        return false;
    }

    private static boolean isConnectedToOthers(int x, int y, String buildingName, Empire empire) {
        return thisTypeIsInThisCell(x - 1, y, buildingName, empire)
                || thisTypeIsInThisCell(x + 1, y, buildingName, empire)
                || thisTypeIsInThisCell(x, y - 1, buildingName, empire)
                || thisTypeIsInThisCell(x, y + 1, buildingName, empire);
    }

    private static boolean thisTypeIsInThisCell(int x, int y, String buildingName, Empire empire) {
        if (positionIsInvalid(x, y)) return false;
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        if (chosenCell.getBuilding() == null) {
            return false;
        } else {
            return chosenCell.getBuilding().getName().equals(buildingName)
                    && chosenCell.getBuilding().getOwnerEmpire().equals(empire);
        }
    }

    public static GameMenuMessages selectBuilding(int x, int y) {
        Building building;
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire currentPlayerEmpire = gameData.getEmpireByPlayerNumber(playerNumber);
        if (positionIsInvalid(x, y)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        if ((building = chosenCell.getBuilding()) == null) {
            return GameMenuMessages.EMPTY_CELL;
        } else if (!building.getOwnerEmpire().equals(currentPlayerEmpire)) {
            return GameMenuMessages.OTHERS_BUILDINGS;
        }
        return GameMenuMessages.SUCCESS;
    }

    private static boolean positionIsInvalid(int x, int y) {
        Map map = gameData.getMap();
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static void selectUnit() {
    }


    public static void nextTurn() {
        gameData.changePlayingPlayer();
        Empire empire = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn());
        empire.updateBuildings();
        empire.affectDestructedStorerooms();
        empire.affectDestructedAccommodations();
        //TODO: some functions. This function should be called in the beginning of the game
    }
}
