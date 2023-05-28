package model;

import controller.menucontrollers.GameMenuController;
import model.gamestates.GameState;
import model.map.Map;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private PlayerNumber playerOfTurn = PlayerNumber.FIRST;
    private int selectedCellX;
    private int selectedCellY;
    private GameState gameState=GameState.VIEW_MAP;
    private int tileWidth = 20;
    private int tileHeight = 20;
    private Pair<Integer,Integer> cornerCellIndex=new Pair<>(1,1);

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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public Pair<Integer,Integer> mousePositionToCellIndex(Pair<Double,Double> mousePosition)
    {
        Pair<Integer,Integer> cellIndex=new Pair<>();
        cellIndex.first=((Double)(mousePosition.first/tileWidth)).intValue();
        cellIndex.second=((Double)(mousePosition.first/tileHeight)).intValue();

        cellIndex.first+=cornerCellIndex.first;
        cellIndex.second+=cornerCellIndex.second;

        return cellIndex;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public Pair<Integer, Integer> getCornerCellIndex() {
        return cornerCellIndex;
    }

    public void setCornerCellIndex(Pair<Integer, Integer> cornerCellIndex) {
        this.cornerCellIndex = cornerCellIndex;
    }
}
