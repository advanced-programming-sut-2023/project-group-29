package controller.menucontrollers;

import model.GameData;
import model.dealing.Trade;

public class TradeMenuController {
    //TODO: should be linked to storage!
    public static String trade(String type, String amount, String price, String message) {
        //TODO CHECK TYPE
        if(Integer.parseInt(amount) < 0) {
            return "Invalid amount!";
        }
        if(Integer.parseInt(price) < 0) {
            return "Invalid price!";
        }
        //Trade trade = new Trade(GameData.getCurrentUser(), )
        return null;
    }

    public static String showTradeList() {
        return null;
    }

    public static String showTradeHistory() {
        return null;
    }

    public static String acceptTrade(String id) {
        return null;
    }
}
