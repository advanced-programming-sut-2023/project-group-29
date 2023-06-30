package view.menus.gamepopupmenus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CellDetailsWindowGraphics {
    @FXML
    private Label textLabel;

    public void initializeText(String text) {
        textLabel.setText(text);
    }
}
