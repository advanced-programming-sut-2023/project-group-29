package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    FIND_USER(""),
    USER_CREATE("\\s*user\\s+create.+"),
    EXIT("\\s*exit\\s*"),
    PICK_QUESTION(""),
    LOGIN("\\s*user\\s+login\\s+-[up]\\s+(\\S+)\\s+-[up]\\s+(\\S+)( --stay-logged-in)?\\s*"),
    FORGET_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\\S+)\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s+(?<username>\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+-[on]\\s+(\\S+)\\s+-[no]\\s+(\\S+)\\s+"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s+-n\\s+(?<nickname>\\S+)\\s*"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s+-e\\s+(?<email>\\S+)\\s*"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+-s\\s+(?<slogan>.+)"),
    REMOVE_SLOGAN("\\s*Profile\\s+remove\\s+slogan\\s*"),
    DISPLAY_HIGH_SCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),
    DISPLAY_PROFILE("\\s*profile\\s+display\\s*"),
    BACK_MAIN_MENU("\\s*back\\s*"),

    SHOW_MAP("show map -x (?<xAmount>\\d+) -y (?<yAmount>\\d+)");
    private final String regex;

    Command(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Command command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);

        return matcher.matches() ? matcher : null;
    }
}
