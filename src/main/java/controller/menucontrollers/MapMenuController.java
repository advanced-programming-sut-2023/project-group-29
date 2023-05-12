package controller.menucontrollers;

import model.Asset;
import model.Empire;
import model.GameData;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.AccommodationType;
import model.buildings.buildingTypes.StoreType;
import model.map.*;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.weapons.Weapon;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import view.messages.MapMenuMessages;

import java.util.ArrayList;

public class MapMenuController {
    private static final int tileWidth = 6;
    private static final int tileHeight = 4;
    private static final int maxNumberOfTilesShowingInRow = 5; //should be 20
    private static final int maxNumberOfTilesShowingInColumn = 2; //should be 8
    private static int showingMapIndexX = 1;
    private static int showingMapIndexY = 1;

    public static String showDetails(int indexX, int indexY) {
        Map map = GameMenuController.getGameData().getMap();
        PlayerNumber currentPlayer = GameMenuController.getGameData().getPlayerOfTurn();

        if (!map.isIndexValid(indexX, indexY))
            return "Index is invalid!\n";

        Cell showingCell = map.getCells()[indexX][indexY];
        String output = "";

        output += "Building: ";
        output += showingCell.hasBuilding() ? showingCell.getBuilding().getName() : "";
        output += "\n";

        output += "Trap: ";
        output += (showingCell.hasTrap() && showingCell.getTrap().getOwnerNumber().equals(currentPlayer)) ? showingCell.getTrap().getName() : "";
        output += "\n";

        output += showingCell.hasTunnel() ? "There is a tunnel under this cell." : "There are no tunnels under this cell.";
        output += "\n";

        output += "Units and Weapons: \n";
        for (Asset asset : showingCell.getMovingObjects()) {
            output += "    ";
            if (asset instanceof Soldier soldier) {
                output += "name: " + soldier.getName() + ". owner: " + soldier.getOwnerNumber() + " PLAYER. " + "hp: " + soldier.getHp() + ". ";
                output += soldier.hasAttackedThisTurn() ? " attacked. " : "not attacked. ";
                output += soldier.hasMovedThisTurn() ? " moved. " : "not moved. ";
            } else if (asset instanceof Weapon weapon) {
                output += "name: " + weapon.getName() + ". owner: " + weapon.getOwnerNumber() + " PLAYER. " + "hp: " + weapon.getHp() + ". ";

                if (weapon instanceof Equipments equipments) {
                    output += equipments.hasMovedThisTurn() ? " moved. " : "not moved. ";
                }
                if (weapon instanceof OffensiveWeapons offensiveWeapons) {
                    output += offensiveWeapons.hasAttackedThisTurn() ? " attacked. " : "not attacked. ";
                    output += offensiveWeapons.hasMovedThisTurn() ? " moved. " : "not moved. ";

                }
                if (weapon instanceof StaticOffensiveWeapons staticOffensiveWeapons) {
                    output += staticOffensiveWeapons.hasAttackedThisTurn() ? " attacked. " : "not attacked. ";
                }
            }
            output += "\n";
        }

        return output;
    }

    public static MapMenuMessages setShowingMapIndexes(int indexX, int indexY) {
        Map map = GameMenuController.getGameData().getMap();
        if (!map.isIndexValid(indexX, indexY))
            return MapMenuMessages.INVALID_INDEX;

        showingMapIndexX = indexX;
        showingMapIndexY = indexY;

        return MapMenuMessages.SUCCESSFUL;
    }

    public static String showMap(int indexX, int indexY) {
        Map map = GameMenuController.getGameData().getMap();

        MapMenuMessages message = setShowingMapIndexes(indexX, indexY);
        if (message.equals(MapMenuMessages.INVALID_INDEX))
            return "Invalid index!";

        //the first line is the building and trap(if is for user)
        //the second,third and forth line shows other units

        int mapShowingWidth = Math.min(maxNumberOfTilesShowingInRow, map.getWidth() - showingMapIndexX + 1);
        int mapShowingHeight = Math.min(maxNumberOfTilesShowingInColumn, map.getWidth() - showingMapIndexY + 1);

        ArrayList<String>[][] tiles = new ArrayList[mapShowingWidth][mapShowingHeight];
        for (int i = 0; i < mapShowingWidth; i++)
            for (int j = 0; j < mapShowingHeight; j++)
                tiles[i][j] = createTileInMap(map.getCells()[i + showingMapIndexX][j + showingMapIndexY]);

        return showTilesOfMap(tiles, mapShowingWidth, mapShowingHeight);
    }

