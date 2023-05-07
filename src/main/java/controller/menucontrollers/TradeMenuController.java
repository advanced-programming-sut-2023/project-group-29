package controller.menucontrollers;

import model.Empire;
import model.PlayerNumber;
import model.dealing.*;

import java.util.ArrayList;
import java.util.List;

public class TradeMenuController {
    public static String trade(String type, String amount, String price, String message, int numberOfAnotherPlayer){
        int trueType = 0;
        Tradable tradable = null;
        ArrayList<Tradable> tradableArrayList = new ArrayList<>();
        tradableArrayList.addAll(List.of(Resource.values()));
        tradableArrayList.addAll(List.of(Product.values()));
        tradableArrayList.addAll(List.of(Food.values()));

        for(Tradable myTradable: tradableArrayList) {
            if(myTradable.getName().equals(type)) {
                trueType = 1;
                tradable = myTradable;
                break;
            }
        }
        if(trueType == 0) {
            System.out.println(type);
            return "Invalid type of tradable!";
        }
        if (numberOfAnotherPlayer < 1 || numberOfAnotherPlayer > GameMenuController.getGameData().getEmpires().size()) {
            return "Invalid number of another player!";
        }
        if(Integer.parseInt(amount) < 0) {
            return "Invalid amount!";
        }
        if(Integer.parseInt(price) < 0) {
            return "Invalid price!";
        }
        Trade trade = new Trade(GameMenuController.getGameData().getPlayerOfTurn(),
                PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer - 1),
                Integer.parseInt(price),
                tradable,
                Integer.parseInt(amount),
                message);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (GameMenuController.getGameData().getPlayerOfTurn()).addTradeHistory(trade);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer - 1)).addTrade(trade);
        GameMenuController.getGameData().getEmpireByPlayerNumber
                (PlayerNumber.getPlayerByIndex(numberOfAnotherPlayer - 1)).addNewTrade(trade);
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
        Trade trade = null;
        ArrayList<Trade> modernTrades = new ArrayList<>();
        for(int i = 0; i < trades.size(); i++) {
            if(i == id - 1) {
                trade = trades.get(i);
                if(trade.getCount() * trade.getPrice() > GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).getWealth()) {
                    return "Your wealth is less than the price of your dealing!";
                }
                GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).
                        getTradesHistory().add(trades.get(i));
                continue;
            }
            modernTrades.add(trades.get(i));
        }
        GameMenuController.getGameData().getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).setTrades(modernTrades);
        Empire reciverEmpire = GameMenuController.getGameData().getEmpireByPlayerNumber(trade.getReceiverPlayer());
        Empire senderEmpire = GameMenuController.getGameData().getEmpireByPlayerNumber(trade.getSenderPlayer());
        reciverEmpire.changeTradableAmount(trade.getCommodity(), trade.getCount());
        senderEmpire.changeTradableAmount(trade.getCommodity(), -trade.getCount());
        reciverEmpire.changeWealth(-(trade.getPrice() * trade.getCount()));
        reciverEmpire.changeWealth(trade.getPrice() * trade.getCount());
        return "The trade was accepted";
    }
}

