package view.menus;

import javafx.scene.input.MouseEvent;
import model.AppData;

public class EnterMenuGraphic {
    public void signIn(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(AppData.getStage());
    }

    public void signUp(MouseEvent mouseEvent) throws Exception {
        new RegisterMenu().start(AppData.getStage());
    }
}
