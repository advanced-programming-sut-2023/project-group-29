package model;

public class Trade {
    private PlayerNumber senderPlayer;
    private PlayerNumber receiverPlayer;
    private int price;
    private Commodity commodity;
    private int count;

    public Trade(PlayerNumber senderPlayer, PlayerNumber receiverPlayer, int price, Commodity commodity, int count)
    {
        this.senderPlayer = senderPlayer;
        this.receiverPlayer = receiverPlayer;
        this.price = price;
        this.commodity = commodity;
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

    public Commodity getCommodity()
    {
        return commodity;
    }

    public int getCount()
    {
        return count;
    }
}
