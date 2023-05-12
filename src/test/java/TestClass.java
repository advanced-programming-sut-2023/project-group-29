import controller.menucontrollers.LoginMenuController;
import model.AppData;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.menus.LoginMenu;

public class TestClass {
    @Test
    public void experimentalTest() {
        User user = new User("ali", "passP@ss1", "ali", "a@a.a", "heb", "1 ali");
        AppData.addUser(user);
        Assertions.assertEquals(AppData.getUserByUsername("ali"), user);
    }
    @Test
    public void checkWeakPassword() {
        int a = LoginMenuController.checkWeakPassword("pass");
        Assertions.assertEquals(a , 1);
    }

}
