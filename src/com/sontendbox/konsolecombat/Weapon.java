package com.sontendbox.konsolecombat;

import java.util.Arrays;

enum Weapon {
    FIST("punches", 8, "fist", Attack.BALANCED),
    BOW("shoots", 8, "bow", Attack.ACCURATE),
    SWORD("slashes", 8, "sword", Attack.ACCURATE, Attack.BALANCED),
    MACE("bashes", 8, "mace", Attack.BALANCED, Attack.HEAVY),
    BATTLEAXE("hacks", 8, "battleaxe", Attack.HEAVY);

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

    int damageCalculation(Attack attack) {
        return baseDamage + attack.getDamageMod();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": "
                + "verb='" + getVerb()
                +", baseDamage=" + baseDamage
                + ", printName='" + getPrintName()
                +", attacks=" + Arrays.toString(attacks);
    }
}