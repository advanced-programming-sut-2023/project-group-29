package controller.menucontrollers;

import model.Empire;
import model.GameData;
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
            return "Invalid type of tradable!\n";
        }
        if (numberOfAnotherPlayer < 1 || numberOfAnotherPlayer > GameMenuController.getGameData().getEmpires().size()) {
            return "Invalid number of another player!\n";
        }
        if(Integer.parseInt(amount) < 0) {
            return "Invalid amount!\n";
        }
        if(Integer.parseInt(price) < 0) {
            return "Invalid price!\n";
        }
        int amountInt = Integer.parseInt(amount);
        GameData gameData = GameMenuController.getGameData();
        Empire empire = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn());
        if (empire.getTradableAmount(tradable) < amountInt) {
            return "You don't have enough commodity!\n";
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
        return "Your trade was recorded\n";
    }

    public static String showTradeList() {
        GameData gameData = GameMenuController.getGameData();
        ArrayList<Trade> trades = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).getTrades();
        return makeOutput(trades);
    }

    private static String makeOutput(ArrayList<Trade> trades) {
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
        GameData gameData = GameMenuController.getGameData();
        ArrayList<Trade> trades = gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).getTradesHistory();
        return makeOutput(trades);
    }

    public static String acceptTrade(int id) {
        ArrayList<Trade> trades = GameMenuController.getGameData().
                getEmpireByPlayerNumber(GameMenuController.getGameData().getPlayerOfTurn()).getTrades();
        if(id < 1 || id > trades.size()) {
            return "Wrong id!";
        }
        Trade trade = null;
        ArrayList<Trade> modernTrades = new ArrayList<>();
        GameData gameData = GameMenuController.getGameData();
        for(int i = 0; i < trades.size(); i++) {
            if(i == id - 1) {
                trade = trades.get(i);
                if(trade.getCount() * trade.getPrice() >
                        gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).getWealth()) {
                    return "Your wealth is less than the price of your dealing!";
                }
                Empire senderEmpire = gameData.getEmpireByPlayerNumber(trade.getSenderPlayer());
                if(senderEmpire.getTradableAmount(trade.getCommodity()) < trade.getCount()) {
                    return "The sender doesn't have enough commodity now!";
                }
                gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).
                        getTradesHistory().add(trades.get(i));
                continue;
            }
            modernTrades.add(trades.get(i));
        }
        gameData.getEmpireByPlayerNumber(gameData.getPlayerOfTurn()).setTrades(modernTrades);
        Empire reciverEmpire = gameData.getEmpireByPlayerNumber(trade.getReceiverPlayer());
        Empire senderEmpire = gameData.getEmpireByPlayerNumber(trade.getSenderPlayer());
        reciverEmpire.changeTradableAmount(trade.getCommodity(), trade.getCount());
        senderEmpire.changeTradableAmount(trade.getCommodity(), -trade.getCount());
        reciverEmpire.changeWealth(-(trade.getPrice() * trade.getCount()));
        senderEmpire.changeWealth(trade.getPrice() * trade.getCount());
        return "The trade was accepted";
    }
}

