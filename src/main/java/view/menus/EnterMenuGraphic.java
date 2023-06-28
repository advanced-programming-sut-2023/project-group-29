package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.scene.input.MouseEvent;
import model.AppData;
import model.buildings.buildingTypes.*;

public class EnterMenuGraphic {

    public static void buildEnums() {
        AccommodationType.enumBuilder();
        AttackingBuildingType.enumBuilder();
        OtherBuildingsType.enumBuilder();
        ProductExtractorType.enumBuilder();
        ProductProcessorType.enumBuilder();
        ResourceExtractorType.enumBuilder();
        ResourceProcessorType.enumBuilder();
        StoreType.enumBuilder();
        UnitCreatorType.enumBuilder();
    }
    public static void signIn() throws Exception {
        new LoginMenu().start(AppData.getStage());
    }

    public static void signUp() throws Exception {
        new RegisterMenu().start(AppData.getStage());
    }

    public void cheat(MouseEvent mouseEvent) throws Exception {
        LoginMenuController.login("alii","Aa@100");
        new MainMenu().start(AppData.getStage());
        //todo delete after use
    }
}
