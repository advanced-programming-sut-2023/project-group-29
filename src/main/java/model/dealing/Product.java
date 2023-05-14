package model.dealing;

public enum Product implements Tradable {
    ARMOUR("armour"),
    SWORD("sword"),
    BOW("bow"),
    PIKE("pike"),
    HORSE("horse"),
    WHEAT("wheat"),
    GRAIN("grain"),
    FLOUR("flour"),
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
