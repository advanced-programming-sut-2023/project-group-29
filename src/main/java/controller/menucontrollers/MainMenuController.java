package controller.menucontrollers;

import model.GameData;

public class MainMenuController {
    public static void startNewGame()
    {
        //todo faratin: give users and chosen map in argument
        //todo jasbi: add remove users command menu
        GameData gameData=new GameData();
        GameMenuController.setGameData(gameData);
    }
}
