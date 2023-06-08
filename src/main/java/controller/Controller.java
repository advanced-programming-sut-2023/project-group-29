package controller;

import model.AppData;
import model.SaveAndLoad;
import model.User;
import model.buildings.buildingTypes.*;
import view.Main;
import view.menus.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    public static void run() throws Exception {
//        User[] users = SaveAndLoad.loadArrayData(AppData.getUsersDataBaseFilePath(), User[].class);
//        if (users != null)
//            AppData.setUsers(new ArrayList<>(Arrays.asList(users)));
        Scanner scanner = new Scanner(System.in);
        MenuNames menuNames;
        if (AppData.checkStayLoggedIn()) {
            menuNames = MenuNames.MAIN_MENU;
        }
        else {
            menuNames = MenuNames.LOGIN_MENU;
        }

        while (true) {
            switch (menuNames) {
                case LOGIN_MENU -> menuNames = LoginMenu.run(scanner);
                case GAME_MENU -> menuNames = GameMenu.run(scanner);
                case MAIN_MENU -> menuNames = MainMenu.run(scanner);
                case PROFILE_MENU -> menuNames = ProfileMenu.run(scanner);
                case SHOP_MENU -> menuNames = ShopMenu.run(scanner);
                case PRE_GAME_MENU -> menuNames = PreGameMenu.run(scanner);
                case TRADE_MENU -> menuNames = TradeMenu.run(scanner);
                case SELECT_BUILDING_MENU -> menuNames = SelectBuildingMenu.run(scanner);
                case SELECT_UNIT_MENU -> menuNames = SelectUnitMenu.run(scanner);
                case MAP_MENU -> {
                    new MapMenu().start(Main.stage);
                    //menuNames = MapMenu.run(scanner);
                }
                case EXIT -> {
                    return;
                }
            }
        }
    }

    public static void buildEnums() {//todo jasbi convert to private
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
}
