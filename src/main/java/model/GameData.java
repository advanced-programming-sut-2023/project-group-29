package model;

import model.dealing.Shop;
import model.dealing.Trade;
import model.map.Map;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Trade> trades = new ArrayList<>();
    private final ArrayList<Empire> empires = new ArrayList<>();
    //TODO: jasbi initialize empires
    //TODO jasbi:getEmpireByPlayerNumber
    private final ArrayList<User> playingUsers=new ArrayList<>();
    private Map map;
    private Shop shop;
    private int turnNumber = 1;
    private PlayerNumber playerOfTurn=PlayerNumber.FIRST;
    private final PlayerNumber ownerOfGame=PlayerNumber.FIRST;
    private int selectedCellX;
    private int selectedCellY;

    public GameData() {
        playingUsers.add(AppData.getCurrentUser());
    }

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
}
