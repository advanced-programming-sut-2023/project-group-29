package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameMenuController;
import model.AppData;
import model.PlayerNumber;
import view.Command;
import view.messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private static PlayerNumber playerNumber;

    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();

        if (Command.getMatcher(input, Command.SHOW_MAP) != null) {
            return MenuNames.MAP_MENU;
        } else if (Command.getMatcher(input, Command.SHOW_POPULARITY_FACTORS) != null) {
            showPopularityFactors();
        } else if (Command.getMatcher(input, Command.SHOW_POPULARITY) != null) {
            showPopularity();
        } else if (Command.getMatcher(input, Command.SHOW_FOOD_LIST) != null) {
            showFoodList();
        } else if (Command.getMatcher(input, Command.SHOW_FOOD_RATE) != null) {
            showFoodRate();
        } else if (Command.getMatcher(input, Command.SHOW_TAX_RATE) != null) {
            showTaxRate();
        } else if ((matcher = Command.getMatcher(input, Command.SET_FOOD_RATE)) != null) {
            setFoodRate(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SET_TAX_RATE)) != null) {
            setTaxRate(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SET_FEAR_RATE)) != null) {
            setFearRate(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.DROP_BUILDING)) != null) {
            dropBuilding(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SELECT_BUILDING)) != null) {
            selectBuilding(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SELECT_UNIT)) != null) {
            selectUnit(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.NEXT_TURN)) != null) {
            GameMenuController.nextTurn();
        } else if (Command.getMatcher(input, Command.ENTER_TRADE_MENU) != null) {
            System.out.println("You entered trade menu");
            for (int i = 0; i < AppData.getUsers().size(); i++)
                System.out.println("User" + (i + 1) + ": " + AppData.getUsers().get(i).getUsername());
            return MenuNames.TRADE_MENU;
        } else if (Command.getMatcher(input, Command.ENTER_SHOP_MENU) != null) {
            System.out.println("You entered shop menu");
            return MenuNames.SHOP_MENU;
        } else if (Command.getMatcher(input, Command.ENTER_SELECT_MENU) != null) {
            System.out.println("You entered select menu");
            return MenuNames.SELECT_MENU;
        } else if (Command.getMatcher(input, Command.BACK_MAIN_MENU) != null) {
            System.out.println("You entered main menu");
            return MenuNames.MAIN_MENU;
        } else {
            System.out.println("Invalid command!");
        }

        return MenuNames.GAME_MENU;
    }


    private static void showPopularityFactors() {
        System.out.println(GameMenuController.showPopularityFactors(playerNumber));
    }

    private static void showPopularity() {
        System.out.println(GameMenuController.showPopularity(playerNumber));
    }

    private static void showFoodList() {
        System.out.println(GameMenuController.showFoodList(playerNumber));
    }

    private static void showFoodRate() {
        System.out.println(GameMenuController.showFoodRate(playerNumber));
    }

    private static void showTaxRate() {
        System.out.println(GameMenuController.showTaxRate(playerNumber));
    }

    private static void setFoodRate(Matcher matcher) {
        int foodRate = Integer.parseInt(matcher.group("rate")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfFoodRate(playerNumber, foodRate);
        switch (result) {
            case SUCCESS -> System.out.println("Food rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void setTaxRate(Matcher matcher) {
        int taxRate = Integer.parseInt(matcher.group("rate")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfTaxRate(playerNumber, taxRate);
        switch (result) {
            case SUCCESS -> System.out.println("Tax rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void setFearRate(Matcher matcher) {
        int fearRate = Integer.parseInt(matcher.group("rate")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfFearRate(playerNumber, fearRate);
        switch (result) {
            case SUCCESS -> System.out.println("Fear rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xPosition")); //TODO: using matcher correctly;
        int y = Integer.parseInt(matcher.group("yPosition")); //TODO: using matcher correctly;
        String buildingName = matcher.group("type"); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.dropBuilding(x, y, buildingName, playerNumber);
        switch (result) {
            case INVALID_POSITION -> System.out.println("You have chosen an Invalid amount of x or y!");
            case INVALID_TYPE -> System.out.println("This type of building doesn't exist!");
            case IMPROPER_CELL_TYPE -> System.out.println("This cell is improper for dropping this type of building!");
            case FULL_CELL -> System.out.println("Another building has been already dropped here!");
            case SUCCESS -> System.out.println("The building was dropped successfully!");
        }
    }

    private static void selectBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xPosition")); //TODO: using matcher correctly;
        int y = Integer.parseInt(matcher.group("yPosition")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.selectBuilding(x, y, playerNumber);
        switch (result) {
            case INVALID_POSITION -> System.out.println("You have chosen an Invalid amount of x or y!");
            case EMPTY_CELL -> System.out.println("This cell is empty!");
            case OTHERS_BUILDINGS -> System.out.println("This building belongs to others!");
            case SUCCESS -> {
                System.out.println("The building is selected successfully!");
                //enter select menu
            }
        }
    }

    private static void selectUnit(Matcher matcher) {
        //TODO: complete it
    }

    private static void trade(Matcher matcher) {
        //TODO: complete it
    }

    private static void showTradeList() {
        //TODO: complete it
    }

    private static void tradeAccept(Matcher matcher) {
        //TODO: complete it
    }

    private static void tradeHistory(Matcher matcher) {
        //TODO: complete it
    }
}
