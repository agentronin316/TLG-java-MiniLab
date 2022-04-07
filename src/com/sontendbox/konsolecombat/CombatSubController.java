package com.sontendbox.konsolecombat;

import com.apps.util.Console;

import java.util.Scanner;

class CombatSubController {
    private static final String selectAttack = "Select attack: ";
    private static final String oneDigitRegex = "\\d{1}";
    private static final String bracketFormatString = "[%s]";
    private static final String VICTORY_FILE_PATH = "resources/victory.txt";
    private static final String notAnEasterEgg = "Jose";
    private static final int winnerBannerWidth = 75;

    private static Controller controller;
    //this will always be a reference to the only instance of this class to exist
    private static CombatSubController instance;

    //factory method that creates a new instance if instance is null, then returns instance
    public static CombatSubController getInstance() {
        //if instance is null,
        if (instance == null) {
            //then populate instance
            instance = new CombatSubController();
        }
        //and always return instance
        return instance;
    }

    //private ctor
    private CombatSubController() {
    }

    private Fighter combatant1;
    private Fighter combatant2;
    private boolean isC1Human;
    private boolean isC2Human;
    private int firstPlayer;
    private int playerTurn = 1;
    private Scanner scanner;

    public static void setController(Controller controller) {
        CombatSubController.controller = controller;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setCombatant1(Fighter combatant1) {
        this.combatant1 = combatant1;
    }

    public void setCombatant2(Fighter combatant2) {
        this.combatant2 = combatant2;
    }

    public void setC1Human(boolean c1Human) {
        isC1Human = c1Human;
    }

    public void setC2Human(boolean c2Human) {
        isC2Human = c2Human;
    }

    //business methods

    public boolean runCombat() {
        preemptiveStrike();
        firstPlayer = (int) (Math.random() * 2 + 1);
        while (combatant1.getHealth() > 0 && combatant2.getHealth() > 0) {
            updateScreen();
            takeTurn();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Console.clear();
        }
        return displayVictoryScreen();
    }

    private void preemptiveStrike() {
        firstPlayer = 1;
        if (combatant1.getWeaponName().equals("bow")) {
            System.out.println(combatant1.getName() + " fires a arrow while " +
                    combatant2.getName() + " is approaching!");
            takeTurn();
            updateScreen();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Console.clear();
            playerTurn = 1;
        }
        if (combatant2.getWeaponName().equals("bow")) {
            System.out.println(combatant2.getName() + " fires a arrow while " +
                    combatant1.getName() + " is approaching!");
            playerTurn = 2;
            takeTurn();
            updateScreen();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Console.clear();
        }
    }

    private void updateScreen() {
        System.out.println(combatant1.getName() + " Health: " + combatant1.getHealth());
        System.out.println(combatant2.getName() + " Health: " + combatant2.getHealth());
    }

    private void takeTurn() {
        if (playerTurn == firstPlayer) {
            Attack[] attacks = combatant1.getAttacks(); //get attack array for combatant1
            controller.displayPlayer(combatant1.getWeaponName()); //display weapon graphics
            if (isC1Human) {
                movePrompt(combatant1, combatant2, attacks); //player turn interaction
            } else {
                int attackIndex = (int) (Math.random() * attacks.length); //pick a random valid attack
                System.out.println(combatant1.attack(attacks[attackIndex], combatant2)); //do attack
            }

        } else {
            Attack[] attacks = combatant2.getAttacks(); //get attack array for combatant2
            controller.displayPlayer(combatant2.getWeaponName()); //display weapon graphics
            if (isC2Human) {
                movePrompt(combatant2, combatant1, attacks);
            } else {
                int attackIndex = (int) (Math.random() * attacks.length);
                System.out.println(combatant2.attack(attacks[attackIndex], combatant1));
            }

        }
        playerTurn = (playerTurn % 2) + 1;
    }

    private void movePrompt(Fighter attacker, Fighter target, Attack[] attacks) {
        System.out.println(attacker.getName() + "'s turn");
        StringBuilder stringBuilder = buildAttacksString(attacker, attacks);
        boolean isValid = true;
        while (isValid) {
            System.out.print(stringBuilder);
            String attackInput = scanner.next();

            if (attackInput.matches(oneDigitRegex) && Integer.parseInt(attackInput) <= attacks.length) {
                System.out.println(attacker.attack(attacks[Integer.parseInt(attackInput) - 1], target));
                isValid = false;
            }
        }
    }

    // make a single string that will display all the information for attack selection in combat
    private StringBuilder buildAttacksString(Fighter attacker, Attack[] attacks) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selectAttack);
        stringBuilder.append("\n   ");
        stringBuilder.append(String.format("| %10S | %9S | %5S\n", "Attack", "Accuracy", "Damage"));
        for (int i = 0; i < attacks.length; i++) {
            stringBuilder.append(String.format(bracketFormatString, i + 1));
            stringBuilder.append(String.format("| %10S | %9S | %5S", attacks[i].name(),
                    attacker.getAccuracy(attacks[i]) + "%",
                    attacker.getDamage(attacks[i])));
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    private boolean displayVictoryScreen() {

        String winner;
        //determine who won
        if (combatant1.getHealth() > 0) {
            winner = combatant1.getName();
        } else {
            winner = combatant2.getName();
        }

        controller.show(VICTORY_FILE_PATH);

        //check for easter egg trigger
        if(combatant1.getName().equalsIgnoreCase(notAnEasterEgg)
                || combatant2.getName().equalsIgnoreCase(notAnEasterEgg)){
            if(winner.equalsIgnoreCase(notAnEasterEgg)){
                notAnEasterEggVictory();
            }
            else{
                notAnEasterEggDefeat();
            }
        }
        else{ //otherwise, display ending graphics normally
            if (combatant1.getHealth() == 0 || combatant2.getHealth() == 0) {
                System.out.println(centerText("", winnerBannerWidth, '*'));
                String victoryText = "Congratulations " + winner + "! You won!";
                victoryText = centerText(victoryText, winnerBannerWidth, '*');
                System.out.println(victoryText);
                System.out.println(centerText("", winnerBannerWidth, '*'));
            }

            if (combatant1.getHealth() < 0 || combatant2.getHealth() < 0) {
                System.out.println(centerText("", winnerBannerWidth, '*'));
                String victoryText = "Congratulations " + winner + "! You decimated your opponent!";
                victoryText = centerText(victoryText, winnerBannerWidth, '*');
                System.out.println(victoryText);
                System.out.println(centerText("", winnerBannerWidth, '*'));
            }
        }



            System.out.print("Play again? [y/n]");
            return ("y".equalsIgnoreCase(scanner.next()));

    }


    String centerText(String text, int width) {
        return centerText(text, width, ' ');
    }

    String centerText(String text, int width, char filler) {
        if (text.length() >= width) {
            return text;
        } else {
            StringBuilder builder = new StringBuilder();
            int leftPad = (width - text.length()) / 2; //determine amount of padding needed on the left
            builder.append(String.valueOf(filler).repeat(leftPad)); //fill left padding with filler character
            builder.append(text); //add the text in the middle
            int rightPad = width - builder.length();
            builder.append(String.valueOf(filler).repeat(rightPad)); //fill the rest of the line with filler character
            return builder.toString();
        }
    }


    private void notAnEasterEggVictory(){
        System.out.println(centerText("", winnerBannerWidth, '*'));
        String victoryText = String.format("%s only won because it's rigged!", notAnEasterEgg);
        victoryText = centerText(victoryText, winnerBannerWidth, '*');
        System.out.println(victoryText);
        System.out.println(centerText("", winnerBannerWidth, '*'));
    }

    private void notAnEasterEggDefeat(){
        System.out.println(centerText("", winnerBannerWidth, '*'));
        String victoryText = String.format("Sorry %s, this isn't Duck Race! You can't win them all!", notAnEasterEgg);
        victoryText = centerText(victoryText, winnerBannerWidth, '*');
        System.out.println(victoryText);
        System.out.println(centerText("", winnerBannerWidth, '*'));
    }


}