package com.sontendbox.konsolecombat;

import com.apps.util.Console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    //file path constants
    private static final String GREET_FILE_PATH = "resources/greeting.txt";
    private static final String WEAPON_TIP_FILE_PATH = "resources/weaponHelp.txt";
    private static final String CHARACTER_TIP_FILE_PATH = "resources/characterHelp.txt";
    private static final String BOW_PLAYER_FILE_PATH = "resources/bowPlayer.txt";
    private static final String AXE_PLAYER_FILE_PATH = "resources/battleaxePlayer.txt";
    private static final String FIST_PLAYER_FILE_PATH = "resources/fistPlayer.txt";
    private static final String SWORD_PLAYER_FILE_PATH = "resources/swordPlayer.txt";
    private static final String MACE_PLAYER_FILE_PATH = "resources/macePlayer.txt";

    //all other constants
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
    private static final String namePrompt = "Enter a name for player: ";

    //static variable
    private static CombatSubController combatControl;

            //#### Singleton stuff
    private static Controller instance;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        CombatSubController.setController(instance);
        return instance;
    }

    private Controller() {
        combatControl = CombatSubController.getInstance();
        combatControl.setScanner(scanner);
    }
            //#### End singleton stuff

    //instance variable
    private Scanner scanner = new Scanner(System.in);

    //main game loop
    public void execute() {
        boolean playAgain = true;
        while (playAgain) {
            show(GREET_FILE_PATH);
            numPlayersPrompt();
            chooseCombatants();
            playAgain = combatControl.runCombat(); //delegate combat to combat controller and ask for another game

        }
    }

    private void chooseCombatants() {
        Fighter combatant = createCombatant();
        combatControl.setCombatant1(combatant); //hand the combatant off to the combat controller
        displayPlayer(combatant.getWeaponName()); //show character picture
        System.out.print("\t\t\t\t" +combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName()+ "\n"); //summarize choice of combatant
        System.out.println();
        combatant = createCombatant(); //and repeat for the second combatant
        combatControl.setCombatant2(combatant);
        displayPlayer(combatant.getWeaponName());
        System.out.println(combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName());
        System.out.println();
    }

    private Fighter createCombatant() {
        //prompt for name
        System.out.print(namePrompt);
        String name = scanner.next();
        Console.clear();
        //prompt for weapon
        StringBuilder builder = new StringBuilder();
        show(WEAPON_TIP_FILE_PATH);
        System.out.println();
        builder.append(weaponSelectPrompt);
        Weapon[] weapons = Weapon.values();
        Character[] characters = Character.values();
        for (int i = 0; i < weapons.length - 1; i++) { //make a list of weapons with attacks
            builder.append(weapons[i].getPrintName()).append(Arrays.toString(weapons[i].getAttacks()));
            builder.append(", ");
            builder.append("\n");
        }
        builder.append(weapons[weapons.length - 1].getPrintName());
        builder.append(Arrays.toString(weapons[weapons.length - 1].getAttacks()));
        System.out.print(builder);
        System.out.println();
        System.out.println();
        System.out.print(weaponSelectPrompt2);
        while (true) {
            String input = scanner.next(); //get weapon selection
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName())) {
                    Console.clear(); //clear console
                    show(CHARACTER_TIP_FILE_PATH); //show character info
                    System.out.println();
                    System.out.println(selectCharacter);
                    for (Character character : characters) {
                        System.out.printf("%s, Damage mod: %s, Accuracy mod: %s\n", character.getPrintName(),
                                character.getDamageMod(), character.getAccuracyMod() + "%");
                    }
                    System.out.println();
                    System.out.print(selectCharacter2);
                    while (true) {
                        String charSelection = scanner.next(); //get character selection
                        for (Character character : Character.values()) {
                            if (charSelection.equalsIgnoreCase(character.getPrintName())) {
                                Console.clear();
                                return new Fighter(weapon, name, character);
                            }

                        }
                    }
                }
            }
        }
    }

    //prompt for number of players, fill in the rest with computer controlled characters
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
                        combatControl.setC1Human(false);
                        combatControl.setC2Human(false);
                        System.out.println();
                        break;
                    case 1:
                        System.out.println(onePlayerAnnouncement);
                        combatControl.setC1Human(true);
                        combatControl.setC2Human(false);
                        System.out.println();
                        break;
                    case 2:
                        System.out.println(twoPlayerAnnouncement);
                        combatControl.setC1Human(true);
                        combatControl.setC2Human(true);
                        System.out.println();
                        break;
                }
            }
        }
    }

    //displays a text file at @path
    void show(String path) {
        try {
            Files.lines(Path.of(path))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //displays character models with weapons
    void displayPlayer(String weaponName){
        switch (weaponName){
            case "bow":
                show(BOW_PLAYER_FILE_PATH);
                break;
            case "mace":
                show(MACE_PLAYER_FILE_PATH);
                break;
            case "fist":
                show(FIST_PLAYER_FILE_PATH);
                break;
            case "sword":
                show(SWORD_PLAYER_FILE_PATH);
                break;
            case "battleaxe":
                show(AXE_PLAYER_FILE_PATH);
                break;

        }
    }

}