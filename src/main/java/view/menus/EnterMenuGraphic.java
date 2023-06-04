package view.menus;

import javafx.scene.input.MouseEvent;
import model.AppData;

public class EnterMenuGraphic {
    public static void signIn() throws Exception {
        new LoginMenu().start(AppData.getStage());
    }

    public static void signUp() throws Exception {
        new RegisterMenu().start(AppData.getStage());
    }
}
