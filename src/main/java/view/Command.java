package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    USER_CREATE("\\s*user\\s+create.+"),
    EXIT("\\s*exit\\s*"),
    PICK_QUESTION(""),
    LOGIN("\\s*user\\s+login\\s+-[up]\\s+(\\S+)\\s+-[up]\\s+(\\S+)( --stay-logged-in)?\\s*"),
    FORGET_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_GAME("\\s*start\\s+a\\s+new\\s+game\\s*"),
    ADD_PLAYER_TO_GAME("\\s*add\\s+to\\s+game\\s+-u\\s+(?<username>\\S+)\\s*"),
    CHOOSE_MAP("\\s*choose\\s+map\\s+(?<index>\\d+)\\s*"),
    READY("\\s*ready\\s*"),
    CANCEL("\\s*cancel\\s*"),
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+(?<username>\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+-[on]\\s+(\\S+)\\s+-[no]\\s+(\\S+)\\s*"),
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
    SHOW_MAP("\\s*show\\s+map\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    SHOW_DETAILS("\\s*show\\s+details\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SHOW_FOOD_RATE("\\s*food\\s+rate\\s+show\\s*"),
    SHOW_TAX_RATE("\\s*tax\\s+rate\\s+show\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    SET_TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    SET_FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rate>-?\\d+)\\s*"),
    DROP_BUILDING("\\s*drop\\s+building\\s+" +
            "-[xytn]\\s+(\\S+)\\s+-[yxtn]\\s+(\\S+)\\s+-[txyn]\\s+(\\S+)\\s+-[txyn]\\s+(\\S+)\\s*"),
    SELECT_BUILDING("\\s*select\\s+building\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    SELECT_UNIT("\\s*select\\s+unit\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    TRADE("\\s*trade\\s+-[tapmn]\\s+([^-]*\\w)\\s+-[atpmn]\\s+([^-]*\\w)\\s+-[ptamn]\\s+"
            + "([^-]*\\w)\\s+-[tapmn]\\s+([^-]*\\w)\\s+-[ntapm]\\s+([^-]*\\w)\\s*"),
    SHOW_TRADE_LIST("\\s*trade\\s+list\\s*"),
    TRADE_ACCEPT("\\s*trade\\s+accept\\s+-i\\s+(?<id>\\S+)\\s*"),
    TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    MOVE_MAP("\\s*move(\\s+\\S+(\\s+\\d+)?)+\\s*"),
    SET_BLOCK_TEXTURE("\\s*set\\s+texture\\s+-[xyt]\\s+(\\S+)\\s+-[yxt]\\s+(\\S+)\\s+-[txy]\\s+(\\S+)\\s*"),
    SET_PART_OF_BLOCK_TEXTURE("\\s*set\\s+texture\\s+-(x1|y1|x2|y2|t)\\s+(\\S+)\\s+-(x1|y1|x2|y2|t)\\s+(\\S+)\\s+-(x1|y1|x2|y2|t)\\s+(\\S+)\\s+-(x1|y1|x2|y2|t)\\s+(\\S+)\\s+-(x1|y1|x2|y2|t)\\s+(\\S+)\\s*"),
    CLEAR("\\s*clear\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    DROP_ROCK("\\s*drop\\s+rock\\s+-[xyd]\\s+(\\S+)\\s+-[yxd]\\s+(\\S+)\\s+-[dxy]\\s+(\\S+)\\s*"),
    DROP_TREE("\\s*drop\\s+tree\\s+-[xyt]\\s+(\\S+)\\s+-[yxt]\\s+(\\S+)\\s+-[txy]\\s+(\\S+)\\s*"),
    CREATE_BUILDING("\\s*create\\s+building\\s+-[xyt]\\s+(\\S+)\\s+-[yxt]\\s+(\\S+)\\s+-[txy]\\s+(\\S+)\\s*"),
    DROP_UNIT("\\s*drop\\s+unit\\s+-[xytcn]\\s+(\\S+)\\s+-[xytcn]\\s+(\\S+)\\s+" +
            "-[xytcn]\\s+(\\S+)\\s+-[xytcn]\\s+(\\S+)\\s+-[xytcn]\\s+(\\S+)\\s*"),
    CREATE_UNIT("\\s*create\\s+unit\\s+-[tc]\\s+(\\S+)\\s+-[ct]\\s+(\\S+)\\s*"),
    DROP_LADDER("\\s*drop\\s+ladder\\s*"),
    CHANGE_BRIDGE_STATE("\\s*change\\s+state\\s+of\\s+this\\s+bridge\\s*"),
    REPAIR_BUILDING("\\s*repair\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+-[xy]\\s+(\\d+)\\s+-[xy]\\s+(\\d+)\\s*"),
    PATROL_UNIT("\\s*patrol\\s+unit\\s+-(x1|x2|y1|y2)\\s+(\\d+)\\s+-(x1|x2|y1|y2)\\s+(\\d+)\\s+-(x1|x2|y1|y2)\\s+(\\d+)\\s+-(x1|x2|y1|y2)\\s+(\\d+)\\s*"),
    SET_STATE_OF_UNIT("\\s*set\\s+-[xys]\\s+(\\S+)\\s+-[yxs]\\s+(\\S+)\\s+-[sxy]\\s+(\\S+)\\s*"),
    ATTACK("\\s*attack\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s+(?<direction>\\S+)\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+-[xy]\\s+(\\d+)\\s+-[yx]\\s+(\\d+)\\s*"),
    BUILD_EQUIPMENT("\\s*build\\s+-q\\s+(?<equipmentName>\\S+)\\s*"),
    DISBAND_UNIT("\\s*disband\\s+unit\\s*"),
    SHOW_PRICE_LIST("\\s*show\\s+price\\s+list\\s*"),
    BUY("\\s*buy\\s+-[na]\\s+(\\S+)\\s+-[an]\\s+(\\S+)\\s*"),
    SELL("\\s*sell\\s+-[an]\\s+(\\S+)\\s+-[an]\\s+(\\S+)\\s*"),
    ENTER_TRADE_MENU("\\s*enter\\s+trade\\s+menu\\s*"),
    ENTER_SHOP_MENU("\\s*enter\\s+shop\\s+menu\\s*"),
    ENTER_SELECT_MENU("\\s*enter\\s+select\\s+menu\\s*"),
    ENTER_MAP_MENU("\\s*enter\\s+map\\s+menu\\s*"),
    BACK_GAME_MENU("\\s*back\\s*"),
    SHOW_WEALTH("\\s*show\\s+wealth\\s*"),
    SHOW_COMMODITY("\\s*show\\s+commodity\\s*"),
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
