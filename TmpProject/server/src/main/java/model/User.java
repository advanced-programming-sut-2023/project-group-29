package model;

public class User {
    private String name;
    private Lobby lobby;

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
