package model;

import javafx.stage.Stage;
import view.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private Stage stage;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String lobbyName;

    public Client(Stage stage) throws IOException {
        this.stage = stage;
        Socket socket = new Socket("localHost", 8080);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream.writeUTF("ali");//todo in the project it should be removed
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void newLobby(int numberOfPlayers) throws IOException {
        dataOutputStream.writeUTF("new lobby " + numberOfPlayers);
        lobbyName = dataInputStream.readUTF();
    }

    public ArrayList<String> getUsernames() throws IOException {
        ArrayList<String> usernames = new ArrayList<>();
        dataOutputStream.writeUTF("get usernames " + lobbyName);
        int number = dataInputStream.readInt();
        for (int i = 0; i < number; i++) {
            String username = dataInputStream.readUTF();
            usernames.add(username);
        }
        return usernames;
    }

    public HashMap<String, String> getLobbiesNames() throws IOException {
        HashMap<String, String> lobbies = new HashMap<>();
        dataOutputStream.writeUTF("get lobbies");
        int number = dataInputStream.readInt();
        for (int i = 0; i < number; i++) {
            String lobbyName = dataInputStream.readUTF();
            int capacity = dataInputStream.readInt();
            String users = dataInputStream.readUTF();
            lobbies.put(lobbyName, capacity + " : " + users);
        }
        return lobbies;
    }

    public boolean joinLobby(String name) throws IOException {
        dataOutputStream.writeUTF("join " + name);
        lobbyName = dataInputStream.readUTF();
        if (lobbyName.equals("null")) return false;
        return true;
    }

    public void leftLobby() {
        try {
            dataOutputStream.writeUTF("left lobby");
            new MainMenu().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin() throws IOException {
        dataOutputStream.writeUTF("is admin");
        return dataInputStream.readBoolean();
    }

    public void changeLobbyAccess() {
        try {
            dataOutputStream.writeUTF("change lobby access");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() throws IOException {
        if (getNumberOfPlayers() == 1){
            //todo alertmessage
        } else {
            dataOutputStream.writeUTF("start game");
        }
    }

    private int getNumberOfPlayers() throws IOException {
        dataOutputStream.writeUTF("get number of players");
        return dataInputStream.readInt();
    }

    public boolean isLobbyValid() {
        try {
            dataOutputStream.writeUTF("is lobby valid");
            return dataInputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
