package view.menus;

import controller.MenuNames;
import controller.menucontrollers.PreGameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.AppData;
import view.Command;
import view.messages.PreGameMenuMessages;

import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = Command.class.getResource("/FXML/PreGameMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static MenuNames run(Scanner scanner) {
        System.out.println("Please choose your opponents and map:");
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = Command.getMatcher(input, Command.ADD_PLAYER_TO_GAME)) != null) {
                addPlayerToGame(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.CHOOSE_MAP)) != null) {
                chooseMap(matcher);
            }
            else if (Command.getMatcher(input, Command.READY) != null) {
                if (notReady()) continue;
                return MenuNames.GAME_MENU;
            }
            else if (Command.getMatcher(input, Command.CANCEL) != null) {
                System.out.println("The game was canceled!");
                return MenuNames.MAIN_MENU;
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void addPlayerToGame(Matcher matcher) {
        String username = matcher.group("username");
        PreGameMenuMessages result = PreGameMenuController.addUserToGame(username);
        switch (result) {
            case INVALID_USER -> System.out.println("No user exists with this username!");
            case USER_EXIST -> System.out.println("You have already added this user!");
            case FILLED_TO_CAPACITY -> System.out.println("The maximum number of player is 8!");
            case SUCCESS -> System.out.println("The player was added successfully!");
        }
    }

    private static void chooseMap(Matcher matcher) {
        int index = Integer.parseInt(matcher.group("index"));
        PreGameMenuMessages result = PreGameMenuController.chooseMap(index);
        switch (result) {
            case FEW_PLAYER -> System.out.println("Number of players is not proper for this map!");
            case OUT_OF_RANGE -> System.out.println("No map exists with this index!");
            case SUCCESS -> System.out.println("Map is chosen successfully!");
        }
    }

    private static boolean notReady() {
        PreGameMenuMessages result = PreGameMenuController.isGameDataReady();
        switch (result) {
            case FEW_PLAYER -> {
                System.out.println("Playing with yourself will not be interesting!");
                return true;
            }
            case NOT_CHOSEN_MAP -> {
                System.out.println("You haven't still chosen the map!");
                return true;
            }
            case READY -> {
                PreGameMenuController.setPlayerNumbersAlive();
                System.out.println("The game started. GOOD LUCK!");
            }
        }
        return false;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(AppData.getStage());
    }

    public void play(MouseEvent mouseEvent) throws Exception {

        new MapMenu().start(AppData.getStage());
    }
}
