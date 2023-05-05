package view.menus;

import controller.MenuNames;
import controller.menucontrollers.ShopMenuController;
import model.Empire;
import view.Command;
import view.messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    public static MenuNames run(Scanner scanner) {
        System.out.println("You have entered shop menu");
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.SHOW_PRICE_LIST)) != null) {
                showPriceList();
            } else if ((matcher = Command.getMatcher(input, Command.BUY)) != null) {
                buy(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.SELL)) != null) {
                sell(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void showPriceList() {
        System.out.println(ShopMenuController.showPriceList());
    }

    private static void buy(Matcher matcher) {
        String resourceName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages result = ShopMenuController.buy(resourceName, amount);
        switch (result) {
            case SUCCESS -> System.out.println("The buy process was successful!");
            case FEW_CASH -> System.out.println("You don't have enough money!");
            case LACK_OF_SPACE -> System.out.println("You don't have enough space for storage!");
        }
    }

    private static void sell(Matcher matcher) {
        String resourceName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages result = ShopMenuController.sell(resourceName, amount);
        switch (result) {
            case SUCCESS -> System.out.println("The sell process was successful!");
            case FEW_AMOUNT -> System.out.println("The amount you have entered is greater than the amount you have!");
        }
    }
}
