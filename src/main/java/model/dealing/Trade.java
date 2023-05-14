package model.dealing;

import model.PlayerNumber;

public class Trade {
    private final PlayerNumber senderPlayer;
    private final PlayerNumber receiverPlayer;
    private final int price;
    private final Tradable tradable;
    private final int count;
    private final String message;

    public Trade(PlayerNumber senderPlayer, PlayerNumber receiverPlayer,
                 int price, Tradable tradable, int count, String message) {
        this.senderPlayer = senderPlayer;
        this.receiverPlayer = receiverPlayer;
        this.price = price;
        this.tradable = tradable;
        this.count = count;
        this.message = message;
    }

    public PlayerNumber getSenderPlayer() {
        return senderPlayer;
    }

    public PlayerNumber getReceiverPlayer() {
        return receiverPlayer;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    public Tradable getCommodity() {
        return tradable;
    }
}
