package model;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SaveAndLoad {

    //sample code:

//    SaveAndLoad.saveData(user, AppData.usersDataBaseFilePath);
//
//    user=(User) SaveAndLoad.loadData(AppData.usersDataBaseFilePath,User.class);
//    if(user!=null)
//        System.out.println(user.getSlogan());


    public static void saveData(Object object, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // or     new Gson()     for ordinary saving

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(object, fileWriter);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static <T> Object loadData(String filePath, Class<T> objectClass) {

        Gson gson = new Gson();

        try (FileReader fileReader = new FileReader(filePath)) {
            return gson.fromJson(fileReader, objectClass);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }

    public static String hashString(String string) {
        String hashedString = Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
        return hashedString;
    }
}
