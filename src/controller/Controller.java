package controller;

import model.buildings.buildingTypes.*;
import view.*;

import java.util.Scanner;

public class Controller {
    public static void run() {
        buildEnums();
        Scanner scanner = new Scanner(System.in);
        MenuNames menuNames = MenuNames.LOGIN_MENU;

        while (true) {
            switch (menuNames) {
                case LOGIN_MENU -> menuNames = LoginMenu.run(scanner);
                case GAME_MENU -> menuNames = GameMenu.run(scanner);
                case MAIN_MENU -> menuNames = MainMenu.run(scanner);
                case PROFILE_MENU -> menuNames = ProfileMenu.run(scanner);
                case EXIT -> {
                    return;
                }
            }
        }
    }

    private static void buildEnums() {
        AccommodationType.enumBuilder();
        AttackingBuildingType.enumBuilder();
        OtherBuildingsType.enumBuilder();
        ProcessorType.enumBuilder();
        ResourceExtracterType.enumBuilder();
        ServiceType.enumBuilder();
        StoreType.enumBuilder();
    }
}
