package model.dealing;

public enum Product implements Tradable{
    ARMOUR("armour"),  //TODO: how we can use this??
    SWORD("sword"),  //TODO: how we can use this??
    BOW("bow"), //TODO: how we can use this??
    PIKE("pike"), //TODO: how we can use this??
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
