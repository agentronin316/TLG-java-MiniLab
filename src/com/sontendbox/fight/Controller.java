package com.sontendbox.fight;

import com.apps.util.Console;

import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    private static final String weaponSelectPrompt = "Select weapon. Weapons available are: \n";
    private static final String selectAttack = "Select attack: ";
    private static final String CHOSE = " chose ";
    private static final String greet1 = "************************************************";
    private static final String greet2 = "*                                              *";
    private static final String greet3 = "* WELCOME T0 CONSOLE COMBAT! PREPARE TO FIGHT! *";
    private static final String oneDigitRegex = "/d{1}";
    private static final String bracketRegex = "[%s]";
    private static final String numPlayersPrompt = "Enter number of players (0-2) players allowed: ";
    private static final String twoPlayerAnnouncement = "Battle is between 2 human controlled fighters";
    private static final String onePlayerAnnouncement = "Battle is between 1 human controlled fighter and " +
            "1 computer controlled fighter";
    private static final String zeroPlayerAnnouncement = "Battle is between 2 computer controlled fighters";
    private static final String namePrompt = "Enter a name for this combatant: ";


    private Fighter combatant1;
    private Fighter combatant2;
    private boolean isC1Human;
    private boolean isC2Human;
    private Scanner scanner = new Scanner(System.in);
    private int firstPlayer;
    private int playerTurn = 1;

    public void execute() {
        boolean playAgain = true;
        while (playAgain) {
            firstPlayer = (int) (Math.random() * 2 + 1);
            greet();
            numPlayersPrompt();
            weaponSelection();
            while (combatant1.getHealth() > 0 && combatant2.getHealth() > 0) {
                takeTurn();
                updateScreen();
            }
            playAgain = displayVictoryScreen();

        }
    }

    private boolean displayVictoryScreen() {
        // TODO: declare winner
        String winner;
        if (combatant1.getHealth() > 0) {
            winner = combatant1.getName();
        } else {
            winner = combatant2.getName();
        }


        System.out.println("************************************************");
        System.out.println("*                                              *");
        System.out.println("*    Congratulations " + winner + "! You won!            *");
        System.out.println("*                                              *");
        System.out.println("************************************************");


        System.out.print("Play again? [y/n]");
        return ("y".equalsIgnoreCase(scanner.next()));
    }

    private void updateScreen() {
        // TODO: display turn results
        Console.clear();
        System.out.println(combatant1.getName() + " Health: " + combatant1.getHealth());
        System.out.println(combatant2.getName() + " Health: " + combatant2.getHealth());
    }


    private void takeTurn() {
        if (playerTurn == firstPlayer) {
            Attack[] attacks = combatant1.getAttacks();
            if (isC1Human) {
                System.out.println(combatant1.getName() + " turn");
                movePrompt(combatant1, combatant2, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                System.out.println(combatant1.attack(attacks[attackIndex], combatant2));
            }
        } else {
            Attack[] attacks = combatant2.getAttacks();
            if (isC2Human) {
                System.out.println(combatant2.getName() + " turn");
                movePrompt(combatant2, combatant1, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                System.out.println(combatant2.attack(attacks[attackIndex], combatant1));
            }
        }
        playerTurn = (playerTurn % 2) + 1;
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
                System.out.println(attacker.attack(attacks[Integer.parseInt(attackInput) - 1], target));
                isValid = false;
            }
        }
    }

    private void weaponSelection() {
        combatant1 = getCombatant();
        System.out.println(combatant1.getName() + CHOSE + combatant1.getWeaponName());
        combatant2 = getCombatant();
        System.out.println(combatant2.getName() + CHOSE + combatant2.getWeaponName());
    }

    private Fighter getCombatant() {
        StringBuilder builder = new StringBuilder();
        builder.append(weaponSelectPrompt);
        Weapon[] weapons = Weapon.values();
        for (int i = 0; i < weapons.length - 1; i++) {
            builder.append(weapons[i].getPrintName()).append(Arrays.toString(weapons[i].getAttacks()));
            builder.append(", ");
            builder.append("\n");
        }
        builder.append(weapons[weapons.length - 1].getPrintName());
        builder.append(Arrays.toString(weapons[weapons.length - 1].getAttacks()));

        System.out.print(builder);
        while (true) {
            String input = scanner.next();
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName())) {
                    System.out.print(namePrompt);
                    String name = scanner.next();
                    return new Fighter(weapon, name);
                }
            }
        }
    }

    private void numPlayersPrompt() {
        boolean isInvalid = true;
        while (isInvalid) {
            System.out.print(numPlayersPrompt);
            int numPlayers = Integer.parseInt(scanner.next());
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