    public static String showMap() {
        return showMap(showingMapIndexX, showingMapIndexY);
    }


    private static String showTilesOfMap(ArrayList<String>[][] tiles, int mapShowingWidth, int mapShowingHeight) {
        String tilesOfMap = "";
        for (int i = 0; i < mapShowingHeight; i++) {
            tilesOfMap += "-".repeat(mapShowingWidth * (tileWidth + 1) + 1);
            tilesOfMap += "\n";

            for (int j = 0; j < tileHeight; j++) {
                for (int k = 0; k < mapShowingWidth; k++) {

                    tilesOfMap += "|";
                    tilesOfMap += tiles[k][i].get(j);
                }
                tilesOfMap += "|\n";
            }
        }
        tilesOfMap += "-".repeat(mapShowingWidth * (tileWidth + 1) + 1);

        return tilesOfMap;
    }

    private static ArrayList<String> createTileInMap(Cell cell) {
        ArrayList<String> tile = new ArrayList<>();

        //building or trap
        String showingSignOfBuilding = "";
        if (cell.hasBuilding())
            showingSignOfBuilding = cell.getBuilding().getShowingSignInMap();
        else if (cell.hasTrap())
            showingSignOfBuilding = cell.getTrap().getShowingSignInMap();

        showingSignOfBuilding = fitStringToTileWidthWithNumberSign(showingSignOfBuilding);
        showingSignOfBuilding = colorString(showingSignOfBuilding, cell.getShowingColor());
        tile.add(showingSignOfBuilding);

        //other units
        for (int i = 0; i < tileHeight - 1; i++) {
            String showingSignOfOtherUnits = "";
            if (i < cell.getMovingObjects().size())
                showingSignOfOtherUnits = cell.getMovingObjects().get(i).getShowingSignInMap();

            showingSignOfOtherUnits = fitStringToTileWidthWithNumberSign(showingSignOfOtherUnits);
            showingSignOfOtherUnits = colorString(showingSignOfOtherUnits, cell.getShowingColor());
            tile.add(showingSignOfOtherUnits);
        }

        //todo pointive: show number of owner on map
        return tile;
    }

    private static String colorString(String string, ConsoleColors color) {
        return color.getStringCode() + string + ConsoleColors.RESET_COLOR.getStringCode();
    }

    private static String fitStringToTileWidthWithNumberSign(String string) {
        if (string.length() > tileWidth)
            return string.substring(0, tileWidth);

        string += "#".repeat(tileWidth - string.length());
        return string;
    }

    public static String moveMap(int upMovements, int rightMovements, int downMovements, int leftMovements) {
        Map map = GameMenuController.getGameData().getMap();

        int newShowingMapIndexX = showingMapIndexX + rightMovements - leftMovements;
        int newShowingMapIndexY = showingMapIndexY + downMovements - upMovements;

        if (!map.isIndexValid(newShowingMapIndexX, newShowingMapIndexY))
            return "The index is invalid";

        return showMap(newShowingMapIndexX, newShowingMapIndexY);
    }

    public static void setBlockTexture(CellType cellType, int x, int y) {
    }

    public static void setPartOfBlockTexture(CellType cellType, int x1, int y1, int x2, int y2) {
    }

    public static void clear(int xPosition, int yPosition) {
    }

    public static void dropRock(int xPosition, int yPosition, String direction) {
    }

    public static void dropTree(int xPosition, int yPosition, TreeType treeType) {
    }


    public static MapMenuMessages dropUnit(int positionX, int positionY, String type, int count, int ownerPlayerNumberInt) {

        Map map = GameMenuController.getGameData().getMap();

        PlayerNumber ownerPlayerNumber = PlayerNumber.getPlayerByIndex(ownerPlayerNumberInt - 1);
        Human unit = Human.createUnitByName(type, ownerPlayerNumber, positionX, positionY);

        if (unit == null)
            return MapMenuMessages.INVALID_UNIT_NAME;
        if (!map.isIndexValid(positionX, positionY))
            return MapMenuMessages.INVALID_INDEX;

        for (int i = 0; i < count; i++) {
            Human addingUnit = Human.createUnitByName(type, ownerPlayerNumber, positionX, positionY);
            map.getCells()[positionX][positionY].addMovingObject(addingUnit);
        }

        System.out.println();

        return MapMenuMessages.SUCCESSFUL;
    }

