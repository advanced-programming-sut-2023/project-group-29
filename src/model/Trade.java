package model;

public class Trade {
    private PlayerNumber senderPlayer;
    private PlayerNumber receiverPlayer;
    private int price;
    private Resource resource;
    private int count;

    public Trade(PlayerNumber senderPlayer, PlayerNumber receiverPlayer, int price, Resource resource, int count)
    {
        this.senderPlayer = senderPlayer;
        this.receiverPlayer = receiverPlayer;
        this.price = price;
        this.resource = resource;
        this.count = count;
    }

    public PlayerNumber getSenderPlayer()
    {
        return senderPlayer;
    }

    public PlayerNumber getReceiverPlayer()
    {
        return receiverPlayer;
    }

    public int getPrice()
    {
        return price;
    }

    public Resource getCommodity()
    {
        return resource;
    }

    public int getCount()
    {
        return count;
    }
}
