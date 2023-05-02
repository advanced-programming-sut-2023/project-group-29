package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    //TODO check option duplication
    //TODO complete regex
    FIND_USER(""),
    USER_CREATE("\\s*user\\s+create.+"),
    EXIT("\\s*exit\\s*"),
    PICK_QUESTION(""),
    LOGIN("\\s*user\\s+login\\s+-[up]\\s+(\\S+)\\s+-[up]\\s+(\\S+)( --stay-logged-in)?\\s*"),
    FORGET_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_GAME("\\s*start\\s+a\\s+new\\s+game\\s*"),
    ADD_PLAYER_TO_GAME("\\s*add\\s+-u\\s+(?<username>\\S+)\\s+to\\s+game\\s*"),
    //TODO: REMOVE_PLAYER_FROM_GAME(""),
    CHOOSE_MAP("\\s*choose\\s+map\\s+(?<index>\\d+)\\s*"),
    READY("\\s*ready\\s*"),
    CANCEL("\\s*cancel\\s*"),
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+(?<username>\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+-[on]\\s+(\\S+)\\s+-[no]\\s+(\\S+)\\s+"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s+-n\\s+(?<nickname>\\S+)\\s*"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s+-e\\s+(?<email>\\S+)\\s*"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>.+\\S)\\s*"),
    REMOVE_SLOGAN("\\s*profile\\s+remove\\s+slogan\\s*"),
    DISPLAY_HIGH_SCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),
    DISPLAY_PROFILE("\\s*profile\\s+display\\s*"),
    BACK_MAIN_MENU("\\s*back\\s*"),

    NEXT_TURN("\\s*next\\s+turn\\s*"),
    SHOW_MAP("show map -x (?<xAmount>\\d+) -y (?<yAmount>\\d+)"),
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    SHOW_FOOD_RATE(""),
    SHOW_TAX_RATE(""),
    SET_FOOD_RATE(""),
    SET_TAX_RATE(""),
    SET_FEAR_RATE(""),
    DROP_BUILDING(""),
    SELECT_BUILDING(""),
    SELECT_UNIT(""),
    TRADE(""),
    SHOW_TRADE_LIST(""),
    TRADE_ACCEPT(""),
    TRADE_HISTORY(""),

    MOVE_MAP(""),
    SET_TEXTURE(""),
    CLEAR(""),
    DROP_ROCK(""),
    DROP_TREE(""),
    //DROP_BUILDING(""),
    DROP_UNIT(""),
    CREATE_UNIT(""),
    REPAIR_BUILDING(""),
    MOVE_UNIT(""),
    PATROL_UNIT(""),
    SET_STATE_OF_UNIT(""),
    MAKE_UNIT_ATTACKING(""),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD_EQUIPMENT(""),
    DISBAND_UNIT(""),
    SHOW_PRICE_LIST(""),
    BUY(""),
    SELL(""),
    ENTER_TRADE_MENU("\\s*enter\\s+trade\\s+menu\\s*"),
    ENTER_SHOP_MENU("\\s*enter\\s+shop\\s+menu\\s*"),
    ENTER_SELECT_MENU("\\s*enter\\s+select\\s+menu\\s*"),
    ENTER_MAP_MENU("\\s*enter\\s+map\\s+menu\\s*"),
    BACK_GAME_MENU("\\s*back\\s*")
    ;
    private final String regex;

    Command(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Command command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        return matcher.matches() ? matcher : null;
    }
}
