package controller.menucontrollers;

import javafx.scene.paint.Color;
import model.*;
import model.map.Cell;
import model.map.CellType;
import model.map.Map;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.unitfeatures.Movable;
import model.unitfeatures.Offensive;
import model.unitfeatures.UnitState;
import model.weapons.weaponClasses.Trap;
import view.messages.SelectUnitMenuMessages;

import java.util.ArrayList;

import static controller.menucontrollers.UnitFunctions.applyAttackDamage;

public class GameController {
    private static GameData gameData = null;

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        GameController.gameData = gameData;
    }

    private static boolean positionIsInvalid(int x, int y) {
        Map map = gameData.getMap();
        return x <= 0 || y <= 0
                || x > map.getWidth()
                || y > map.getWidth();
    }

    public static void updateGameData(GameData gameData){
        GameController.gameData=gameData;
        gameData.getMapMenu().setUpAndShowMap();

        //todo check for end of game
        gameData.getGameGraphicFunctions().alertMessage(Color.YELLOW,"","it is "+gameData.getPlayerOfTurn()+"'s turn");
    }
    public static boolean nextTurn() {
        Map map = gameData.getMap();
        if (gameIsFinished()) return false;
        gameData.changePlayingPlayer();
        randomSickness(0.001);
        setNumberOfUnits(map, gameData.getPlayerOfTurn());
        updateEmpire(gameData.getPlayerOfTurn());
        //patrol apply
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects())
                    if (movingUnit instanceof Movable movable && movable.getPatrol().isPatrolling())
                        movable.getPatrol().patrol(movable, map);

        //act according to unit state
        ArrayList<Asset> trash = new ArrayList<>();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getWidth(); j++) {
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
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getWidth(); j++) {
                Cell currentCell = map.getCells()[i][j];
                if (currentCell.hasTrap()) {
                    if (currentCell.getMovingObjects().size() > 0) {
                        UnitFunctions.DamageStruct damageStruct = new UnitFunctions.DamageStruct();

                        Trap trap = currentCell.getTrap();
                        damageStruct.groundDamage = trap.getDamage();

                        applyAttackDamage(currentCell.getEnemiesOfPlayerInCell(trap.getOwnerNumber()), damageStruct, currentCell);
                        currentCell.setTrap(null);
                    }
                }
                if (currentCell.getCellType().equals(CellType.PLAIN))
                    currentCell.getMovingObjects().clear();
            }


        int fireDamage = 15;
        //apply firings
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getWidth(); j++) {
                Cell currentCell = gameData.getMap().getCells()[i][j];
                if (currentCell.getFireRemainingTurns() > 0) {
                    currentCell.decrementFireRemainingTurns();
                    for (Asset asset : currentCell.getMovingObjects())
                        asset.decreaseHp(fireDamage);
                    if (currentCell.hasTrap())
                        currentCell.getTrap().decreaseHp(fireDamage);
                    if (currentCell.hasBuilding())
                        currentCell.getBuilding().decreaseHp(fireDamage);

                    currentCell.removeDeadUnitsAndBuilding();
                }
            }


        //reset hasAttacked and hasMoved
        resetActsOfUnits();

        return true;
    }

    private static void randomSickness(double sickNessPossibility) {
        int r = (int) (1 / sickNessPossibility);
        Map map = GameController.gameData.getMap();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Cell cell = map.getCells()[i][j];
                if ((int) (Math.random() * r) % r == 0) {
                    cell.setSick(true);
                }
            }
        }
    }

    private static void applyStateForEngineerWithOil(UnitState unitState, Soldier engineerWithOil) {
        if (unitState.equals(UnitState.STANDING))
            return;

        int minimumEnemiesToAttack = unitState.equals(UnitState.DEFENSIVE) ? 3 : 1;

        for (Direction direction : Direction.values()) {
            if (UnitFunctions.oneUnitPourOil(engineerWithOil, direction, minimumEnemiesToAttack).equals(SelectUnitMenuMessages.SUCCESSFUL))
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
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
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
                    if (UnitFunctions.makeOneUnitAttack(attacker, new Pair<>(currentX + i, currentY + j))
                            .equals(SelectUnitMenuMessages.SUCCESSFUL))
                        return true;
                }
            }

        return false;
    }

    private static void resetActsOfUnits() {
        Map map = gameData.getMap();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getWidth(); j++)
                for (Asset movingUnit : map.getCells()[i][j].getMovingObjects()) {
                    if (movingUnit instanceof Movable movable)
                        movable.setMovedThisTurn(false);
                    if (movingUnit instanceof Offensive attacker)
                        attacker.setAttackedThisTurn(false);
                }
    }

    public static void notify(String message) {
        gameData.getGameGraphicFunctions().alertMessage(Color.YELLOW, "inform", message);
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
