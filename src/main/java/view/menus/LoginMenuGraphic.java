package view.menus;

import javafx.scene.input.MouseEvent;
import model.AppData;

public class LoginMenuGraphic {
    public void back(MouseEvent mouseEvent) throws Exception {
        new EnterMenu().start(AppData.getStage());
    }
}
