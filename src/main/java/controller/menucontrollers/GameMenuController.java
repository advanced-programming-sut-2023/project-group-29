package controller.menucontrollers;

import model.*;
import model.buildings.Building;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.dealing.Food;
import model.dealing.Tradable;
import model.map.Cell;
import model.map.CellType;
import model.map.Map;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.weapons.weaponClasses.Trap;
import view.menus.GameMenu;
import view.messages.GameMenuMessages;
import view.messages.SelectUnitMenuMessages;

import java.util.ArrayList;
import java.util.List;

import static controller.menucontrollers.SelectUnitMenuController.applyAttackDamage;

public class GameMenuController {
    private static GameData gameData = null;

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        GameMenuController.gameData = gameData;
    }

    public static String showPopularityFactors() {
        PlayerNumber playerNumber = gameData.getPlayerOfTurn();
        Empire empire = gameData.getEmpireByPlayerNumber(playerNumber);
        String output = "popularity factors:\n";
        output += "factor 1: religion -> " + empire.getPopularityChange(Empire.PopularityFactors.RELIGION) + "\n";
        output += "factor 2: tax rate -> " + empire.getPopularityChange(Empire.PopularityFactors.TAX) + "\n";
        output += "factor 3: fear rate -> " + empire.getPopularityChange(Empire.PopularityFactors.FEAR) + "\n";
        output += "factor 4: food rate -> " + empire.getPopularityChange(Empire.PopularityFactors.FOOD);
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
        }
        else if (!building.getOwnerEmpire().equals(currentPlayerEmpire)) {
            return GameMenuMessages.OTHERS_BUILDINGS;
        }

        gameData.setStartSelectedCellsPosition(new Pair<Integer,Integer>(xPosition, yPosition));
        SelectBuildingMenuController.setSelectedBuilding(building);
        return GameMenuMessages.SUCCESS;
    }

    private static boolean positionIsInvalid(int x, int y) {
        Map map = gameData.getMap();
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static boolean nextTurn() {
        Map map = gameData.getMap();
        if (gameIsFinished()) return false;
        gameData.changePlayingPlayer();
        setNumberOfUnits(map, gameData.getPlayerOfTurn());
        updateEmpire(gameData.getPlayerOfTurn());

        //patrol apply
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects())
                    if (movingUnit instanceof Movable movable && movable.getPatrol().isPatrolling())
                        movable.getPatrol().patrol(movable, map);

        //act according to unit state
        ArrayList<Asset> trash = new ArrayList<>();
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++) {
                trash.clear();
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects()) {
                    if (movingUnit instanceof Soldier soldier && soldier.getSoldierType().equals(SoldierType.ENGINEER_WITH_OIL)) {
                        applyStateForEngineerWithOil(soldier.getUnitState(), soldier);
                        continue;
                    }

                    if (movingUnit instanceof Offensive defensiveAttacker &&
                            defensiveAttacker.getUnitState().equals(UnitState.DEFENSIVE))
                        attackNearestUnit(defensiveAttacker, i, j);

                    else if (movingUnit instanceof Offensive offensiveAttacker &&
                            offensiveAttacker.getUnitState().equals(UnitState.OFFENSIVE))
                        moveAndAttackNearestUnit(offensiveAttacker, i, j, trash);

                }
                map.getCells()[i][j].getMovingObjects().removeAll(trash);
            }

        //trap and plain check
        for (int i = 1; i <= map.getWidth(); i++)
            for (int j = 1; j <= map.getWidth(); j++) {
                Cell currentCell = map.getCells()[i][j];
                if (currentCell.hasTrap()) {
                    if (currentCell.getMovingObjects().size() > 0) {
                        SelectUnitMenuController.DamageStruct damageStruct = new SelectUnitMenuController.DamageStruct();

                        Trap trap = currentCell.getTrap();
                        damageStruct.groundDamage = trap.getDamage();

                        applyAttackDamage(currentCell.getEnemiesOfPlayerInCell(trap.getOwnerNumber()), damageStruct, currentCell);
                        currentCell.setTrap(null);
                    }
                }
                if (currentCell.getCellType().equals(CellType.PLAIN))
                    currentCell.getMovingObjects().clear();
            }

        //reset hasAttacked and hasMoved
        resetActsOfUnits();

        return true;
    }

    private static void applyStateForEngineerWithOil(UnitState unitState, Soldier engineerWithOil) {
        if (unitState.equals(UnitState.STANDING))
            return;

        int minimumEnemiesToAttack = unitState.equals(UnitState.DEFENSIVE) ? 3 : 1;

        for (Direction direction : Direction.values()) {
            if (SelectUnitMenuController.oneUnitPourOil(engineerWithOil, direction, minimumEnemiesToAttack).equals(SelectUnitMenuMessages.SUCCESSFUL))
                return;
        }
    }

    private static boolean gameIsFinished() {
        int numberOfDeadPlayers = 0;
        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            if (!playerNumber.isAlive()) numberOfDeadPlayers++;
        }
        return numberOfDeadPlayers + 1 == gameData.getNumberOfPlayers();
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

    private static void moveAndAttackNearestUnit(Offensive attacker, int currentX, int currentY, ArrayList<Asset> trash) {
        if (!(attacker instanceof Movable movableAttacker)) {
            attackNearestUnit(attacker, currentX, currentY);
            return;
        }

        Asset attackerAsset = (Asset) attacker;
        for (int i = -movableAttacker.getSpeed(); i <= movableAttacker.getSpeed(); i++)
            for (int j = -movableAttacker.getSpeed(); j <= movableAttacker.getSpeed(); j++) {
                int destinationX = currentX + i;
                int destinationY = currentY + j;
                if (movableAttacker.checkForMoveErrors
                        (gameData.getMap(), destinationX, destinationY).equals(Movable.MovingResult.SUCCESSFUL)) {
                    if (i == 0 && j == 0) {
                        if (attackNearestUnit(attacker, currentX, currentY))
                            return;
                        continue;
                    }
                    attackerAsset.setPositionX(destinationX);
                    attackerAsset.setPositionY(destinationY);
                    gameData.getMap().getCells()[destinationX][destinationY].getMovingObjects().add(attackerAsset);

                    if (attackNearestUnit(attacker, currentX, currentY)) {
                        trash.add(attackerAsset);
                        return;
                    }

                    //failed to attack on that cell
                    attackerAsset.setPositionX(currentX);
                    attackerAsset.setPositionY(currentY);
                    gameData.getMap().getCells()[destinationX][destinationY].getMovingObjects().remove(attackerAsset);
                }
            }
    }

    private static boolean attackNearestUnit(Offensive attacker, int currentX, int currentY) {
        for (int i = -attacker.getAimRange(); i <= attacker.getAimRange(); i++)
            for (int j = -attacker.getAimRange(); j <= attacker.getAimRange(); j++) {
                Offensive.AttackingResult attackingResult = attacker.canAttack
                        (gameData.getMap(), currentX + i, currentY + j, false);
                if (attackingResult.equals(Offensive.AttackingResult.SUCCESSFUL)) {
                    if (SelectUnitMenuController.makeOneUnitAttack(attacker,new Pair<>(currentX + i, currentY + j))
                            .equals(SelectUnitMenuMessages.SUCCESSFUL))
                        return true;
                }
            }

        return false;
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
        return GameMenuController.getGameData().getEmpireByPlayerNumber
                (GameMenuController.gameData.getPlayerOfTurn()).getWealth();
    }

    public static String showCommodity() {
        String output = "Your recourse: \n";
        Empire empire = GameMenuController.getGameData().getEmpireByPlayerNumber
                (GameMenuController.gameData.getPlayerOfTurn());
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

    public static void showWinner() {
        PlayerNumber winner = null;
        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            if (playerNumber.getNumber() > gameData.getNumberOfPlayers()) break;
            if (playerNumber.isAlive()) winner = playerNumber;
        }
        Empire empire = gameData.getEmpireByPlayerNumber(winner);
        User winnerUser = empire.getUser();
        winnerUser.setHighScore(empire.getWealth() + empire.getPopularity());
        notify("The player number " + (winner.getNumber() + 1) + "won the game!");
    }
}
