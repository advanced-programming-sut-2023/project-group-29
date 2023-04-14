package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    FIND_USER(""),
    USER_CREATE(""),
    PICK_QUESTION(""),
    LOGIN(""),
    FORGET_PASSWORD(""),
    LOGOUT("user logout"),
    CHANGE_PROFILE(""),
    CHANGE_PASSWORD(""),
    CHANGE_SLOGAN(""),
    REMOVE_SLOGAN(""),
    DISPLAY_HIGH_SCORE(""),
    DISPLAY_SLOGAN(""),
    DISPLAY_RANK(""),
    DISPLAY_PROFILE(""),

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
