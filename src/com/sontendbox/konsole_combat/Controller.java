package com.sontendbox.konsole_combat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    private static final String weaponSelectPrompt = "Select weapon. Current weapon availability is limited to: \n";
    private static final String selectCharacter = "Select character. Available characters are: ";
    private static final String CHOSE = " chose ";
    private static final String GREET_FILE_PATH = "resources/greeting.txt";
    private static final String VICTORY_FILE_PATH = "resources/victory.txt";
    private static final String numPlayersPrompt = "Enter number of players (0-2) players allowed: ";
    private static final String twoPlayerAnnouncement = "Battle is between 2 human controlled fighters";
    private static final String onePlayerAnnouncement = "Battle is between 1 human controlled fighter and " +
            "1 computer controlled fighter";
    private static final String zeroPlayerAnnouncement = "Battle is between 2 computer controlled fighters";
    private static final String namePrompt = "Enter a name for this combatant: ";
    private static Controller instance;
    private static CombatSubController combatContol;

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
        System.out.println(combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName());
        combatant = getCombatant();
        combatContol.setCombatant2(combatant);
        System.out.println(combatant.getName() + CHOSE + combatant.getWeaponName() + " and "
                + combatant.getCharacter().getPrintName());
    }

    private Fighter getCombatant() {
        StringBuilder builder = new StringBuilder();
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
        while (true) {
            String input = scanner.next();
            for (Weapon weapon : Weapon.values()) {
                if (input.equalsIgnoreCase(weapon.getPrintName())) {
                    System.out.print(namePrompt);
                    String name = scanner.next();
                    System.out.println(selectCharacter);
                    for (Character character : characters) {
                        System.out.printf("%s, Damage mod: %s, Accuracy mod: %s\n",
                                character.getPrintName(), character.getDamageMod(), character.getAccuracyMod() + "%");
                    }
                    String charSelection = scanner.next();
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
                        break;
                    case 1:
                        System.out.println(onePlayerAnnouncement);
                        combatContol.setC1Human(true);
                        combatContol.setC2Human(false);
                        break;
                    case 2:
                        System.out.println(twoPlayerAnnouncement);
                        combatContol.setC1Human(true);
                        combatContol.setC2Human(true);
                        break;
                }
            }
        }
    }

    private void show(String path) {
        try {
            Files.lines(Path.of(path))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void victory(){
        try {
            Files.lines(Path.of(VICTORY_FILE_PATH))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}