package model.dealing;

public enum Food implements Tradable{
    APPLE("apple"),
    MEAT("meat"),
    BREAD("bread"),
    CHEESE("cheese"),

    ;

    private final String name;

    Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
