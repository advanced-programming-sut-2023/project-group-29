package controller.menucontrollers;

import model.*;
import model.map.Cell;
import model.map.Map;
import model.map.MapTemplate;
import model.network.Client;
import model.people.humanTypes.SoldierType;
import view.messages.PreGameMenuMessages;

import java.io.IOException;

public class PreGameMenuController {

    public static PreGameMenuMessages addUserToGame(String username) {
        GameData gameData = GameController.getGameData();
        User user;
        if ((user = AppData.getUserByUsername(username)) == null) {
            return PreGameMenuMessages.INVALID_USER;
        } else if (gameData.IsUserInGame(user)) {
            return PreGameMenuMessages.USER_EXIST;
        } else if (gameData.getNumberOfPlayers() >= 8) {
            return PreGameMenuMessages.FILLED_TO_CAPACITY;
        }
        gameData.addEmpire(new Empire(user));
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages chooseMap(String mapName) {
        MapTemplate mapTemplate;
        if ((mapTemplate = AppData.getLocalMapByMapName(mapName)) == null) {
            if ((mapTemplate = AppData.getPublicMapByMapName(mapName)) == null) {
                return PreGameMenuMessages.INVALID_MAP;
            }
        }
        Map map = newMapFromTemplate(mapTemplate);
        if (map == null) return PreGameMenuMessages.FEW_PLAYER;
        return PreGameMenuMessages.SUCCESS;
    }

    private static Map newMapFromTemplate(MapTemplate mapTemplate) {
        Map map = new Map(mapTemplate.getWidth(), mapTemplate.getUsersCount());
        Cell[][] cells = map.getCells();
        int empireNumber = 1;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if (mapTemplate.getHasEmpire()[i][j]){
                    MapFunctions.dropBuildingAsAdmin(i, j, "mainKeep", empireNumber);
                    MapFunctions.dropBuildingAsAdmin(i, j, "stockPile", empireNumber);
                    MapFunctions.dropUnit(i, j, SoldierType.ENGINEER.getName(), 2, empireNumber);
                    MapFunctions.dropUnit(i, j, SoldierType.ARCHER.getName(), 2, empireNumber);
                    empireNumber++;
                }
                cells[i][j] = new Cell(mapTemplate.getCellTypes()[i][j], i, j);
                cells[i][j].setTree(mapTemplate.getTreeTypes()[i][j]);
            }
        }
        GameController.getGameData().setMap(map);
        return map;
    }

    public static PreGameMenuMessages isGameDataReady() {
        GameData gameData = GameController.getGameData();
        if (gameData.getMap() == null) {
            return PreGameMenuMessages.NOT_CHOSEN_MAP;
        }
        GameController.updateEmpire(PlayerNumber.FIRST);
        String result;
        try {
            String[] playerUsernames = new String[gameData.getNumberOfPlayers()];
            for (int i = 0; i < gameData.getNumberOfPlayers(); i++)
                playerUsernames[i] = gameData.getEmpires().get(i).getUser().getUsername();
            result = AppData.getClient().requestFormServer(AppData.getClient().requestStringGenerator(Client.RequestType.START_GAME, playerUsernames));
            gameData.setId(result);
            AppData.getClient().startListeningForInput(Client.ListenType.GAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return PreGameMenuMessages.READY;
    }

    public static void setPlayerNumbersAlive() {
        for (PlayerNumber playerNumber : PlayerNumber.values()) {
            playerNumber.setAlive(true);
        }
    }
}
