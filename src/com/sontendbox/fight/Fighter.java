package com.sontendbox.fight;

import java.util.Arrays;

class Fighter {
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
            throw new IllegalArgumentException("weapon " + weapon + " does not have attack: " + attack);
        }
        int hit = (int)(Math.random() * 100);
        if (hit < attack.getAccuracy()){
            int damage = weapon.damageCalculation(attack);
            opponent.takeDamage(damage);
            toReturn = weapon.getVerb() + " for " + damage + " damage!";
        } else {
            toReturn = "misses.";
        }
        return toReturn;
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