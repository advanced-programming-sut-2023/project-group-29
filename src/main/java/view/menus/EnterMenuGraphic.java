package view.menus;

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
}