    public static MapMenuMessages buildBuilding(int x, int y, String buildingName) {
        //todo difference between build and below decrease coin
        GameData gameData = GameMenuController.getGameData();
        PlayerNumber ownerPlayerNumber = gameData.getPlayerOfTurn();
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        Empire empire = gameData.getEmpireByPlayerNumber(ownerPlayerNumber);

        MapMenuMessages result = buildErrorCheck(x, y, buildingName, ownerPlayerNumber);
        if (!result.equals(MapMenuMessages.SUCCESSFUL))
            return result;

        if (!empire.canBuyBuilding(buildingName)) {
            return MapMenuMessages.LACK_OF_RESOURCES;
        } else if (empire.getWorklessPopulation() < Building.getNumberOfWorkers(buildingName)) {
            return MapMenuMessages.LACK_OF_HUMAN;
        }

        empire.buyBuilding(buildingName);
        empire.changeWorklessPopulation(-Building.getNumberOfWorkers(buildingName));
        chosenCell.makeBuilding(buildingName, ownerPlayerNumber);
        return MapMenuMessages.SUCCESSFUL;
    }

    public static MapMenuMessages dropBuildingAsAdmin(int x, int y, String buildingName, int ownerNumber) {
        GameData gameData = GameMenuController.getGameData();
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        PlayerNumber ownerPlayerNumber = PlayerNumber.getPlayerByIndex(ownerNumber - 1);

        MapMenuMessages result = buildErrorCheck(x, y, buildingName, ownerPlayerNumber);
        if (result.equals(MapMenuMessages.SUCCESSFUL)) {
            chosenCell.makeBuilding(buildingName, ownerPlayerNumber);
            return MapMenuMessages.SUCCESSFUL;
        } else
            return result;
    }

    private static MapMenuMessages buildErrorCheck(int x, int y, String buildingName, PlayerNumber ownerPlayerNumber) {
        GameData gameData = GameMenuController.getGameData();
        Empire empire = gameData.getEmpireByPlayerNumber(ownerPlayerNumber);
        if (!gameData.getMap().isIndexValid(x, y)) {
            return MapMenuMessages.INVALID_INDEX;
        }
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        String mainKeepName = AccommodationType.MAIN_KEEP.getName();
        if (buildingName.equals(mainKeepName)
                && empire.getNumberOfBuildingType(mainKeepName) > 0) {
            return MapMenuMessages.TWO_MAIN_KEEP;
        } else if (!Building.isBuildingNameValid(buildingName)) {
            return MapMenuMessages.INVALID_TYPE;
        } else if (!chosenCell.isAbleToBuildOn(buildingName)) {
            return MapMenuMessages.IMPROPER_CELL_TYPE;
        } else if (chosenCell.getBuilding() != null) {
            return MapMenuMessages.FULL_CELL;
        } else if (buildingTypeIsStore(buildingName)
                && IsAnotherStore(empire, buildingName)
                && !isConnectedToOthers(x, y, buildingName, empire)) {
            return MapMenuMessages.UNCONNECTED_STOREROOMS;
        }

        return MapMenuMessages.SUCCESSFUL;
    }

    private static boolean IsAnotherStore(Empire empire, String buildingName) {
        return empire.getNumberOfBuildingType(buildingName) > 0;
    }

    private static boolean buildingTypeIsStore(String buildingName) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.getName().equals(buildingName)) return true;
        }
        return false;
    }

    private static boolean isConnectedToOthers(int x, int y, String buildingName, Empire empire) {
        return thisTypeIsInThisCell(x - 1, y, buildingName, empire)
                || thisTypeIsInThisCell(x + 1, y, buildingName, empire)
                || thisTypeIsInThisCell(x, y - 1, buildingName, empire)
                || thisTypeIsInThisCell(x, y + 1, buildingName, empire);
    }

    private static boolean thisTypeIsInThisCell(int x, int y, String buildingName, Empire empire) {

        Map map = GameMenuController.getGameData().getMap();

        if (!map.isIndexValid(x, y)) return false;
        Cell chosenCell = map.getCells()[x][y];
        if (chosenCell.getBuilding() == null) {
            return false;
        } else {
            return chosenCell.getBuilding().getName().equals(buildingName)
                    && chosenCell.getBuilding().getOwnerEmpire().equals(empire);
        }
    }
}
