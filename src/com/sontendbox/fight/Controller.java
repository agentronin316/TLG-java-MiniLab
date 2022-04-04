package com.sontendbox.fight;

import java.io.Console;
import java.util.Scanner;

public class Controller {


    private Fighter combatant1;
    private Fighter combatant2;
    private boolean isC1Human;
    private boolean isC2Human;
    private Scanner scanner = new Scanner(System.in);
    private Weapon weapon;
    private int numPlayers = 0;
    private int playerTurn = 1;

    public void execute() {
        boolean playAgain = true;
        while (playAgain) {
            greet();
            numPlayersPrompt();
            weaponSelection();
            while (combatant1.getHealth() > 0 && combatant2.getHealth() > 0) {
                takeTurn();
                updateScreen();
                //scanner.next();

            }
            playAgain = displayVictoryScreen();
        }
    }

    private boolean displayVictoryScreen() {
        // TODO: declare winner
        String winner;
        if(combatant1.getHealth() > 0){
            winner = "combatant 1";
        }
        else{
            winner = "combatant 2";
        }


        System.out.println("************************************************");
        System.out.println("*                                              *");
        System.out.println("*   Congratulations " + winner + "! You won!   *");
        System.out.println("*                                              *");
        System.out.println("************************************************");


        System.out.print("Play again? [y/n]");
        return ("y".equalsIgnoreCase(scanner.next()));
    }

    private void updateScreen() {
        // TODO: display turn results
        System.out.println("Combatant 1 Health: " + combatant1.getHealth());
        System.out.println("Combatant 2 Health: " + combatant2.getHealth());
    }

    private void takeTurn() {
        if (playerTurn == 1) {
            Attack[] attacks = combatant1.getAttacks();
            if (isC1Human) {
                System.out.println("Combatant 1 turn");
                movePrompt(combatant1, combatant2, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                combatant1.attack(attacks[attackIndex], combatant2);
            }
            playerTurn = 2;
        } else {
            Attack[] attacks = combatant2.getAttacks();
            if (isC2Human) {
                System.out.println("Combatant 2 turn");
                movePrompt(combatant2, combatant1, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                combatant2.attack(attacks[attackIndex], combatant1);
            }
            playerTurn = 1;
        }
    }

    private void movePrompt(Fighter attacker, Fighter target, Attack[] attacks) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selectAttack);
        for (int i = 0; i < attacks.length; i++) {
            stringBuilder.append(String.format(bracketRegex, i + 1));
            stringBuilder.append(attacks[i].name().toLowerCase());
        }
        boolean isValid = true;
        while (isValid) {
            System.out.print(stringBuilder);
            String attackInput = scanner.next();

            if (attackInput.matches("\\d{1}") && Integer.parseInt(attackInput) <= attacks.length) {

            

                attacker.attack(attacks[Integer.parseInt(attackInput) - 1], target);
                isValid = false;
        }
    }

    private void weaponSelection() {
        combatant1 = getCombatant();
        System.out.println(com1Chose + combatant1.getWeaponName());
        combatant2 = getCombatant();
        System.out.println(com2Chose + combatant2.getWeaponName());
    }

    private Fighter getCombatant() {
        StringBuilder builder = new StringBuilder();
        builder.append(weaponSelectPrompt);
        Weapon[] weapons = Weapon.values();
        for (int i = 0; i < weapons.length - 1; i++) {
            builder.append(weapons[i].getPrintName());
            builder.append(", ");
        }
        builder.append(weapons[weapons.length - 1].getPrintName());
        System.out.print(builder);
        while(true) {
            String input = scanner.next();
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName())) {
                    return new Fighter(weapon);
                }
            }
        }
    }

    private void numPlayersPrompt() {
        boolean isInvalid = true;
        while (isInvalid) {
            System.out.print(numPlayersPrompt);
            this.numPlayers = Integer.parseInt(scanner.nextLine());
            if (numPlayers >= 0 && numPlayers <= 2) {
                isInvalid = false;
                switch (numPlayers) {
                    case 0:
                        System.out.println(zeroPlayerAnnouncement);
                        isC1Human = false;
                        isC2Human = false;
                        break;
                    case 1:
                        System.out.println(onePlayerAnnouncement);
                        isC1Human = true;
                        isC2Human = false;
                        break;
                    case 2:
                        System.out.println(twoPlayerAnnouncement);
                        isC1Human = true;
                        isC2Human = true;
                        break;
                }
            }
        }
    }

    private void greet() {
        System.out.println(greet1);
        System.out.println(greet2);
        System.out.println(greet3);
        System.out.println(greet2);
        System.out.println(greet1);
    }
}