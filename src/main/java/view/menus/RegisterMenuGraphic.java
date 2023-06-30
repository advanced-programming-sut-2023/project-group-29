package view.menus;

import controller.menucontrollers.LoginMenuController;
import javafx.scene.input.MouseEvent;
import model.AppData;

public class RegisterMenuGraphic {


    public void back(MouseEvent mouseEvent) throws Exception {
        RegisterMenu.getTimelineUsername().stop();
        RegisterMenu.getTimelinePassword().stop();
        new EnterMenu().start(AppData.getStage());
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        RegisterMenu.getUsernameText().setText("");
        RegisterMenu.getPasswordText().setText("");
        RegisterMenu.getEmailText().setText("");
        RegisterMenu.getNicknameText().setText("");
        RegisterMenu.getSloganText().setText("");
        boolean checkSlogan = RegisterMenu.getSloganCheckBox().isSelected();
        if (RegisterMenu.getPasswordTextField().isVisible()) {
            LoginMenuController.createUser(RegisterMenu.getUsernameTextField().getText(), RegisterMenu.getPasswordTextField().getText(), RegisterMenu.getEmailTextField().getText(), RegisterMenu.getNicknameTextField().getText(), RegisterMenu.getSloganTextField().getText(), checkSlogan);
        }
        else {
            LoginMenuController.createUser(RegisterMenu.getUsernameTextField().getText(), RegisterMenu.getPasswordField().getText(), RegisterMenu.getEmailTextField().getText(), RegisterMenu.getNicknameTextField().getText(), RegisterMenu.getSloganTextField().getText(), checkSlogan);
        }
    }

    public void visiblePass(MouseEvent mouseEvent) {
        if (RegisterMenu.getPasswordField().isVisible()) {
            RegisterMenu.getPasswordField().setVisible(false);
            RegisterMenu.getPasswordTextField().setText(RegisterMenu.getPasswordField().getText());
            RegisterMenu.getPasswordTextField().setVisible(true);
        }
        else {
            RegisterMenu.getPasswordField().setVisible(true);
            RegisterMenu.getPasswordField().setText(RegisterMenu.getPasswordTextField().getText());
            RegisterMenu.getPasswordTextField().setVisible(false);
        }
    }

    public void randomPass(MouseEvent mouseEvent) {
        String randomPass = LoginMenuController.createRandomPassword();
        RegisterMenu.getPasswordTextField().setText(randomPass);
        RegisterMenu.getPasswordField().setText(randomPass);
    }

}
