package controller.menucontrollers;

import model.GameData;
import model.PlayerNumber;
import model.buildings.Building;
import model.map.Cell;
import model.map.ConsoleColors;
import model.map.Map;
import model.people.Human;
import model.people.humanClasses.Soldier;
import model.people.humanTypes.SoldierType;
import view.menus.GameMenu;
import view.messages.GameMenuMessages;
import view.messages.MapMenuMessages;

import java.util.ArrayList;

public class MapMenuController {
    private static final int tileWidth = 6;
    private static final int tileHeight = 4;
    private static final int maxNumberOfTilesShowingInRow = 14;
    private static final int maxNumberOfTilesShowingInColumn = 4;
    private static int showingMapIndexX=1;
    private static int showingMapIndexY=1;

    public static MapMenuMessages setShowingMapIndexes(int indexX, int indexY)
    {
        Map map = GameMenuController.getGameData().getMap();
        if (!map.isIndexValid(indexX, indexY))
            return MapMenuMessages.INVALID_INDEX;

        showingMapIndexX=indexX;
        showingMapIndexY=indexY;

        return MapMenuMessages.SUCCESSFUL;
    }
    public static String showMap(int indexX, int indexY) {
        Map map = GameMenuController.getGameData().getMap();

        MapMenuMessages message=setShowingMapIndexes(indexX,indexY);
        if(message.equals(MapMenuMessages.INVALID_INDEX))
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

    public static String showMap()
    {
        return showMap(showingMapIndexX,showingMapIndexY);
    }


    private static String showTilesOfMap(ArrayList<String>[][] tiles, int mapShowingWidth, int mapShowingHeight) {
        String tilesOfMap = "";
        for (int i = 0; i < mapShowingHeight; i++) {
            tilesOfMap += "-".repeat(mapShowingWidth * (tileWidth + 1) + 1);
            tilesOfMap += "\n";

            for (int j = 0; j < tileHeight; j++) {
                for (int k = 0; k < mapShowingWidth; k++) {

                    //System.out.println("j: "+j+" k:" +k+" i: "+i);
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
            showingSignOfBuilding =cell.getBuilding().getShowingSignInMap();
        else if (cell.hasTrap())
            showingSignOfBuilding =cell.getTrap().getShowingSignInMap();

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

        //TODO extra point defined by us:maybe show ... for more units
        //TODO extra point defined by us:maybe count number of each soldier

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

        //TODO change string below or delete it because it can be handled in show map
        if (!map.isIndexValid(newShowingMapIndexX, newShowingMapIndexY))
            return "The index is invalid";

        return showMap(newShowingMapIndexX, newShowingMapIndexY);
    }

    //TODO below functions should probably move to another menu


    public static void setTexture() {
    }

    public static void clear() {
    }

    public static void dropRock() {
    }

    public static void dropTree() {
    }


    public static String dropUnit(int positionX,int positionY,String type,int count,int ownerPlayerNumberInt) {

        Map map=GameMenuController.getGameData().getMap();
        return map.dropUnit(positionX,positionY,type,count,ownerPlayerNumberInt);
    }

    //todo correct return type and other changes in function
    public static GameMenuMessages dropBuilding(int x, int y, String buildingName, int playerNumberInt) {

        Map map=GameMenuController.getGameData().getMap();
        return map.dropBuilding(x,y,buildingName,playerNumberInt);
    }
}
