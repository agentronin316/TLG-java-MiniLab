package com.sontendbox.fight;

import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    private Fighter combantant1;
    private Fighter combantant2;
    private boolean isHuman;
    private Scanner scanner = new Scanner(System.in);
    private Weapon weapon;
    private int numPlayers = 0;

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
    }

    private void weaponSelection() {
        int i = 1;
        while(i <= 2) {
            System.out.print("Select weapon. Current weapon availability is limited to "
                    + Weapon.FIST.getPrintName());
            String input = scanner.next();
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName()))
                    this.weapon = weapon;
                System.out.println("Weapon chosen: " + weapon.getPrintName());
            }
            switch (i){
                case 1:
                    combantant1 = new Fighter(weapon);
                    i++;
                    break;
                case 2:
                    combantant2 = new Fighter(weapon);
                    i++; break;
            }
        }
    }

    private void numPlayersPrompt() {
        boolean isValid = true;
        while (isValid) {
            System.out.print("Enter number of players (0-2) players allowed: ");
            this.numPlayers = Integer.parseInt(scanner.nextLine());
            if(numPlayers >= 0 && numPlayers <=2) {

                switch (numPlayers) {
                    case 0:
                        System.out.println("Battle is between 2 computer controlled fighters");

                        isValid = false;
                        break;
                    case 1:
                        System.out.println("Battle is between 1 human controlled fighter and 1 computer controlled fighter ");
                        isValid = false;
                        break;
                    case 2:
                        System.out.println("Battle is between 2 human controlled fighters");
                        isValid = false;
                        break;
                }
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