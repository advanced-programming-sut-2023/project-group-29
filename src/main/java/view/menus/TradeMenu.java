package view.menus;

import controller.MenuNames;
import controller.menucontrollers.TradeMenuController;
import org.checkerframework.checker.units.qual.C;
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
        } else if (Command.getMatcher(input, Command.SHOW_TRADE_LIST) != null) {
            showTradeList();
        } else if ((matcher = Command.getMatcher(input, Command.TRADE_ACCEPT)) != null) {
            tradeAccept(matcher);
        } else if ((matcher = Command.getMatcher(input, Command.TRADE_HISTORY)) != null) {
            tradeHistory();
        } else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
            System.out.println("You entered game menu");
            return MenuNames.GAME_MENU;
        } else {
            System.out.println("Invalid command!");
        }
        return MenuNames.TRADE_MENU;
    }

    private static void trade(Matcher matcher) {
        String input = matcher.group(0);
        Matcher matcherExistType = Pattern.compile("-t (\\S+)").matcher(input);
        Matcher matcherExistAmount = Pattern.compile("-a (\\S+)").matcher(input);
        Matcher matcherExistPrice = Pattern.compile("-p (\\S+)").matcher(input);
        Matcher matcherExistMessage = Pattern.compile("-m (\\S+)").matcher(input);
        if(!(matcherExistType.find() && matcherExistAmount.find() &&
                matcherExistPrice.find() && matcherExistMessage.find())) {
            System.out.println("Invalid command!");
            return;
        }
        System.out.println(TradeMenuController.trade(matcherExistType.group(1), matcherExistAmount.group(1),
                matcherExistPrice.group(1), matcherExistMessage.group(1)));
    }

    private static void showTradeList() {
        //TODO: complete it
    }

    private static void tradeAccept(Matcher matcher) {
        //TODO: complete it
    }

    private static void tradeHistory() {
        //TODO: complete it
    }
}
