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
    public void checkLengthOfPassword() {
        int a = LoginMenuController.checkWeakPassword("psP@1");
        Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkWeakPassword() {
        int a = LoginMenuController.checkWeakPassword("passpass");
        Assertions.assertEquals(1 , a);
    }
    @Test
    public void checkPassword() {
        int a = LoginMenuController.checkWeakPassword("passP@1");
        Assertions.assertEquals(2 , a);
    }
    @Test
    public void checkInvalidCommand1() {
        int a = LoginMenuController.checkInvalidCommand("-u");
        Assertions.assertEquals(1 , a);
    }
    @Test
    public void checkInvalidCommand2() {
        int a = LoginMenuController.checkInvalidCommand("-u -p -email -n");
        Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkEmptyField1() {
        //int a = LoginMenuController.checkEmptyFilled();
        //Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkEmptyField2() {
        int a = LoginMenuController.checkInvalidCommand("-u -p -email -n");
        Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkEmptyField3() {
        int a = LoginMenuController.checkInvalidCommand("-u -p -email -n");
        Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkEmptyField4() {
        int a = LoginMenuController.checkInvalidCommand("-u -p -email -n");
        Assertions.assertEquals(0 , a);
    }
    @Test
    public void checkEmptyField5() {
        int a = LoginMenuController.checkInvalidCommand("-u -p -email -n");
        Assertions.assertEquals(0 , a);
    }

}
