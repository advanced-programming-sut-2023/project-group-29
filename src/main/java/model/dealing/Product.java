package model.dealing;

public enum Product implements Tradable{
    ARMOUR("armour"),  //TODO: how we can use this??
    SWORD("sword"),
    BOW("bow"),
    PIKE("pike"),
    WHEAT("wheat"),
    HORSE("horse"),
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
