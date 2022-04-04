package com.sontendbox.fight;

import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    //private Fighter combantant1;
    //private Fighter combantant2;
    private boolean isHuman;
    private Scanner scanner = new Scanner(System.in);

    public void execute() {
        greet();
        numPlayersPrompt();
        weaponSelection();
        movePrompt();
        updateScreen();
    }

    private void updateScreen() {

    }

    private void movePrompt() {
        System.out.println("Select Attack");
        String attack = scanner.nextLine();
        System.out.println(attack);
    }

    private void weaponSelection() {
        System.out.print("Select weapon. Current weapon availability is limited to : " + Weapon.FIST.getPrintName());
        String weapon = scanner.next();
        System.out.println(weapon);
    }

    private void numPlayersPrompt() {
        System.out.print("Enter number of players (0-2) players allowed: ");
        boolean isValid = false;
        while (isValid) {
            int numPlayers = Integer.parseInt(scanner.nextLine());
            if(numPlayers < 0)

            switch (numPlayers) {
                case 0:
                    System.out.println("Battle is between 2 computer controlled fighters");
                    break;
                case 1:
                    System.out.println("Battle is between 1 human controlled fighter and 1 computer controlled fighter ");
                    break;
                case 2:
                    System.out.println("Battle is between 2 human controlled fighters");
                    break;
            }
        }
    }

    private void greet() {
        System.out.println("************************************************");
        System.out.println("*                                              *");
        System.out.println("* WELCOME T0 CONSOLE COMBAT! PREPARE TO FIGHT! *");
        System.out.println("*                                              *");
        System.out.println("************************************************");
    }
}