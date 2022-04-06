package com.sontendbox.konsole_combat;

import com.apps.util.Console;

import java.util.Scanner;

class CombatSubController {
    private static final String selectAttack = "Select attack: ";
    private static final String oneDigitRegex = "\\d{1}";
    private static final String bracketRegex = "[%s]";
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
    private CombatSubController(){
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

    public boolean runCombat(){
        firstPlayer = (int)(Math.random() * 2 + 1);
        while (combatant1.getHealth() > 0 && combatant2.getHealth() > 0) {
            takeTurn();
            updateScreen();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Console.clear();
        }
        return displayVictoryScreen();
    }

    private void updateScreen() {
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

            if (attackInput.matches(oneDigitRegex) && Integer.parseInt(attackInput) <= attacks.length) {
                System.out.println(attacker.attack(attacks[Integer.parseInt(attackInput) - 1], target));
                isValid = false;
            }
        }
    }

    private boolean displayVictoryScreen(){

        String winner;
        if(combatant1.getHealth() > 0){
            winner = combatant1.getName();
        }
        else{
            winner = combatant2.getName();
        }

        controller.victory();
        if (combatant1.getHealth() == 0 || combatant2.getHealth() == 0){
            System.out.println("*********************************************************");
            System.out.println("   Congratulations " + winner + "! You won!    ");
            System.out.println("*********************************************************");
        }
        if (combatant1.getHealth() < 0 || combatant2.getHealth() < 0){
            System.out.println("************************************************************************");
            System.out.println("   Congratulations " + winner + "! You decimated your opponent!    ");
            System.out.println("************************************************************************");
        }


        System.out.print("Play again? [y/n]");
        return ("y".equalsIgnoreCase(scanner.next()));
    }
}