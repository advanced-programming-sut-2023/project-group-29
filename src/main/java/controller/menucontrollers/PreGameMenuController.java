package controller.menucontrollers;

import model.AppData;
import model.Empire;
import model.GameData;
import model.User;
import model.map.Map;
import model.map.MapInitializer;
import view.messages.PreGameMenuMessages;

public class PreGameMenuController {

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
        gameData.addEmpire(new Empire(user));
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages chooseMap(int index) {
        if (index >= MapInitializer.getDefaultMapCounts()) {
            return PreGameMenuMessages.OUT_OF_RANGE;
        }
        GameData gameData = GameMenuController.getGameData();
        Map map = MapInitializer.initialize(index);
        gameData.setMap(map);
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
