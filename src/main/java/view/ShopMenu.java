package view;

import controller.MenuNames;
import controller.ShopMenuController;
import model.Empire;
import view.messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private static Empire loggedInEmpire;

    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if ((matcher = Command.getMatcher(input, Command.SHOW_PRICE_LIST)) != null) {
            showPriceList();
        } else if ((matcher = Command.getMatcher(input, Command.BUY)) != null) {
            buy(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.SELL)) != null) {
            sell(matcher);
        } else {
            System.out.println("Invalid command!");
        }
        return MenuNames.SHOP_MENU;
    }

    private static void showPriceList() {
        System.out.println(ShopMenuController.showPriceList(loggedInEmpire));
    }

    private static void buy(Matcher matcher) {
        String resourceName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages result = ShopMenuController.buy(loggedInEmpire, resourceName, amount);
        switch (result) {
            case SUCCESS -> System.out.println("The buy process was successful!");
            case FEW_CASH -> System.out.println("You don't have enough money!");
            case LACK_OF_SPACE -> System.out.println("You don't have enough space for storage!");
        }
    }

    private static void sell(Matcher matcher) {
        String resourceName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        ShopMenuMessages result = ShopMenuController.sell(loggedInEmpire, resourceName, amount);
        switch (result) {
            case SUCCESS -> System.out.println("The sell process was successful!");
            case FEW_AMOUNT -> System.out.println("The amount you have entered is greater than the amount you have!");
        }
    }
}
