package view;

import controller.GameMenuController;
import controller.MenuNames;
import model.Empire;
import model.PlayerNumber;
import view.messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private static Empire loggedInEmpire;
    private static PlayerNumber playerNumber;

    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if (Command.getMatcher(input, Command.SHOW_MAP) != null) {
            showMap();
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
        } else if ((matcher = Command.getMatcher(input, Command.TRADE)) != null) {
            trade(matcher);
        } else if (Command.getMatcher(input, Command.SHOW_TRADE_LIST) != null) {
            showTradeList();
        } else if ((matcher = Command.getMatcher(input, Command.TRADE_ACCEPT)) != null) {
            tradeAccept(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.TRADE_HISTORY)) != null) {
            tradeHistory(matcher);
        } else {
            System.out.println("Invalid command!");
        }

        return null;
    }

    private static void showMap() {
        //TODO: complete it
    }

    private static void showPopularityFactors() {
        System.out.println(GameMenuController.showPopularityFactors(loggedInEmpire));
    }

    private static void showPopularity() {
        System.out.println(GameMenuController.showPopularity(loggedInEmpire));
    }

    private static void showFoodList() {
        System.out.println(GameMenuController.showFoodList(loggedInEmpire));
    }

    private static void showFoodRate() {
        System.out.println(GameMenuController.showFoodRate(loggedInEmpire));
    }

    private static void showTaxRate() {
        System.out.println(GameMenuController.showTaxRate(loggedInEmpire));
    }

    private static void setFoodRate(Matcher matcher) {
        int foodRate = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfFoodRate(loggedInEmpire, foodRate);
        switch (result) {
            case SUCCESS:
                System.out.println("food rate has been set successfully!");
            case UNSUCCESS:
                System.out.println("There was an error in the process!");
            default:
                break;
        }
    }

    private static void setTaxRate(Matcher matcher) {
        int taxRate = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfTaxRate(loggedInEmpire, taxRate);
        switch (result) {
            case SUCCESS:
                System.out.println("tax rate has been set successfully!");
            case UNSUCCESS:
                System.out.println("There was an error in the process!");
            default:
                break;
        }
    }

    private static void setFearRate(Matcher matcher) {
        int fearRate = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.determinationOfFearRate(loggedInEmpire, fearRate);
        switch (result) {
            case SUCCESS -> System.out.println("fear rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        int y = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        String buildingName = matcher.group(""); //TODO: using matcher correctly;
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
        int x = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        int y = Integer.parseInt(matcher.group("")); //TODO: using matcher correctly;
        GameMenuMessages result = GameMenuController.selectBuilding(x, y, loggedInEmpire);
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