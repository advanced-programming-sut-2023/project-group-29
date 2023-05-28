package model;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class AppData {
    private static final String usersDataBaseFilePath = "./src/main/dataFiles/UsersDataBase.json";
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private static int delayInLogin = 0;

    private static final int screenWidth=1080;
    private static final int screenHeight=720;
    private static Stage stage;


    public static User getUserByUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(email)) {
                return users.get(i);
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
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
        ArrayList<User> sortedUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            sortedUsers.add(users.get(i));
        }
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (sortedUsers.get(i).getHighScore() < sortedUsers.get(j).getHighScore()) {
                    Collections.swap(sortedUsers, i, j);
                }
            }
        }
        return sortedUsers;
    }

    public static int rankOfUser(ArrayList<User> sortedUsers, String username) {
        for (int i = 0; i < sortedUsers.size(); i++) {
            if (sortedUsers.get(i).getUsername().equals(username)) {
                return i + 1;
            }
        }
        return 0;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        AppData.users = users;
    }

    public static String getUsersDataBaseFilePath() {
        return usersDataBaseFilePath;
    }

    public static boolean checkStayLoggedIn() {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStayLoggedIn() == 1) {
                AppData.setCurrentUser(users.get(i));
                return true;
            }
        }
        return false;
    }

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
}
