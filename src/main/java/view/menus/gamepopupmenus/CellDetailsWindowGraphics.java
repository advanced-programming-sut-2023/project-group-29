package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CellDetailsWindowGraphics {
    @FXML
    private Label textLabel;

    public void initializeText(String text) {
        textLabel.setText(text);
    }
}
