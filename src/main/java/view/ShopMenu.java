package view;

import controller.MenuNames;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
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

        return null;
    }

    private static void showPriceList() {
    }

    private static void buy(Matcher matcher) {
        
    }

    private static void sell(Matcher matcher) {
        
    }
}
