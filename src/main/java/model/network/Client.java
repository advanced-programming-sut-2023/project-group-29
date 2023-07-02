package model.network;

import com.google.gson.Gson;
import controller.menucontrollers.GameController;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import model.AppData;
import model.GameData;
import model.PlayerNumber;
import view.menus.GameGraphicFunctions;
import view.menus.LobbiesMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    private final int portNumber = 8080;
    private final String host = "localhost"; // for network "192.168.0.100";
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private Thread listeningForInputThread;

    public Client() {
        try {
            socket = new Socket(host,portNumber);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String requestStringGenerator(RequestType requestType, String[] args) {
        String request="";
        request+=requestType.name()+" ";
        for(String argument:args)
            request+=argument+" ";

        return request.trim();
    }


    public <T> T getObjectFromJson(Class<T> objectClass, String json) {
        Gson gson = new Gson();

        T object;
        try {
            object = (T)gson.fromJson(json, objectClass);
        } catch (Exception e){
            return null;
        }
        return object;
    }

    public <T> T[] getArrayOfObjectsFromJson(Class<T[]> listClass, String json) {
        Gson gson = new Gson();

        T[] object;
        try {
            object = (T[])gson.fromJson(json, listClass);
        } catch (Exception e){
            return null;
        }
        return object;
    }

    public String requestFormServer(String request) throws IOException {
        dataOutputStream.writeUTF(request);
        return dataInputStream.readUTF();
    }

    public void sendToServer(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

    public enum ListenType{
        LOBBY,
        GAME
    }
    public void startListeningForInput(ListenType listenType) {
        if (listeningForInputThread != null && listeningForInputThread.isAlive())
            listeningForInputThread.interrupt();

        listeningForInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String input;
                    try {
                        input = dataInputStream.readUTF();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(listenType.equals(ListenType.GAME)){
                        GameData gameData=(GameData) getObjectFromJson(GameData.class,input);
                        GameController.updateGameData(gameData);
                    }
                }
            }
        });
        listeningForInputThread.setDaemon(true);
        listeningForInputThread.start();
    }

    public void stopListeningForInput() {
        if (listeningForInputThread != null && listeningForInputThread.isAlive())
            listeningForInputThread.interrupt();
    }

    public enum RequestType {
        LOGIN,
        GET_USER_BY_USERNAME,
        GET_USER_BY_EMAIL,
        SORT_USERS_BY_HIGH_SCORE,
        RANK_OF_USER,
        GET_USERS,
        ADD_USER,
        CHANGE_USER_DATA,
        GET_PUBLIC_MAP_BY_NAME,
        GET_PUBLIC_MAPS,
        START_GAME,
        NEXT_TURN,
        GET_MESSAGE,
        ADD_MESSAGE,
        CHANGE_MESSAGE_DATA
    }

    public void newLobby(int numberOfPlayers) throws IOException {
        dataOutputStream.writeUTF("new lobby " + numberOfPlayers);
        AppData.setLobbyName(dataInputStream.readUTF());
    }

    public ArrayList<String> getUsernames() throws IOException {
        ArrayList<String> usernames = new ArrayList<>();
        dataOutputStream.writeUTF("get usernames " + AppData.getLobbyName());
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
        AppData.setLobbyName(dataInputStream.readUTF());
        if (AppData.getLobbyName().equals("null")) return false;
        return true;
    }

    public void leftLobby() {
        try {
            dataOutputStream.writeUTF("left lobby");
            new LobbiesMenu().start(AppData.getStage());
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("One Player");
            alert.setContentText("Playing with yourself will not be interesting!");
            alert.showAndWait();
        } else {
            dataOutputStream.writeUTF("start game");
            GameData gameData = GameController.getGameData();
            GameController.updateEmpire(PlayerNumber.FIRST);
            String result;
            try {
                String[] playerUsernames = new String[gameData.getNumberOfPlayers()];
                for (int i = 0; i < gameData.getNumberOfPlayers(); i++)
                    playerUsernames[i] = gameData.getEmpires().get(i).getUser().getUsername();
                result = AppData.getClient().requestFormServer(AppData.getClient().requestStringGenerator(Client.RequestType.START_GAME, playerUsernames));
                gameData.setId(result);
                AppData.getClient().startListeningForInput(Client.ListenType.GAME);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
