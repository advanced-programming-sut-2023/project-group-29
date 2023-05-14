package view.menus;

import controller.MenuNames;
import controller.menucontrollers.TradeMenuController;
import view.Command;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeMenu {
    public static MenuNames run(Scanner scanner) {
        Matcher matcher;
        String input = scanner.nextLine();
        if ((matcher = Command.getMatcher(input, Command.TRADE)) != null) {
            trade(matcher);
        }
        else if (Command.getMatcher(input, Command.SHOW_TRADE_LIST) != null) {
            showTradeList();
        }
        else if ((matcher = Command.getMatcher(input, Command.TRADE_ACCEPT)) != null) {
            tradeAccept(matcher);
        }
        else if (Command.getMatcher(input, Command.TRADE_HISTORY) != null) {
            tradeHistory();
        }
        else if (Command.getMatcher(input, Command.BACK_GAME_MENU) != null) {
            System.out.println("You entered game menu");
            return MenuNames.GAME_MENU;
        }
        else {
            System.out.println("Invalid command!");
        }
        return MenuNames.TRADE_MENU;
    }

    private static void trade(Matcher matcher) {
        String input = matcher.group(0);
        Matcher matcherExistType = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher matcherExistAmount = Pattern.compile("-a\\s+(\\d+)").matcher(input);
        Matcher matcherExistPrice = Pattern.compile("-p\\s+(\\d+)").matcher(input);
        Matcher matcherExistMessage = Pattern.compile("-m\\s+(\\S[^-]+\\w)").matcher(input);
        Matcher matcherExistNumberOfAnotherPlayer = Pattern.compile("-n\\s+(\\d)").matcher(input);
        if (!(matcherExistType.find() && matcherExistAmount.find() &&
                matcherExistPrice.find() && matcherExistMessage.find() && matcherExistNumberOfAnotherPlayer.find())) {
            System.out.println("Invalid command!");
            return;
        }
        System.out.print(TradeMenuController.trade(matcherExistType.group(1),
                matcherExistAmount.group(1),
                matcherExistPrice.group(1),
                matcherExistMessage.group(1),
                Integer.parseInt(matcherExistNumberOfAnotherPlayer.group(1))));
    }

    private static void showTradeList() {
        System.out.println(TradeMenuController.showTradeList());
    }

    private static void tradeAccept(Matcher matcher) {
        String input = matcher.group(0);
        Matcher existIdMatcher = Pattern.compile("-i (\\d+)").matcher(input);
        if (!(existIdMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int id = Integer.parseInt(existIdMatcher.group(1));
        System.out.println(TradeMenuController.acceptTrade(id));
    }

    private static void tradeHistory() {
        System.out.println(TradeMenuController.showTradeHistory());
    }
}
