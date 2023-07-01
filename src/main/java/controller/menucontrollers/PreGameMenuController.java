package controller.menucontrollers;

import model.*;
import model.map.Map;
import model.map.MapInitializer;
import model.network.Client;
import view.messages.PreGameMenuMessages;

import java.io.IOException;

public class PreGameMenuController {

    public static PreGameMenuMessages addUserToGame(String username) {
        GameData gameData = GameController.getGameData();
        User user;
        if ((user = AppData.getUserByUsername(username)) == null) {
            return PreGameMenuMessages.INVALID_USER;
        }
        else if (gameData.IsUserInGame(user)) {
            return PreGameMenuMessages.USER_EXIST;
        }
        else if (gameData.getNumberOfPlayers() >= 8) {
            return PreGameMenuMessages.FILLED_TO_CAPACITY;
        }
        gameData.addEmpire(new Empire(user));
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages chooseMap(int index) {
        if (index > MapInitializer.getDefaultMapCounts() || index == 0) {
            return PreGameMenuMessages.OUT_OF_RANGE;
        }
        Map map = MapInitializer.initialize(index, GameController.getGameData());
        if (map == null) return PreGameMenuMessages.FEW_PLAYER;
        return PreGameMenuMessages.SUCCESS;
    }

    public static PreGameMenuMessages isGameDataReady() {
        GameData gameData = GameController.getGameData();
        if (gameData.getMap() == null) {
            return PreGameMenuMessages.NOT_CHOSEN_MAP;
        }
        else if (gameData.getNumberOfPlayers() == 1) {
            return PreGameMenuMessages.FEW_PLAYER;
        }
        GameController.updateEmpire(PlayerNumber.FIRST);

        String result;
        try {
            String[] playerUsernames=new String[gameData.getNumberOfPlayers()];
            for(int i=0;i<gameData.getNumberOfPlayers();i++)
                playerUsernames[i]=gameData.getEmpires().get(i).getUser().getUsername();
            result=AppData.getClient().requestFormServer(AppData.getClient().requestStringGenerator(Client.RequestType.START_GAME,playerUsernames));
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
