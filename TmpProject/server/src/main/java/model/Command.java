package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    NEW_LOBBY("new lobby (?<number>\\d+)"),
    JOIN_LOBBY("join (?<name>\\S+)"),
    LEFT_LOBBY("left lobby"),
    GET_USERNAMES("get usernames (?<lobbyName>\\S+)"),
    GET_LOBBIES("get lobbies"),
    IS_ADMIN("is admin"),
    CHANGE_LOBBY_ACCESS("change lobby access"),
    START_GAME("start game"),
    GET_NUMBER("get number of players"),
    IS_LOBBY_VALID("is lobby valid"),
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
