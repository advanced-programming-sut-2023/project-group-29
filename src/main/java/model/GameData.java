package model;

import model.dealing.Shop;
import model.dealing.Trade;
import model.map.Map;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Trade> trades = new ArrayList<>();
    private final ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private Shop shop;
    private int turnNumber = 1;
    private PlayerNumber playerOfTurn = PlayerNumber.FIRST;
    private final PlayerNumber ownerOfGame = PlayerNumber.FIRST;
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

    public Shop getShop() {
        return shop;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void incrementTurn() {
        turnNumber++;
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

    public void setSelectedCellX(int selectedCellX) {
        this.selectedCellX = selectedCellX;
    }

    public int getSelectedCellY() {
        return selectedCellY;
    }

    public void setSelectedCellY(int selectedCellY) {
        this.selectedCellY = selectedCellY;
    }

    public int getNumberOfPlayers() {
        return empires.size();
    }

    public void setMap(Map map) {
        this.map = map;
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
        //TODO: game finish
        if (turnNumber > 5) System.out.println("finish");
        playerOfTurn = PlayerNumber.getPlayerByIndex(index);
        System.out.println("player number " + (index+1) + " is playing");
    }
}
