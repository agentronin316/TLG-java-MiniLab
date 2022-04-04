package com.sontendbox.fight;

import java.util.Arrays;

class Fighter {
    private static final String missMessage = "misses.";
    private static final String hitFormat = "%s for %s damage!";
    private static final String exceptionMessageFormat = "weapon %s does not have attack: %s";

    private Weapon weapon;
    private int health = 100;

    Fighter(Weapon weapon) {
        this.weapon = weapon;
    }

    Attack[] getAttacks() {
        return weapon.getAttacks();
    }

    String attack(Attack attack, Fighter opponent) throws IllegalArgumentException{
        String toReturn;
        if(Arrays.stream(weapon.getAttacks()).noneMatch(attack1 -> attack1 == attack)){
            throw new IllegalArgumentException(String.format(exceptionMessageFormat, weapon.name(), attack.name()));
        }
        int hit = (int)(Math.random() * 100);
        if (hit < attack.getAccuracy()){
            int damage = weapon.damageCalculation(attack);
            opponent.takeDamage(damage);
            toReturn = String.format(hitFormat, weapon.getVerb(), damage);
        } else {
            toReturn = missMessage;
        }
        return toReturn;
    }

    String getWeapon(){
        return weapon.getPrintName();
    }

    int getDamage(Attack attack) {
        return weapon.damageCalculation(attack);
    }

    int getHealth(){
        return health;
    }

    private void takeDamage(int damage) {
        health -= damage;
    }
}