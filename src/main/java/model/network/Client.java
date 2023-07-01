package model.network;

import com.google.gson.Gson;
import controller.menucontrollers.GameController;
import model.GameData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class Client {
    private final int portNumber = 8080;
    private final String host = "localhost"; // for network "192.168.0.100";
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private Thread listeningForInputThread;

    public Client() {
        System.out.println("client created");
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
        NEXT_TURN
    }
}
