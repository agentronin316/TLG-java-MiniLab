package com.sontendbox.fight;

import java.util.Scanner;

public class Controller {
    private static final String weaponSelectPrompt = "Select weapon. Current weapon availability is limited to: ";
    private static final String selectAttack = "Select attack: ";
    private static final String com1Chose = "Combatant 1 chose ";
    private static final String com2Chose = "Combatant 2 chose ";
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
                scanner.next();
                // TODO: clear screen?
            }
            playAgain = displayVictoryScreen();
        }
    }

    private boolean displayVictoryScreen() {
        // TODO: declare winner

        System.out.print("Play again? [y/n]");
        return ("y".equalsIgnoreCase(scanner.next()));

    }

    private void updateScreen() {
        // TODO: display turn results
    }

    private void takeTurn() {
        if (playerTurn == 1) {
            Attack[] attacks = combatant1.getAttacks();
            if (isC1Human) {
                movePrompt(combatant1, combatant2, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                combatant1.attack(attacks[attackIndex], combatant2);
            }
            playerTurn = 2;
        } else {
            Attack[] attacks = combatant2.getAttacks();
            if (isC2Human) {
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
        boolean isValid = false;
        while (!isValid) {
            System.out.print(stringBuilder);
            String attackInput = scanner.next();
            if (attackInput.matches(oneDigitRegex) && Integer.parseInt(attackInput) <= attacks.length) {
                attacker.attack(attacks[Integer.parseInt(attackInput) - 1], target);
                isValid = true;
            }
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