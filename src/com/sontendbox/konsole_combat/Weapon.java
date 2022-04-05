package com.sontendbox.konsole_combat;

import java.util.Arrays;

enum Weapon {
    FIST("punches", 7, "fist", Attack.BALANCED);

    private final String verb;
    private final int baseDamage;
    private final String printName;
    private final Attack[] attacks;

    Weapon(String verb, int baseDamage, String printName, Attack... attacks) {
        this.verb = verb;
        this.baseDamage = baseDamage;
        this.printName = printName;
        this.attacks = attacks;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

    public String getPrintName() {
        return printName;
    }

    public String getVerb() {
        return verb;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": "
                + "verb='" + getVerb()
                +", baseDamage=" + baseDamage
                + ", printName='" + getPrintName()
                +", attacks=" + Arrays.toString(attacks);
    }

    int damageCalculation(Attack attack) {
        return baseDamage + attack.getDamageMod();
    }
}