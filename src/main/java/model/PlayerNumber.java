package model;

import controller.menucontrollers.GameMenuController;

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
        Empire empire = GameMenuController.getGameData().getEmpireByPlayerNumber(this);
        isAlive = empire.getNumberOfBuildingType("mainKeep") > 0;
    }

    public void dead() {
        GameMenuController.notify("You have lost your main keep and you can't play any more!");
    }
}
