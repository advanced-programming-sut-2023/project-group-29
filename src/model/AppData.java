package model;

import java.util.ArrayList;
import java.util.Collections;

public class AppData {
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    public static int delayInLogin = 0;
    public static int stayLoggedIn = 0;

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

    public static int getStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(int stayLoggedIn) {
        AppData.stayLoggedIn = stayLoggedIn;
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
}
