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
    ADD_PLAYER_TO_GAME("\\s*add\\s+to\\s+game\\s+-u\\s+(?<username>\\S+)\\s*"),
    //pointive: REMOVE_PLAYER_FROM_GAME(""),
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
    SHOW_MAP("show map -x (?<xAmount>\\d+) -y (?<yAmount>\\d+)\\s*"), //todo
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SHOW_FOOD_RATE("\\s*food\\s+rate\\s+show\\s*"),
    SHOW_TAX_RATE("\\s*tax\\s+rate\\s+show\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"), //TODO: ERROr for big number
    SET_TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    SET_FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    DROP_BUILDING("\\s*dropbuilding\\s+-x\\s+(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s+-type\\s+(?<type>\\S+)\\s*"),
    //TODO: جابجایی
    SELECT_BUILDING("\\s*select\\s+building\\s+-x\\s+(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s*"),
    SELECT_UNIT("\\s*select\\s+unit\\s+-x\\s+(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s*"),
    TRADE("\\s*trade\\s+-[tapmn]\\s+([^-]*\\w)\\s+-[atpmn]\\s+([^-]*\\w)\\s+-[ptamn]\\s+"
            + "([^-]*\\w)\\s+-[tapmn]\\s+([^-]*\\w)\\s+-[ntapm]\\s+([^-]*\\w)\\s*"),
    SHOW_TRADE_LIST("\\s*trade\\s+list\\s*"),
    TRADE_ACCEPT("\\s*trade\\s+accept\\s+-i\\s+(?<id>\\S+)\\s*"),
    TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    MOVE_MAP("\\s*move\\s+((up)|(down)|(right)|(left))"),  //TODO more than one
    SET_TEXTURE(""),
    CLEAR(""),
    DROP_ROCK(""),
    DROP_TREE(""),
    ADMIN_DROP_BUILDING("\\s*admin\\s+dropbuilding\\s+-x\\s+" +
            "(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s+-type\\s+(?<type>\\S+)\\s*"),
    DROP_UNIT("\\s*dropunit\\s+-x\\s+(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s+-t\\s+(?<type>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    CREATE_UNIT("\\s*createUnit\\s+-t\\s+(?<type>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"), //TODO
    REPAIR_BUILDING("\\s*repair\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+-x\\s+(?<xPosition>\\d+)\\s+-y\\s+(?<yPosition>\\d+)\\s*"),
    PATROL_UNIT(""),
    SET_STATE_OF_UNIT(""),
    MAKE_UNIT_ATTACKING("\\s*make\\s+unit\\s+attacking\\s*"), //TODO: HANDLE
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD_EQUIPMENT(""),
    DISBAND_UNIT(""),
    SHOW_PRICE_LIST("\\s*show\\s+price\\s+list\\s*"),
    BUY("\\s*buy\\s+-u\\s+(?<name>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*"),
    SELL("\\s*sell\\s+-u\\s+(?<name>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*"),
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
