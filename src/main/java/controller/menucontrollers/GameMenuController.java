package controller.menucontrollers;

import model.*;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.dealing.Food;
import model.dealing.Tradable;
import model.map.Cell;
import model.map.Map;
import model.people.humanClasses.Soldier;
import view.menus.GameMenu;
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
        output += "factor 1: religion -> " + empire.getPopularityChange(Empire.PopularityFactors.RELIGION) + "\n";
        output += "factor 2: tax rate -> " + empire.getPopularityChange(Empire.PopularityFactors.TAX) + "\n";
        output += "factor 3: fear rate -> " + empire.getPopularityChange(Empire.PopularityFactors.FEAR) + "\n";
        output += "factor 4: food variation -> " + empire.getPopularityChange(Empire.PopularityFactors.FOOD);
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
        for (Food food : Food.values()) {
            output += food.getName() + " -> count: " + empire.getFoodsCount(food) + "\n";
        }
        return output.trim();
    }

    public static GameMenuMessages determinationOfFoodRate(int foodRate) {
        if (foodRate < -2 || foodRate > 2) return GameMenuMessages.RATE_OUT_OF_RANGE;
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
        if (taxRate < -3 || taxRate > 8) return GameMenuMessages.RATE_OUT_OF_RANGE;
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

    public static GameMenuMessages determinationOfFearRate(int fearRate) {
        if (fearRate < -5 || fearRate > 5) return GameMenuMessages.RATE_OUT_OF_RANGE;
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFearRate(fearRate);
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

        gameData.setSelectedCell(xPosition, yPosition);
        SelectBuildingMenuController.setSelectedBuilding(building);
        return GameMenuMessages.SUCCESS;
    }

    private static boolean positionIsInvalid(int x, int y) {
        Map map = gameData.getMap();
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static GameMenuMessages selectUnit(int xPosition, int yPosition) {
        if (!gameData.getMap().isIndexValid(xPosition, yPosition)) {
            return GameMenuMessages.INVALID_POSITION;
        }
        Cell cell = gameData.getMap().getCells()[xPosition][yPosition];
        if (cell.getMovingObjectsOfPlayer(gameData.getPlayerOfTurn()).size() == 0) {
            return GameMenuMessages.EMPTY_CELL;
        }
        gameData.setSelectedCell(xPosition, yPosition);
        return GameMenuMessages.SUCCESS;
    }

    public static void nextTurn() {
        Map map = gameData.getMap();
        gameData.changePlayingPlayer();
        setNumberOfUnits(map, gameData.getPlayerOfTurn());
        updateEmpire(gameData.getPlayerOfTurn());
        //patrol apply
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects())
                    if (movingUnit instanceof Movable movable && movable.getPatrol().isPatrolling())
                        movable.getPatrol().patrol(movable, map);

        //reset hasAttacked and hasMoved
        resetActsOfUnits();

        //act according to unit state
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects()) {
                    if (movingUnit instanceof Offensive defensiveAttacker &&
                            defensiveAttacker.getUnitState().equals(UnitState.DEFENSIVE))
                        attackNearestUnit(defensiveAttacker, i, j);

                    else if (movingUnit instanceof Offensive offensiveAttacker &&
                            offensiveAttacker.getUnitState().equals(UnitState.OFFENSIVE))
                        moveAndAttackNearestUnit(offensiveAttacker, i, j);

                }
    }

    private static void setNumberOfUnits(Map map, PlayerNumber playerOfTurn) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerOfTurn);
        int counter = 0;
        for (int i = 1; i <= map.getWidth(); i++) {
            for (int j = 1; j <= map.getWidth(); j++) {
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects()) {
                    if (movingUnit instanceof Soldier soldier && soldier.getOwnerNumber().equals(playerOfTurn)) {
                        counter++;
                    }
                }
            }
        }
        empire.setNumberOfUnits(counter);
    }

    public static void updateEmpire(PlayerNumber playerNumber) {
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        empire.setFields();
        empire.updateBuildings();
        empire.growPopulation();
        empire.affectDestructedStorerooms();
        empire.affectDestructedAccommodations();
    }

    private static boolean moveAndAttackNearestUnit(Offensive attacker, int x, int y) {
        return true;
        //todo abbasfar attack maintain: if no enemy there still attack and say successful
    }

    private static boolean attackNearestUnit(Offensive attacker, int x, int y) {
        return true;
//        for(int i=0;i<=attacker.getAimRange();i++)
//            for(int j=0;j<=attacker.getAimRange();j++)
//            {
//                //Offensive.AttackingResult attackingResult=attacker.ca
//            }
//
//        return false;
        //todo abbasfar
    }

    private static void resetActsOfUnits() {
        Map map = gameData.getMap();
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects()) {
                    if (movingUnit instanceof Movable movable)
                        movable.setMovedThisTurn(false);
                    if (movingUnit instanceof Offensive attacker)
                        attacker.setAttackedThisTurn(false);
                }
    }

    public static void notify(String message) {
        GameMenu.print(message);
    }

    public static int showWealth() {
        return GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.gameData.getPlayerOfTurn()).getWealth();
    }

    public static String showCommodity() {
        String output = "Your recourse: \n";
        Empire empire = GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.gameData.getPlayerOfTurn());
        for (Tradable tradable : empire.getTradableAmounts().keySet()) {
            int amount = empire.getTradableAmount(tradable);
            output += tradable.getName() + ": " + amount + "\n";
        }
        return output;
    }

    public static boolean isAnyMarket() {
        Empire empire = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn());
        return empire.getNumberOfBuildingType(OtherBuildingsType.MARKET.getName()) > 0;
    }
}
