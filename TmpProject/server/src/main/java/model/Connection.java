package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Connection extends Thread {
    private Server server;
    private DataInputStream dataInputStream;
    private User user;
    private DataOutputStream dataOutputStream;

    public Connection(DataOutputStream dataOutputStream, DataInputStream dataInputStream, User user, Server server) {
        this.server = server;
        this.dataInputStream = dataInputStream;
        this.user = user;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while (true) {
            String line = "";
            try {
                line = dataInputStream.readUTF();
                Matcher matcher;
                if ((matcher = Command.getMatcher(line, Command.NEW_LOBBY)) != null) {
                    newLobby(matcher);
                } else if ((matcher = Command.getMatcher(line, Command.JOIN_LOBBY)) != null) {
                    joinLobby(matcher);
                } else if (Command.getMatcher(line, Command.LEFT_LOBBY) != null) {
                    leftLobby();
                } else if ((matcher = Command.getMatcher(line, Command.GET_USERNAMES)) != null) {
                    sendUsernames(matcher);
                } else if (Command.getMatcher(line, Command.GET_LOBBIES) != null) {
                    sendLobbyNames();
                } else if (Command.getMatcher(line, Command.IS_ADMIN) != null) {
                    isAdmin();
                } else if (Command.getMatcher(line, Command.CHANGE_LOBBY_ACCESS) != null) {
                    changeLobbyAccess();
                } else if (Command.getMatcher(line, Command.START_GAME) != null) {
                    startGame(user.getLobby().getUsers());
                } else if (Command.getMatcher(line, Command.GET_NUMBER) != null) {
                    sendNumberOfPlayers();
                } else if (Command.getMatcher(line, Command.IS_LOBBY_VALID) != null) {
                    isLobbyValid();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void isLobbyValid() throws IOException {
        boolean isValid = (user.getLobby() != null);
        dataOutputStream.writeBoolean(isValid);
    }

    private void sendNumberOfPlayers() throws IOException {
        dataOutputStream.writeInt(user.getLobby().getUsers().size());
    }

    private void changeLobbyAccess() {
        Lobby lobby = user.getLobby();
        lobby.setPublic(!lobby.isPublic());
    }

    private void isAdmin() throws IOException {
        boolean isAdmin = false;
        if (user.getLobby().getAdmin().equals(user)) isAdmin = true;
        dataOutputStream.writeBoolean(isAdmin);
    }

    private void sendLobbyNames() throws IOException {
        dataOutputStream.writeInt(getPublicSize(server.lobbies));
        for (Lobby lobby : server.lobbies) {
            if (!lobby.isPublic()) continue;
            dataOutputStream.writeUTF(lobby.getName());
            dataOutputStream.writeInt(lobby.getCapacity());
            String users = "";
            for (User user : lobby.getUsers()) {
                users += user.getName() + ",";
            }
            dataOutputStream.writeUTF(users);
        }
    }

    private int getPublicSize(ArrayList<Lobby> lobbies) {
        int counter = 0;
        for (Lobby lobby : lobbies) {
            if (lobby.isPublic()) counter++;
        }
        return counter;
    }

    private void sendUsernames(Matcher matcher) throws IOException {
        String lobbyName = matcher.group("lobbyName");
        Lobby lobby = server.getLobbyByName(lobbyName);
        dataOutputStream.writeInt(lobby.getUsers().size());
        for (User user1 : lobby.getUsers()) {
            dataOutputStream.writeUTF(user1.getName());
        }
    }

    private void leftLobby() {
        Lobby lobby = user.getLobby();
        lobby.remove(user);
        if (lobby.getUsers().size() == 0) server.lobbies.remove(lobby);
    }

    private void joinLobby(Matcher matcher) throws IOException {
        String lobbyName = matcher.group("name");
        Lobby lobby = server.getLobbyByName(lobbyName);
        if (lobby != null) {
            lobby.addUser(user);
            dataOutputStream.writeUTF(lobby.getName());
        }
        else dataOutputStream.writeUTF("null");
        if (lobby.getCapacity() <= lobby.getUsers().size()) startGame(lobby.getUsers());
    }

    private void startGame(ArrayList<User> users) {
        //todo start game
    }

    private void newLobby(Matcher matcher) throws IOException {
        int capacity = Integer.parseInt(matcher.group("number"));
        Lobby lobby = new Lobby(user, capacity, server);
        server.lobbies.add(lobby);
        dataOutputStream.writeUTF(lobby.getName());
    }
}
