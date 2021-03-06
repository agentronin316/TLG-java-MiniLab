package com.sontendbox.konsolecombat;

import java.util.Arrays;

class Fighter {
    private static final String missMessage = " misses.";
    private static final String hitFormat = "%s %s %s for %s damage!";
    private static final String exceptionMessageFormat = "weapon %s does not have attack: %s";

    private Weapon weapon;
    private int health = 100;
    private String name;
    private Character character = Character.DEFAULT;

    Fighter(Weapon weapon, String name) {
        this.weapon = weapon;
        this.name = name;
    }

    Fighter(Weapon weapon, String name, Character character) {
        this.character = character;
        this.name = name;
        this.weapon = weapon;
    }

    Attack[] getAttacks() {
        return weapon.getAttacks();
    }

    // throws IllegalArgumentException if the weapon does not allow the attack that is passed
    String attack(Attack attack, Fighter opponent) throws IllegalArgumentException{
        String toReturn;
        if(Arrays.stream(weapon.getAttacks()).noneMatch(attack1 -> attack1 == attack)){
            throw new IllegalArgumentException(String.format(exceptionMessageFormat, weapon.name(), attack.name()));
        }
        int hit = (int)(Math.random() * 100); //check hit percentage
        if (hit < getAccuracy(attack)){ //if it hits
            int damage = getDamage(attack); //calculate damage
            opponent.takeDamage(damage);    //do the damage and create a string for player feedback
            toReturn = String.format(hitFormat, getName(), weapon.getVerb(), opponent.getName(), damage);
        } else {
            toReturn = name + missMessage; //if it misses, tell the player
        }
        return toReturn;
    }

    String getName() {
        return name;
    }

    String getWeaponName(){
        return weapon.getPrintName();
    }

    int getAccuracy(Attack attack) {
        return character.getAccuracyMod() + attack.getAccuracy();
    }

    int getDamage(Attack attack) {
        return character.getDamageMod() + weapon.damageCalculation(attack);
    }

    int getHealth(){
        return health;
    }

    public Character getCharacter() {
        return character;
    }

    private void takeDamage(int damage) {
        health -= damage;
    }
}