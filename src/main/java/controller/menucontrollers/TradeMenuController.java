package controller.menucontrollers;

import model.PlayerNumber;
import model.dealing.Product;
import model.dealing.Resource;
import model.dealing.Tradable;
import model.dealing.Trade;

import java.util.ArrayList;

public class TradeMenuController {
    public static String trade(String type, String amount, String price, String message, int numberOfAnotherPlayer){
        int trueType = 0;
        Tradable tradable = null;
        for(Resource myResource: Resource.values()) {
            if(myResource.getName().equals(type)) {
                trueType = 1;
                tradable = myResource;
                break;
            }
        }
        for(Product myproduct: Product.values()) {
            if(myproduct.getName().equals(type)) {
                trueType = 1;
                tradable = myproduct;
                break;
            }
        }
        if(trueType == 0) {
            System.out.println(type);
            return "Invalid type of tradable!";
        }
        if (numberOfAnotherPlayer < 0 || numberOfAnotherPlayer > 7) {
            return "Invalid number of another player!";
        }
        if(Integer.parseInt(amount) < 0) {
            return "Invalid amount!";
        }
        if(Integer.parseInt(price) < 0) {
            return "Invalid price!";
        }
        Trade trade = new Trade(GameMenuController.getGameData().getPlayerOfTurn(),
                PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer),
                Integer.parseInt(price),
                tradable,
                Integer.parseInt(amount),
                message);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (GameMenuController.getGameData().getPlayerOfTurn()).addTradeHistory(trade);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer)).addTrade(trade);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer)).addNewTrade(trade);
        return "Your trade was recorded";
    }

    public static String showTradeList() {
        ArrayList<Trade> trades = GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).getTrades();
        String output = "";
        for(int i = 0; i < trades.size(); i++) {
            output += (i + 1) + ") sender player: " + trades.get(i).getSenderPlayer().getNumber();
            output += " | receiver player: " + trades.get(i).getReceiverPlayer().getNumber();
            output += " | commodity: " + trades.get(i).getCommodity().getName();
            output += " | amount: " + trades.get(i).getCount();
            output += " | price: " + trades.get(i).getPrice();
            output += " | message: " + trades.get(i).getMessage();
            output += "\n";
        }
        return output;
    }

    public static String showTradeHistory() {
        ArrayList<Trade> trades = GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).getTradesHistory();
        String output = "";
        for(int i = 0; i < trades.size(); i++) {
            output += (i + 1) + ") sender player: " + trades.get(i).getSenderPlayer().getNumber();
            output += " | receiver player: " + trades.get(i).getReceiverPlayer().getNumber();
            output += " | commodity: " + trades.get(i).getCommodity().getName();
            output += " | amount: " + trades.get(i).getCount();
            output += " | price: " + trades.get(i).getPrice();
            output += " | message: " + trades.get(i).getMessage();
            output += "\n";
        }
        return output;
    }

    public static String acceptTrade(int id) {
        ArrayList<Trade> trades = GameMenuController.getGameData().
                getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).getTrades();
        if(id < 1 || id > trades.size()) {
            return "Wrong id!";
        }
        ArrayList<Trade> modernTrades = new ArrayList<>();
        for(int i = 0; i < trades.size(); i++) {
            if(i == id - 1) {
                GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).
                        getTradesHistory().add(trades.get(i));
                continue;
            }
            modernTrades.add(trades.get(i));
        }
        GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).setTrades(modernTrades);
        return "The trade was accepted";
    }
}

