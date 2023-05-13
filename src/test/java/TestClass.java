import controller.menucontrollers.LoginMenuController;
import model.AppData;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.menus.LoginMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void checkWeakPassword1() {
        int a = LoginMenuController.checkWeakPassword("passpass");
        Assertions.assertEquals(1 , a);
    }
    @Test
    public void checkWeakPassword2() {
        int a = LoginMenuController.checkWeakPassword("pas1spass");
        Assertions.assertEquals(1 , a);
    }
    @Test
    public void checkWeakPassword3() {
        int a = LoginMenuController.checkWeakPassword("pass@pass");
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
        Matcher existSloganMatcher = Pattern.compile("-s").matcher("-s heb");
        Matcher usernameMatcher = Pattern.compile("-u\\s+(\\S+)").matcher("-u");
        Matcher passwordMatcher = Pattern.compile("-p\\s+(\\S+)").matcher("-p pass");
        Matcher nicknameMatcher = Pattern.compile("-n\\s+(\\S+)").matcher("-n ali");
        Matcher emailMatcher = Pattern.compile("-email\\s+(\\S+)").matcher("-email a@a.a");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s heb");
        int a = LoginMenuController.checkEmptyFilled(existSloganMatcher, usernameMatcher, passwordMatcher, nicknameMatcher, emailMatcher, sloganMatcher);
        Assertions.assertEquals(1 , a);
    }
    @Test
    public void checkEmptyField2() {
        Matcher existSloganMatcher = Pattern.compile("-s").matcher("-s heb");
        Matcher usernameMatcher = Pattern.compile("-u\\s+(\\S+)").matcher("-u ali");
        Matcher passwordMatcher = Pattern.compile("-p\\s+(\\S+)\\s+(\\S+)").matcher("-p pass");
        Matcher nicknameMatcher = Pattern.compile("-n\\s+(\\S+)").matcher("-n ali");
        Matcher emailMatcher = Pattern.compile("-email\\s+(\\S+)").matcher("-email a@a.a");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s heb");
        int a = LoginMenuController.checkEmptyFilled(existSloganMatcher, usernameMatcher, passwordMatcher, nicknameMatcher, emailMatcher, sloganMatcher);
        Assertions.assertEquals(2 , a);
    }
    @Test
    public void checkEmptyField3() {
        Matcher existSloganMatcher = Pattern.compile("-s").matcher("-s heb");
        Matcher usernameMatcher = Pattern.compile("-u\\s+(\\S+)").matcher("-u ali");
        Matcher passwordMatcher = Pattern.compile("-p\\s+(\\S+)").matcher("-p pass pass");
        Matcher nicknameMatcher = Pattern.compile("-n\\s+(\\S+)").matcher("-n");
        Matcher emailMatcher = Pattern.compile("-email\\s+(\\S+)").matcher("-email a@a.a");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s heb");
        int a = LoginMenuController.checkEmptyFilled(existSloganMatcher, usernameMatcher, passwordMatcher, nicknameMatcher, emailMatcher, sloganMatcher);
        Assertions.assertEquals(3 , a);
    }
    @Test
    public void checkEmptyField4() {
        Matcher existSloganMatcher = Pattern.compile("-s").matcher("-s heb");
        Matcher usernameMatcher = Pattern.compile("-u\\s+(\\S+)").matcher("-u ali");
        Matcher passwordMatcher = Pattern.compile("-p\\s+(\\S+)").matcher("-p pass");
        Matcher nicknameMatcher = Pattern.compile("-n\\s+(\\S+)").matcher("-n ali");
        Matcher emailMatcher = Pattern.compile("-email\\s+(\\S+)").matcher("-email");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s heb");
        int a = LoginMenuController.checkEmptyFilled(existSloganMatcher, usernameMatcher, passwordMatcher, nicknameMatcher, emailMatcher, sloganMatcher);
        Assertions.assertEquals(4 , a);
    }
    @Test
    public void checkEmptyField5() {
        Matcher existSloganMatcher = Pattern.compile("-s").matcher("-s heb");
        Matcher usernameMatcher = Pattern.compile("-u\\s+(\\S+)").matcher("-u ali");
        Matcher passwordMatcher = Pattern.compile("-p\\s+(\\S+)").matcher("-p pass");
        Matcher nicknameMatcher = Pattern.compile("-n\\s+(\\S+)").matcher("-n ali");
        Matcher emailMatcher = Pattern.compile("-email\\s+(\\S+)").matcher("-email a@a.a");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s");
        int a = LoginMenuController.checkEmptyFilled(existSloganMatcher, usernameMatcher, passwordMatcher, nicknameMatcher, emailMatcher, sloganMatcher);
        Assertions.assertEquals(5 , a);
    }
    @Test
    public void checkRandomSlogan() {
        Matcher existSloganMatcher = Pattern.compile("-s").matcher(".+-s.+");
        Matcher sloganMatcher = Pattern.compile("-s\\s+(\\w[^-]+\\S)").matcher("-s random");
        String slogan = LoginMenuController.getSlogan(existSloganMatcher, sloganMatcher);
        Assertions.assertEquals(true, !slogan.equals("random"));
    }
    /*@Test
    public void checkRandomPassword() {
        String password = LoginMenuController.createRandomPassword();
        int a = LoginMenuController.checkWeakPassword(password);
        Assertions.assertEquals(2, a);
    }*/
    @Test
    public void checkEmailFormat1() {
        String email = "aaa.a";
        int a = LoginMenuController.checkEmailFormat(email);
        Assertions.assertEquals(0, a);
    }
    @Test
    public void checkEmailFormat2() {
        String email = "aa@a.a";
        int a = LoginMenuController.checkEmailFormat(email);
        Assertions.assertEquals(1, a);
    }
    @Test
    public void checkEmailFormat3() {
        String email = "aaa@a";
        int a = LoginMenuController.checkEmailFormat(email);
        Assertions.assertEquals(0, a);
    }



}
