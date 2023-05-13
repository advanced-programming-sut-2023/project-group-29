package controller.menucontrollers;

import model.*;

public class MainMenuController {
    public static void createGameData() {
        GameData gameData = new GameData();
        GameMenuController.setGameData(gameData);
        gameData.addEmpire(new Empire(AppData.getCurrentUser()));
        gameData.setPlayerOfTurn(PlayerNumber.FIRST);
    }
}
