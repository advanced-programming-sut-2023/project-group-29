package model;

import controller.menucontrollers.GameController;
import model.gamestates.GameState;
import model.map.Map;
import model.people.humanClasses.Soldier;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import view.menus.GameGraphicFunctions;
import view.menus.MapMenu;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private Map map;
    private PlayerNumber playerOfTurn = PlayerNumber.FIRST;
    private Pair<Integer, Integer> startSelectedCellsPosition = null;
    private Pair<Integer, Integer> endSelectedCellsPosition = null;
    private Pair<Integer, Integer> destinationCellPosition = null;
    private final ArrayList<Asset> allUnitsInSelectedCells = new ArrayList<>();
    private final ArrayList<Asset> selectedUnits = new ArrayList<>();
    private GameState gameState = GameState.VIEW_MAP;
    private int tileWidth = 50;
    private int tileHeight = 50;
    private Pair<Integer, Integer> cornerCellIndex = new Pair<>(1, 1);
    private MapMenu mapMenu;
    private GameGraphicFunctions gameGraphicFunctions;
    private boolean pauseMainPane = false;

    public void zoomIn() {
        if (tileWidth >= 100)
            return;

        tileWidth += 10;
        tileHeight += 10;
    }

    public void zoomOut() {
        if (tileWidth <= 50)
            return;

        tileWidth -= 10;
        tileHeight -= 10;
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

    public void setMap(Map map) {
        this.map = map;
    }

    public void incrementTurn() {
    }

    public Pair<Integer, Integer> getDestinationCellPosition() {
        return destinationCellPosition;
    }

    public void setDestinationCellPosition(Pair<Integer, Integer> destinationCellPosition) {
        this.destinationCellPosition = destinationCellPosition;
    }

    public PlayerNumber getPlayerOfTurn() {
        return playerOfTurn;
    }

    public void setPlayerOfTurn(PlayerNumber playerOfTurn) {
        this.playerOfTurn = playerOfTurn;
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
        GameController.notify("player number " + (index + 1) + " is playing");
    }

    public Pair<Integer, Integer> getEndSelectedCellsPosition() {
        return endSelectedCellsPosition;
    }

    public void setEndSelectedCellsPosition(Pair<Integer, Integer> endSelectedCellsPosition) {
        this.endSelectedCellsPosition = endSelectedCellsPosition;
    }

    public Pair<Integer, Integer> getStartSelectedCellsPosition() {
        return startSelectedCellsPosition;
    }

    public void setStartSelectedCellsPosition(Pair<Integer, Integer> startSelectedCellsPosition) {
        this.startSelectedCellsPosition = startSelectedCellsPosition;
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

    public Pair<Integer, Integer> mousePositionToCellIndex(Pair<Double, Double> mousePosition) {
        Pair<Integer, Integer> cellIndex = new Pair<>();
        cellIndex.first = ((Double) (mousePosition.first / tileWidth)).intValue();
        cellIndex.second = ((Double) (mousePosition.first / tileHeight)).intValue();

        cellIndex.first += cornerCellIndex.first;
        cellIndex.second += cornerCellIndex.second;

        return cellIndex;
    }

    public Pair<Integer, Integer> getCornerCellIndex() {
        return cornerCellIndex;
    }

    public void setCornerCellIndex(Pair<Integer, Integer> cornerCellIndex) {
        this.cornerCellIndex = cornerCellIndex;
    }

    public int getNumberOfTilesShowingInRow() {
        return 1000 / tileWidth;
    }

    public int getNumberOfTilesShowingInColumn() {
        return AppData.getScreenHeight() / tileHeight;
    }

    public ArrayList<Asset> getSelectedUnits() {
        return selectedUnits;
    }

    public MapMenu getMapMenu() {
        return mapMenu;
    }

    public void setMapMenu(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public GameGraphicFunctions getGameGraphicFunctions() {
        return gameGraphicFunctions;
    }

    public void setGameGraphicFunctions(GameGraphicFunctions gameGraphicFunctions) {
        this.gameGraphicFunctions = gameGraphicFunctions;
    }

    public boolean isPauseMainPane() {
        return pauseMainPane;
    }

    public void setPauseMainPane(boolean b) {
        pauseMainPane = b;
    }

    public ArrayList<Asset> getAllUnitsInSelectedCells() {
        return allUnitsInSelectedCells;
    }

    public int getCountOfUnitTypeOnArrayList(ArrayList<Asset> units, String typeName) {
        int count = 0;
        for (Asset asset : units) {
            if (asset instanceof Soldier soldier && soldier.getSoldierType().getName().equals(typeName))
                count++;
            else if (asset instanceof OffensiveWeapons offensiveWeapons && offensiveWeapons.getOffensiveWeaponsType().getName().equals(typeName))
                count++;
            else if (asset instanceof StaticOffensiveWeapons staticOffensiveWeapons && staticOffensiveWeapons.getStaticOffensiveWeaponsType().getName().equals(typeName))
                count++;
            else if (asset instanceof Equipments equipments && equipments.getEquipmentsType().getName().equals(typeName))
                count++;
        }

        return count;
    }

    public Empire getPlayingEmpire() {
        return getEmpireByPlayerNumber(playerOfTurn);
    }
}
