package controller.menucontrollers;

import model.AppData;
import model.GameData;
import model.User;
import model.map.DefaultMaps;
import model.map.Map;
import view.messages.PreGameMenuMessages;

public class PreGameMenuController {

    //todo jasbi user dooooooes nooooot have empire
    public static PreGameMenuMessages addUserToGame(String username) {
        GameData gameData = GameMenuController.getGameData();
        User user;
        if((user = AppData.getUserByUsername(username)) == null) {
            return PreGameMenuMessages.INVALID_USER;
        } else if (gameData.IsUserInGame(user)) {
            return PreGameMenuMessages.USER_EXIST;
        } else if (gameData.getNumberOfPlayers() >= 8) {
            return PreGameMenuMessages.FILLED_TO_CAPACITY;
        }
        gameData.addEmpire(user.getEmpire());
        //TODO: set player number
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages chooseMap(int index) {
        if (index >= DefaultMaps.getDefaultMapsCount()) {
            return PreGameMenuMessages.OUT_OF_RANGE;
        }
        //TODO: choose map number index;
        GameMenuController.getGameData().setMap(DefaultMaps.SECOND_MAP.getMap());//todo get by index
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages isGameDataReady() {
        GameData gameData = GameMenuController.getGameData();
        if (gameData.getMap() == null) {
            return PreGameMenuMessages.NOT_CHOSEN_MAP;
        } else if (gameData.getNumberOfPlayers() == 1) {
            return PreGameMenuMessages.FEW_PLAYER;
        }
        return PreGameMenuMessages.READY;
    }
}
