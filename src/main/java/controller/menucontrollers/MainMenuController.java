package controller.menucontrollers;

import model.GameData;
import model.PlayerNumber;
import model.User;

public class MainMenuController {
    public static void createGameData(User loggedInUser) {
        //todo faratin: give users and chosen map in argument
        //todo jasbi: add remove users command menu
        GameData gameData = new GameData();
        GameMenuController.setGameData(gameData);
        gameData.addEmpire(loggedInUser.getEmpire());
        gameData.setPlayerOfTurn(PlayerNumber.FIRST);
    }
}
