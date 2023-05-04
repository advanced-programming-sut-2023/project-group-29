package model.dealing;

public enum Product {
    ARMOUR("armour"),
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
}
