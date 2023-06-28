package model;

import controller.menucontrollers.GameController;
import model.map.Cell;
import model.map.Map;

public enum PlayerNumber {
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FORTH(3),
    FIFTH(4),
    SIXTH(5),
    SEVENTH(6),
    EIGHTH(7);

    private final int number;
    private boolean isAlive;

    PlayerNumber(int number) {
        this.number = number;
    }

    public static PlayerNumber getPlayerByIndex(int index) {
        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            if (playerNumber.number == index) return playerNumber;
        }
        return null;
    }

    public int getNumber() {
        return number;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setAliveOrNot() {
        Empire empire = GameController.getGameData().getEmpireByPlayerNumber(this);
        if (empire.getNumberOfBuildingType("mainKeep") > 0) isAlive = true;
        else {
            isAlive = false;
            dead();
        }
    }

    public void dead() {
        GameController.notify("Player number" + this.number + "\n" +
                "You have lost your main keep and you can't play any more!");
        Map map = GameController.getGameData().getMap();
        Empire deadEmpire = GameController.getGameData().getEmpireByPlayerNumber(this);
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Cell cell = map.getCells()[i][j];
                if (cell.hasBuilding() && cell.getBuilding().getOwnerEmpire().equals(deadEmpire)) {
                    cell.setBuilding(null);
                }
                if (cell.hasTrap() && cell.getTrap().getOwnerEmpire().equals(deadEmpire)) {
                    cell.setTrap(null);
                }
                cell.getMovingObjects().removeIf(asset -> asset.getOwnerNumber().equals(this));

            }
        }
    }
}
