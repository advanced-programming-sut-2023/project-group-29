package controller.menucontrollers;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.*;
import model.buildings.Building;
import model.buildings.buildingClasses.OtherBuildings;
import model.buildings.buildingTypes.AccommodationType;
import model.buildings.buildingTypes.OtherBuildingsType;
import model.buildings.buildingTypes.StoreType;
import model.map.*;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import model.weapons.Weapon;
import model.weapons.weaponClasses.Equipments;
import model.weapons.weaponClasses.OffensiveWeapons;
import model.weapons.weaponClasses.StaticOffensiveWeapons;
import view.messages.MapMenuMessages;

public class MapFunctions {
    private static final int tileCapacityForShowingUnits=10;//todo should update with zoom level
    public static MapMenuMessages setShowingMapIndexes(int indexX, int indexY) {
//        Map map = GameController.getGameData().getMap();
//        if (!map.isIndexValid(indexX, indexY))
//            return MapMenuMessages.INVALID_INDEX;
//
//        showingMapIndexX = indexX;
//        showingMapIndexY = indexY;
        //todo for mini map

        return MapMenuMessages.SUCCESSFUL;
    }

    public static Pane[][] showMap(int indexX, int indexY, Group rootPane) {
        GameData gameData=GameController.getGameData();
        int tileWidth=gameData.getTileWidth();
        int tileHeight=gameData.getTileHeight();

        int numberOfTilesShowingInRow = AppData.getScreenWidth() / tileWidth;
        int numberOfTilesShowingInColumn = AppData.getScreenHeight() / tileHeight;

        Map map = GameController.getGameData().getMap();
        Pane[][] tiles=new Pane[numberOfTilesShowingInRow][numberOfTilesShowingInColumn];

        for (int i = 0; i < numberOfTilesShowingInRow; i++) {
            for (int j = 0; j < numberOfTilesShowingInColumn; j++) {
                if (!map.isIndexValid(indexX + i, indexY + j))
                    break;

                tiles[i][j] = createTile(indexX + i, indexY + j);
                tiles[i][j].setLayoutX(i * tileWidth);
                tiles[i][j].setLayoutY(j * tileHeight);
                rootPane.getChildren().add(tiles[i][j]);
            }
        }

        return tiles;
    }
    public static Pane createTile(int indexX, int indexY) {

        GameData gameData = GameController.getGameData();
        Cell cell = gameData.getMap().getCells()[indexX][indexY];
        int tileWidth=gameData.getTileWidth();
        int tileHeight=gameData.getTileHeight();

        Pane tile = new Pane();
        tile.setMaxWidth(tileWidth);
        tile.setMinWidth(tileWidth);
        tile.setMaxHeight(tileHeight);
        tile.setMinHeight(tileHeight);

        BackgroundSize backgroundSize=new BackgroundSize(tileWidth,tileHeight,false,false,false,false);
        tile.setBackground(new Background(new BackgroundImage(cell.getCellType().getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        //todo handle trees

        //building or trap
        if (cell.hasBuilding())
            fitImageInTile(cell.getBuilding().getShowingImage(), tile,tileWidth,tileHeight);
        else if (cell.hasTrap() && cell.getTrap().getOwnerNumber().equals(GameController.getGameData().getPlayerOfTurn()))
            fitImageInTile(cell.getTrap().getShowingImage(), tile,tileWidth,tileHeight);

        //other units
        int index = 0;
        for (int i = 0; i < tileCapacityForShowingUnits; i++) {
            while (index < cell.getMovingObjects().size()) {
                //hide assassin
                if (cell.getMovingObjects().get(index) instanceof Soldier soldier &&
                        soldier.getSoldierType().equals(SoldierType.ASSASSIN) &&
                        !soldier.getOwnerNumber().equals(gameData.getPlayerOfTurn()) &&
                        !gameData.getMap().isAssassinSeen
                                (cell.getXPosition(), cell.getYPosition(), gameData.getPlayerOfTurn()))
                    index++;
                else {
                    addUnitInTile(cell.getMovingObjects().get(index).getShowingImage(),tile,i);
                    break;
                }
            }
            index++;
        }

        return tile;
    }

    private static void addUnitInTile(Image image, Pane tile, int indexOfUnitInTile) {
        GameData gameData = GameController.getGameData();
        int unitWidth=30;
        int unitHeight=30;

        ImageView unitImage=new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(unitImage,unitWidth,unitHeight);

        int numberOfUnitsInRow=(gameData.getTileWidth()-unitWidth)/5+1;
        numberOfUnitsInRow=Math.max(numberOfUnitsInRow,1);

        int unitIndexInRow=indexOfUnitInTile%numberOfUnitsInRow;
        unitImage.setLayoutX(unitIndexInRow*5);


        int numberOfUnitsInColumn=(gameData.getTileHeight()/2-unitHeight)/5+1;
        numberOfUnitsInColumn=Math.max(numberOfUnitsInColumn,1);

        int unitIndexInColumn=(indexOfUnitInTile/numberOfUnitsInRow)%numberOfUnitsInColumn;
        unitImage.setLayoutY(unitIndexInColumn*5+gameData.getTileHeight()/2);

        tile.getChildren().add(unitImage);
    }

    private static void fitImageInTile(Image image, Pane tile,int tileWidth,int tileHeight) {
        ImageView imageView=new ImageView(image);
        ImagePracticalFunctions.fitWidthHeight(imageView,tileWidth,tileHeight);
        tile.getChildren().add(imageView);
    }


    public static String showDetails(int indexX, int indexY) {
        Map map = GameController.getGameData().getMap();
        PlayerNumber currentPlayer = GameController.getGameData().getPlayerOfTurn();

        if (!map.isIndexValid(indexX, indexY))
            return "Index is invalid!\n";

        Cell showingCell = map.getCells()[indexX][indexY];
        String output = "";

        output += "Type of cell: ";
        output += showingCell.getCellType().getName();
        output += "\n";

        output += "Type of tree: ";
        if (showingCell.getTreeTypes() != null)
            output += showingCell.getTreeTypes().getName();
        output += "\n";

        output += "Building: ";
        output += showingCell.hasBuilding() ? showingCell.getBuilding().getName() : "";
        output += showingCell.hasBuilding() ? " HP: " + showingCell.getBuilding().getHp() : "";
        output += "\n";

        output += "Trap: ";
        output += (showingCell.hasTrap() &&
                showingCell.getTrap().getOwnerNumber().equals(currentPlayer)) ? showingCell.getTrap().getName() : "";
        output += "\n";

        output += showingCell.hasTunnel() ?
                "There is a tunnel under this cell." : "There are no tunnels under this cell.";
        output += "\n";

        output += "Units and Weapons: \n";
        for (Asset asset : showingCell.getMovingObjects()) {
            output += "    ";
            if (asset instanceof Soldier soldier) {
                output += "name: " + soldier.getName() + ". owner: " +
                        soldier.getOwnerNumber() + " PLAYER. " + "hp: " + soldier.getHp() + ". ";
                output += soldier.hasAttackedThisTurn() ? "attacked. " : "not attacked. ";
                output += soldier.hasMovedThisTurn() ? " moved. " : "not moved. ";
            }
            else if (asset instanceof Weapon weapon) {
                output += "name: " + weapon.getName() + ". owner: " +
                        weapon.getOwnerNumber() + " PLAYER. " + "hp: " + weapon.getHp() + ". ";

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

    private static String colorString(String string, ConsoleColors color) {
        return color.getStringCode() + string + ConsoleColors.RESET_COLOR.getStringCode();
    }

    public static void moveMap(int upMovements, int rightMovements, int downMovements, int leftMovements) {
        GameData gameData=GameController.getGameData();
        Map map = gameData.getMap();
        Pair<Integer,Integer> mapCornerPosition=gameData.getCornerCellIndex();

        int newShowingMapIndexX = mapCornerPosition.first + rightMovements - leftMovements;
        int newShowingMapIndexY = mapCornerPosition.second + downMovements - upMovements;

        if (map.isIndexValid(newShowingMapIndexX, newShowingMapIndexY)) {
            gameData.setCornerCellIndex(new Pair<>(newShowingMapIndexX,newShowingMapIndexY));
        }
    }

    public static String setBlockTexture(CellType cellType, int x, int y) {
        if (GameController.getGameData().getMap().getCells()[x][y].hasBuilding()) {
            return "You can't change texture of this cell!";
        }
        GameController.getGameData().getMap().getCells()[x][y].setCellType(cellType);
        return "Type of the cell was successfully changed";
    }

    public static String setPartOfBlockTexture(CellType cellType, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (GameController.getGameData().getMap().getCells()[i][j].hasBuilding()) {
                    continue;
                }
                GameController.getGameData().getMap().getCells()[i][j].setCellType(cellType);
            }
        }
        return "Type of the cells were successfully changed";
    }

    public static String clear(int xPosition, int yPosition) {
        if (GameController.getGameData().getMap().getCells()[xPosition][yPosition].hasBuilding()) {
            return "You can't change texture of this cell!";
        }
        GameController.getGameData().getMap().getCells()[xPosition][yPosition].setCellType(CellType.PLAIN_GROUND);
        return "Type of the cell was successfully cleared";
    }

    public static String dropRock(int xPosition, int yPosition, String direction) {
        return switch (direction) {
            case "n" -> setBlockTexture(CellType.UP_ROCK, xPosition, yPosition);
            case "e" -> setBlockTexture(CellType.RIGHT_ROCK, xPosition, yPosition);
            case "s" -> setBlockTexture(CellType.DOWN_ROCK, xPosition, yPosition);
            case "w" -> setBlockTexture(CellType.LEFT_ROCK, xPosition, yPosition);
            default -> null;
        };
    }

    public static MapMenuMessages dropTree(int xPosition, int yPosition, String name) {
        GameData gameData = GameController.getGameData();
        TreeType treeType = TreeType.getTreeTypeByName(name);
        Cell cell = gameData.getMap().getCells()[xPosition][yPosition];
        cell.setTree(treeType);
        return MapMenuMessages.SUCCESSFUL;
    }


    public static MapMenuMessages dropUnit(int positionX, int positionY, String type, int count, int ownerPlayerNumberInt) {

        Map map = GameController.getGameData().getMap();

        PlayerNumber ownerPlayerNumber = PlayerNumber.getPlayerByIndex(ownerPlayerNumberInt - 1);
        Human unit = Human.createUnitByName(type, ownerPlayerNumber, positionX, positionY);

        if (unit == null)
            return MapMenuMessages.INVALID_UNIT_NAME;
        if (!map.isIndexValid(positionX, positionY))
            return MapMenuMessages.INVALID_INDEX;

        Cell currentCell = map.getCells()[positionX][positionY];
        if (currentCell.hasBuilding() && currentCell.getBuilding() instanceof OtherBuildings otherBuildings)
            if (otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.MOAT))
                if (!unit.isAbleToClimbLadder())
                    return MapMenuMessages.IMPROPER_CELL_TYPE;

        if (currentCell.hasTrap() && currentCell.getTrap().getOwnerNumber().equals(unit.getOwnerNumber()))
            return MapMenuMessages.IMPROPER_CELL_TYPE;
        if (!currentCell.isAbleToMoveOn())
            return MapMenuMessages.IMPROPER_CELL_TYPE;

        for (int i = 0; i < count; i++) {
            Human addingUnit = Human.createUnitByName(type, ownerPlayerNumber, positionX, positionY);
            map.getCells()[positionX][positionY].addMovingObject(addingUnit);
        }

        return MapMenuMessages.SUCCESSFUL;
    }

    public static MapMenuMessages buildBuilding(int x, int y, String buildingName) {
        GameData gameData = GameController.getGameData();
        PlayerNumber ownerPlayerNumber = gameData.getPlayerOfTurn();
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        Empire empire = gameData.getEmpireByPlayerNumber(ownerPlayerNumber);

        MapMenuMessages result = buildErrorCheck(x, y, buildingName, ownerPlayerNumber);
        if (!result.equals(MapMenuMessages.SUCCESSFUL))
            return result;

        if (!empire.canBuyBuilding(buildingName)) {
            return MapMenuMessages.LACK_OF_RESOURCES;
        }
        else if (empire.getWorklessPopulation() < Building.getNumberOfWorkers(buildingName)) {
            return MapMenuMessages.LACK_OF_HUMAN;
        }

        empire.buyBuilding(buildingName);
        empire.changeWorklessPopulation(-Building.getNumberOfWorkers(buildingName));
        chosenCell.makeBuilding(buildingName, ownerPlayerNumber);
        return MapMenuMessages.SUCCESSFUL;
    }

    public static MapMenuMessages dropBuildingAsAdmin(int x, int y, String buildingName, int ownerNumber) {
        GameData gameData = GameController.getGameData();
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        PlayerNumber ownerPlayerNumber = PlayerNumber.getPlayerByIndex(ownerNumber - 1);
        MapMenuMessages result = buildErrorCheck(x, y, buildingName, ownerPlayerNumber);
        if (result.equals(MapMenuMessages.SUCCESSFUL)) {
            chosenCell.makeBuilding(buildingName, ownerPlayerNumber);
            return MapMenuMessages.SUCCESSFUL;
        }
        else
            return result;
    }

    private static MapMenuMessages buildErrorCheck(int x, int y, String buildingName, PlayerNumber ownerPlayerNumber) {
        GameData gameData = GameController.getGameData();
        Empire empire = gameData.getEmpireByPlayerNumber(ownerPlayerNumber);
        Cell chosenCell = gameData.getMap().getCells()[x][y];
        String mainKeepName = AccommodationType.MAIN_KEEP.getBuildingType().name();
        if (buildingName.equals(mainKeepName)
                && empire.getNumberOfBuildingType(mainKeepName) > 0) {
            return MapMenuMessages.TWO_MAIN_KEEP;
        }
        else if (!chosenCell.getCellType().isAbleToBuildOn(buildingName)) {
            return MapMenuMessages.IMPROPER_CELL_TYPE;
        }
        else if (chosenCell.getBuilding() != null || chosenCell.hasTrap()) {
            if (!(chosenCell.getBuilding() instanceof OtherBuildings otherBuildings &&
                    otherBuildings.getOtherBuildingsType().equals(OtherBuildingsType.SHORT_WALL) &&
                    buildingName.equals("stair") &&
                    chosenCell.getBuilding().getOwnerNumber().equals(gameData.getPlayerOfTurn()))) {
                return MapMenuMessages.FULL_CELL;
            }
        }
        else if (buildingTypeIsStore(buildingName)
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
            if (storeType.getBuildingType().name().equals(buildingName)) return true;
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

        Map map = GameController.getGameData().getMap();

        if (!map.isIndexValid(x, y)) return false;
        Cell chosenCell = map.getCells()[x][y];
        if (chosenCell.getBuilding() == null) {
            return false;
        }
        else {
            return chosenCell.getBuilding().getName().equals(buildingName)
                    && chosenCell.getBuilding().getOwnerEmpire().equals(empire);
        }
    }

    public static void setPopularityFactors(int foodRate, int fearRate, int taxRate) {
        Empire empire = GameController.getGameData().getPlayingEmpire();
        empire.setFearRate(fearRate);
        empire.setTaxRate(taxRate);
        empire.setFoodRate(foodRate);
    }
}
