package view.menus;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.AppData;

public class RegisterMenuGraphic {
    public void back(MouseEvent mouseEvent) throws Exception {
        new EnterMenu().start(AppData.getStage());
    }

    public void showSloganPart(MouseEvent mouseEvent) {
        TextField textField = new TextField();
        textField.setPromptText("Slogan");
        RegisterMenu.getPane().getChildren().add(textField);
    }
}
