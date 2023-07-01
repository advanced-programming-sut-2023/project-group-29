package model.network;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private Thread listeningForInputThread;

    public Client() {
        System.out.println("client created");
        try {
            socket = new Socket("localhost", 8080);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String requestStringGenerator(RequestType requestType, String[] args) {
        return null;
    }


    public <T> Object getObjectFromJson(Class<T> objectClass, String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, objectClass);
    }

    public <T> T[] getArrayOfObjectsFromJson(Class<T[]> listClass, String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, listClass);
    }

    public String requestFormServer(String request) throws IOException {
        dataOutputStream.writeUTF(request);

        return dataInputStream.readUTF();
    }

    public void sendToServer(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

    public void startListeningForInput(Method method, Object object) {
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

                    try {
                        method.invoke(object, input);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
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
    }
}
