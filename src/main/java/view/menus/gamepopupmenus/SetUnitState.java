package view.menus.gamepopupmenus;

import controller.menucontrollers.GameController;
import controller.menucontrollers.UnitFunctions;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.ImagePracticalFunctions;
import model.unitfeatures.Offensive;
import model.unitfeatures.UnitState;
import model.weapons.weaponTypes.EquipmentsType;
import model.weapons.weaponTypes.OffensiveWeaponsType;
import model.weapons.weaponTypes.StaticOffensiveWeaponsType;
import model.weapons.weaponTypes.TrapType;
import view.menus.EnterMenu;

import java.util.ArrayList;

public class SetUnitState {
    public void exitPopUp(MouseEvent mouseEvent) {
        GameController.getGameData().getGameGraphicFunctions().exitPopUp();
    }
    public void offensive(MouseEvent mouseEvent) {
        ArrayList<Offensive> attackers=Offensive.getOffensivesOfUnits(GameController.getGameData().getSelectedUnits());

        UnitFunctions.setStateOfUnit(attackers, UnitState.OFFENSIVE);

        GameController.getGameData().getGameGraphicFunctions().alertMessage(Color.GREEN,"setting state results:","the state was successfully set");
    }

    public void deffensive(MouseEvent mouseEvent) {
        ArrayList<Offensive> attackers=Offensive.getOffensivesOfUnits(GameController.getGameData().getSelectedUnits());

        UnitFunctions.setStateOfUnit(attackers, UnitState.DEFENSIVE);

        GameController.getGameData().getGameGraphicFunctions().alertMessage(Color.GREEN,"setting state results:","the state was successfully set");

    }
    public void standing(MouseEvent mouseEvent) {
        ArrayList<Offensive> attackers=Offensive.getOffensivesOfUnits(GameController.getGameData().getSelectedUnits());

        UnitFunctions.setStateOfUnit(attackers, UnitState.STANDING);

        GameController.getGameData().getGameGraphicFunctions().alertMessage(Color.GREEN,"setting state results:","the state was successfully set");
    }
}
