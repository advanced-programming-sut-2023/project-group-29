package view.menus;

import controller.MenuNames;
import controller.menucontrollers.GameMenuController;
import controller.menucontrollers.MapMenuController;
import controller.menucontrollers.SelectUnitMenuController;
import model.GameData;
import model.dealing.Trade;
import view.Command;
import view.messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered game menu");
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();

            if ((matcher=Command.getMatcher(input, Command.SHOW_MAP)) != null) {
                showMap(matcher);
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
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_WEALTH)) != null) {
                showWealth();
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_COMMODITY)) != null) {
                showCommodity();
            }else if ((matcher = Command.getMatcher(input, Command.SELECT_BUILDING)) != null) {
                if(selectBuilding(matcher).equals(GameMenuMessages.SUCCESS))
                    return MenuNames.SELECT_BUILDING_MENU;
            } else if ((matcher = Command.getMatcher(input, Command.SELECT_UNIT)) != null) {
                if(selectUnit(matcher).equals(GameMenuMessages.SUCCESS))
                    return MenuNames.SELECT_UNIT_MENU;
            } else if ((matcher = Command.getMatcher(input, Command.NEXT_TURN)) != null) {
                GameMenuController.nextTurn();
            } else if (Command.getMatcher(input, Command.ENTER_TRADE_MENU) != null) {
                enterTradeMenu();
                return MenuNames.TRADE_MENU;
            } else if (Command.getMatcher(input, Command.ENTER_SHOP_MENU) != null) {
                return MenuNames.SHOP_MENU;
            } else if (Command.getMatcher(input, Command.BACK_MAIN_MENU) != null) {
                return MenuNames.MAIN_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void enterTradeMenu() {
        GameData gameData = GameMenuController.getGameData();
        System.out.println("You entered trade menu");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - -");
        for(int i = 0; i < gameData.getEmpires().size(); i++) {
            System.out.println("User" + (i + 1) + ": " + gameData.getEmpires().get(i).getUser().getUsername());
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("New Trade For You:");
        ArrayList<Trade> trades = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).getNewTrades();
        for(int i = 0; i < trades.size(); i++) {
            System.out.print((i + 1) + ") sender player: " + trades.get(i).getSenderPlayer().getNumber());
            System.out.print(" | receiver player: " + trades.get(i).getReceiverPlayer().getNumber());
            System.out.print(" | commodity: " + trades.get(i).getCommodity().getName());
            System.out.print(" | amount: " + trades.get(i).getCount());
            System.out.print(" | price: " + trades.get(i).getPrice());
            System.out.print(" | message: " + trades.get(i).getMessage());
            System.out.println("\n");
            gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).getNewTrades().set(i, null);
        }
    }

    private static void showMap(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int positionX=Integer.parseInt(xMatcher.group(1));
        int positionY=Integer.parseInt(yMatcher.group(1));

        MapMenuController.setShowingMapIndexes(positionX,positionY);

        //todo faratin handle messages and errors
    }

    private static void showPopularityFactors() {
        System.out.println(GameMenuController.showPopularityFactors());
    }

    private static void showPopularity() {
        System.out.println(GameMenuController.showPopularity());
    }

    private static void showFoodList() {
        System.out.println(GameMenuController.showFoodList());
    }

    private static void showFoodRate() {
        System.out.println(GameMenuController.showFoodRate());
    }

    private static void showTaxRate() {
        System.out.println(GameMenuController.showTaxRate());
    }

    private static void setFoodRate(Matcher matcher) {
        int foodRate = Integer.parseInt(matcher.group("rate"));
        GameMenuMessages result = GameMenuController.determinationOfFoodRate(foodRate);
        switch (result) {
            case SUCCESS -> System.out.println("Food rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void setTaxRate(Matcher matcher) {
        int taxRate = Integer.parseInt(matcher.group("rate"));
        GameMenuMessages result = GameMenuController.determinationOfTaxRate(taxRate);
        switch (result) {
            case SUCCESS -> System.out.println("Tax rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static void setFearRate(Matcher matcher) {
        int fearRate = Integer.parseInt(matcher.group("rate"));
        GameMenuMessages result = GameMenuController.determinationOfFearRate(fearRate);
        switch (result) {
            case SUCCESS -> System.out.println("Fear rate has been set successfully!");
            case UNSUCCESS -> System.out.println("There was an error in the process!");
        }
    }

    private static GameMenuMessages selectBuilding(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            return GameMenuMessages.INVALID_COMMAND;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        GameMenuMessages result = GameMenuController.selectBuilding(x, y);
        switch (result) {
            case INVALID_POSITION -> System.out.println("You have chosen an Invalid amount of x or y!");
            case EMPTY_CELL -> System.out.println("This cell is empty!");
            case OTHERS_BUILDINGS -> System.out.println("This building belongs to others!");
            case SUCCESS -> {
                System.out.println("The building is selected successfully!");
            }
        }
        return result;
    }

    private static GameMenuMessages selectUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            return GameMenuMessages.INVALID_COMMAND;
        }
        int xPosition = Integer.parseInt(xMatcher.group(1));
        int yPosition = Integer.parseInt(yMatcher.group(1));

        GameMenuMessages message=GameMenuController.selectUnit(xPosition,yPosition);

        return message;
    }
    private static void showWealth() {
        System.out.println(GameMenuController.showWealth());
    }

    private static void showCommodity() {
        System.out.print(GameMenuController.showCommodity());
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
