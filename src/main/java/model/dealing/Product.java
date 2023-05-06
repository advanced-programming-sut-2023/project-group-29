package model.dealing;

public enum Product implements Tradable{
    ARMOUR("armour"),  //TODO: how we can use this??
    SWORD("sword"),
    BOW("bow"),
    PIKE("pike"),
    WHEAT("wheat"),
    APPLE("apple"),
    CHEESE("cheese"),
    HORSE("horse"),
    GRAIN("grain"),
    MEAT("meat"),
    FLOUR("flour"),
    BREAD("bread"),
    BEER("beer"),
    ;
    private final String name;

    Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
