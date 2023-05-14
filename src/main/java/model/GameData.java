package model;

import controller.menucontrollers.GameMenuController;
import model.map.Map;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private PlayerNumber playerOfTurn = PlayerNumber.FIRST;
    private int selectedCellX;
    private int selectedCellY;


    public void addEmpire(Empire empire) {
        empires.add(empire);
    }

    public ArrayList<Empire> getEmpires() {
        return empires;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void incrementTurn() {
    }

    public PlayerNumber getPlayerOfTurn() {
        return playerOfTurn;
    }

    public void setPlayerOfTurn(PlayerNumber playerOfTurn) {
        this.playerOfTurn = playerOfTurn;
    }

    public int getSelectedCellX() {
        return selectedCellX;
    }

    public int getSelectedCellY() {
        return selectedCellY;
    }

    public int getNumberOfPlayers() {
        return empires.size();
    }

    public boolean IsUserInGame(User user) {
        for (Empire empire : empires) {
            if (user.equals(empire.getUser())) return true;
        }
        return false;
    }

    public Empire getEmpireByPlayerNumber(PlayerNumber playerNumber) {
        return empires.get(playerNumber.getNumber());
    }

    public void changePlayingPlayer() {
        int index = playerOfTurn.getNumber();
        index++;
        if (index >= empires.size()) {
            incrementTurn();
            index -= empires.size();
        }
        playerOfTurn = PlayerNumber.getPlayerByIndex(index);
        playerOfTurn.setAliveOrNot();
        if (!playerOfTurn.isAlive()) {
            changePlayingPlayer();
            return;
        }
        GameMenuController.notify("player number " + (index + 1) + " is playing");
    }

    public void setSelectedCell(int selectedCellX, int selectedCellY) {
        this.selectedCellX = selectedCellX;
        this.selectedCellY = selectedCellY;
    }
}
