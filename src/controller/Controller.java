package controller;

import view.GameMenu;
import view.LoginMenu;

import java.util.Scanner;

public class Controller
{
    public static void run()
    {
        Scanner scanner= new Scanner(System.in);
        MenuNames menuNames= MenuNames.LOGIN_MENU;

        loop:
        while (true)
        {
            switch (menuNames)
            {
                case LOGIN_MENU:
                    menuNames= LoginMenu.run(scanner);
                    break;
                case GAME_MENU:
                    menuNames= GameMenu.run(scanner);
                    break;
            }
        }
    }
}
