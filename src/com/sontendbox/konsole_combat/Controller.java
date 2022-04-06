package com.sontendbox.konsole_combat;

import com.apps.util.Console;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    private static final String GREET_FILE_PATH = "resources/greeting.txt";
    private static final String WEAPON_TIP_FILE_PATH = "resources/weaponHelp.txt";
    private static final String CHARACTER_TIP_FILE_PATH = "resources/characterHelp.txt";
    private static final String BOW_PLAYER_FILE_PATH = "resources/bowPlayer.txt";
    private static final String AXE_PLAYER_FILE_PATH = "resources/battleaxePlayer.txt";
    private static final String FIST_PLAYER_FILE_PATH = "resources/fistPlayer.txt";
    private static final String SWORD_PLAYER_FILE_PATH = "resources/swordPlayer.txt";
    private static final String MACE_PLAYER_FILE_PATH = "resources/macePlayer.txt";


    private static final String weaponSelectPrompt = "Select weapon. Weapons to chose from are: \n";
    private static final String weaponSelectPrompt2 = "Enter the name of a weapon listed above \n";
    private static final String selectCharacter = "Select character. Available characters are: ";
    private static final String selectCharacter2 = "Enter the name of a character listed above \n";
    private static final String CHOSE = " chose ";
    private static final String numPlayersPrompt = "Enter number of players (0-2) players allowed: ";
    private static final String twoPlayerAnnouncement = "Battle is between 2 human controlled fighters";
    private static final String onePlayerAnnouncement = "Battle is between 1 human controlled fighter and " +
            "1 computer controlled fighter";
    private static final String zeroPlayerAnnouncement = "Battle is between 2 computer controlled fighters";
    private static final String namePrompt = "Enter a name for player";
    private static Controller instance;
    private static CombatSubController combatContol;

    private int playerNumber = 1;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        CombatSubController.setController(instance);
        return instance;
    }

    private Controller() {
        combatContol = CombatSubController.getInstance();
        combatContol.setScanner(scanner);
    }

    private Scanner scanner = new Scanner(System.in);

    public void execute() {
        boolean playAgain = true;
        while (playAgain) {

            show(GREET_FILE_PATH);
            numPlayersPrompt();
            weaponSelection();

            playAgain = combatContol.runCombat();

        }
    }

    private void weaponSelection() {
        Fighter combatant = getCombatant();
        combatContol.setCombatant1(combatant);
        System.out.print("\t\t\t\t" +combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName()+ "\n");
        displayPlayer(combatant.getWeaponName());
        System.out.println();
        combatant = getCombatant();
        combatContol.setCombatant2(combatant);
        System.out.println(combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName());
        displayPlayer(combatant.getWeaponName());
        System.out.println();
    }

    private Fighter getCombatant() {
        System.out.printf("%s %s: \n",namePrompt, playerNumber);
        playerNumber += 1;
        String name = scanner.next();
        Console.clear();
        StringBuilder builder = new StringBuilder();
        show(WEAPON_TIP_FILE_PATH);
        builder.append(weaponSelectPrompt);
        Weapon[] weapons = Weapon.values();
        Character[] characters = Character.values();
        for (int i = 0; i < weapons.length - 1; i++) {
            builder.append(weapons[i].getPrintName()).append(Arrays.toString(weapons[i].getAttacks()));
            builder.append(", ");
            builder.append("\n");
        }
        builder.append(weapons[weapons.length - 1].getPrintName()).append(Arrays.toString(weapons[weapons.length - 1].getAttacks()));
        System.out.print(builder);
        System.out.print("\n");
        System.out.print(weaponSelectPrompt2);
        while (true) {
            String input = scanner.next();
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName())) {
                    Console.clear();
                    show(CHARACTER_TIP_FILE_PATH);
                    System.out.println(selectCharacter);
                    for (Character character : characters) {
                        System.out.printf("%s, Damage mod: %s, Accuracy mod: %s\n",
                                character.getPrintName(), character.getDamageMod(), character.getAccuracyMod() + "%");
                    }
                    System.out.print(selectCharacter2);
                    String charSelection = scanner.next();
                    Console.clear();
                    for (Character character : Character.values()) {
                        if (charSelection.equalsIgnoreCase(character.getPrintName())) {
                            return new Fighter(weapon, name, character);
                        }

                    }
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
                        combatContol.setC1Human(false);
                        combatContol.setC2Human(false);
                        System.out.println();
                        break;
                    case 1:
                        System.out.println(onePlayerAnnouncement);
                        combatContol.setC1Human(true);
                        combatContol.setC2Human(false);
                        System.out.println();
                        break;
                    case 2:
                        System.out.println(twoPlayerAnnouncement);
                        combatContol.setC1Human(true);
                        combatContol.setC2Human(true);
                        System.out.println();
                        break;
                }
            }
        }
    }

    void show(String path) {
        try {
            Files.lines(Path.of(path))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void displayPlayer(String name){
        switch (name){
            case "bow":
                show(BOW_PLAYER_FILE_PATH);
                break;
            case "mace":
                show(MACE_PLAYER_FILE_PATH);
            case "fist":
                show(FIST_PLAYER_FILE_PATH);
            case "sword":
                show(SWORD_PLAYER_FILE_PATH);
            case "battleaxe":
                show(AXE_PLAYER_FILE_PATH);

        }
    }

}