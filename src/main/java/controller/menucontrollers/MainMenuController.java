package controller.menucontrollers;

import model.AppData;
import model.Empire;
import model.GameData;
import model.PlayerNumber;

public class MainMenuController {
    public static void createGameData() {
        GameData gameData = new GameData();
        GameController.setGameData(gameData);
        gameData.addEmpire(new Empire(AppData.getCurrentUser()));
        gameData.setPlayerOfTurn(PlayerNumber.FIRST);
    }
}
