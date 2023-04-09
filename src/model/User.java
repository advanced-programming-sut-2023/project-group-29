package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<String> questions=new ArrayList<>();
    static {    //question initialize

    }
    private String username;
    private String password;
    private String nickname;
    private String email;
    //recovery question
    private String Slogan;



    public static ArrayList<String> getQuestions()
    {
        return questions;
    }

    public static void setQuestions(ArrayList<String> questions)
    {
        User.questions = questions;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getSlogan()
    {
        return Slogan;
    }

    public void setSlogan(String slogan)
    {
        Slogan = slogan;
    }
}
