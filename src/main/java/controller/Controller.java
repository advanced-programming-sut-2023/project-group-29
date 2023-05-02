package controller;

import model.buildings.buildingTypes.*;
import view.menus.*;

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
                case SHOP_MENU -> menuNames = ShopMenu.run(scanner);
                case TRADE_MENU -> menuNames = TradeMenu.run(scanner);
                case SELECT_MENU -> menuNames = SelectMenu.run(scanner);
                case MAP_MENU -> menuNames = MapMenu.run(scanner);
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
        ResourceExtractorType.enumBuilder();
        ServiceType.enumBuilder();
        StoreType.enumBuilder();
    }
}
