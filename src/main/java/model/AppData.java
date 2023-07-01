package model;

import com.google.gson.Gson;
import javafx.stage.Stage;
import model.network.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AppData {
    //private static final String usersDataBaseFilePath = "./src/main/dataFiles/UsersDataBase.json";
    private static final int screenWidth = 1080;
    private static final int screenHeight = 720;
    private static final ArrayList<MapTemplate> mapTemplates = new ArrayList<>();
    //private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private static int delayInLogin = 0;
    private static Stage stage;
    private static Client client;//todo set

    public static User getUserByUsername(String username){
        String resultString;
        try {
            resultString=client.requestFormServer(client.requestStringGenerator(Client.RequestType.GET_USER_BY_USERNAME,new String[]{username}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return (User) client.getObjectFromJson(User.class, resultString);
        } catch (Exception e){
            return null;
        }
    }

    public static User getUserByEmail(String email){
        String resultString;
        try {
            resultString=client.requestFormServer(client.requestStringGenerator(Client.RequestType.GET_USER_BY_EMAIL,new String[]{email}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return (User) client.getObjectFromJson(User.class, resultString);
        } catch (Exception e){
            return null;
        }
    }

    public static int getDelayInLogin() {
        return delayInLogin;
    }

    public static void setDelayInLogin(int delayInLogin) {
        AppData.delayInLogin = delayInLogin;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AppData.currentUser = currentUser;
    }

    public static ArrayList<User> sortUserByHighScore() {
        String resultString;
        try {
            resultString=client.requestFormServer(client.requestStringGenerator(Client.RequestType.SORT_USERS_BY_HIGH_SCORE,new String[]{}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return new ArrayList<User>(Arrays.asList(client.getArrayOfObjectsFromJson(User[].class, resultString)));
        } catch (Exception e){
            return null;
        }
    }

//    public static int rankOfUser(ArrayList<User> sortedUsers, String username) {
//        for (int i = 0; i < sortedUsers.size(); i++) {
//            if (sortedUsers.get(i).getUsername().equals(username)) {
//                return i + 1;
//            }
//        }
//        return 0;
//    }

    public static ArrayList<User> getUsers() {
        String resultString;
        try {
            resultString=client.requestFormServer(client.requestStringGenerator(Client.RequestType.GET_USERS,new String[]{}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return new ArrayList<User>(Arrays.asList(client.getArrayOfObjectsFromJson(User[].class, resultString)));
        } catch (Exception e){
            return null;
        }
    }

    public static void addUser(User user){

        Gson gson=new Gson();
        String userString=gson.toJson(user);

        try {
            client.sendToServer(client.requestStringGenerator(Client.RequestType.ADD_USER,new String[]{userString}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUserData(User user){

        Gson gson=new Gson();
        String userString=gson.toJson(user);

        try {
            client.sendToServer(client.requestStringGenerator(Client.RequestType.CHANGE_USER_DATA,new String[]{userString}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//
//    public static String getUsersDataBaseFilePath() {
//        return usersDataBaseFilePath;
//    }
//

//    public static boolean checkStayLoggedIn() {   //todo
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getStayLoggedIn() == 1) {
//                AppData.setCurrentUser(users.get(i));
//                return true;
//            }
//        }
//        return false;
//    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AppData.stage = stage;
    }

    public static ArrayList<MapTemplate> getMapTemplates() {
        return mapTemplates;
    }
}
