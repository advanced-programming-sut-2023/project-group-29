package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server();
    }

    ArrayList<User> loggedInUsers = new ArrayList<>();
    public ArrayList<Lobby> lobbies = new ArrayList<>();

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String name = dataInputStream.readUTF();
            User user = new User(name);
            loggedInUsers.add(user);
            Connection connection = new Connection(dataOutputStream, dataInputStream, user, this);
            connection.start();
        }
    }

    public synchronized Lobby getLobbyByName(String lobbyName) {
        for (Lobby lobby : lobbies) {
            if (lobby.getName().equals(lobbyName)) return lobby;
        }
        return null;
    }

    public void destroy(String lobbyName) {
        Lobby lobby = getLobbyByName(lobbyName);
        for (User user : lobby.getUsers()) {
            user.setLobby(null);
        }
        lobbies.remove(lobby);
    }
}
