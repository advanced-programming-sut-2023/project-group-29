package model;

public class Trade {
    private final PlayerNumber senderPlayer;
    private final PlayerNumber receiverPlayer;
    private final int price;
    private final Resource resource;
    private final int count;

    public Trade(PlayerNumber senderPlayer, PlayerNumber receiverPlayer, int price, Resource resource, int count) {
        this.senderPlayer = senderPlayer;
        this.receiverPlayer = receiverPlayer;
        this.price = price;
        this.resource = resource;
        this.count = count;
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

    public Resource getCommodity() {
        return resource;
    }

    public int getCount() {
        return count;
    }
}
