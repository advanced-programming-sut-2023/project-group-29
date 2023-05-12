package model;

import controller.menucontrollers.GameMenuController;

import java.util.ArrayList;

public class User {
    private static ArrayList<String> questions = new ArrayList<>();

    private String username;
    private String password;
    private String nickname;
    private String email;
    //recovery question
    private String slogan;
    private final String securityQuestion;
    private int highScore = 0;

    public User(String username, String password, String nickname, String email, String slogan, String securityQuestion) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.securityQuestion = securityQuestion;
    }

    public static ArrayList<String> getQuestions() {
        return questions;
    }

    public static void setQuestions(ArrayList<String> questions) {
        User.questions = questions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User)obj).username.equals(this.username);
        }
        return false;
    }

    public Empire getEmpire() {
        GameData gameData = GameMenuController.getGameData();
        for (Empire empire: gameData.getEmpires()){
            if (empire.getUser().equals(this)) return empire;
        }
        return null;
    }
}
