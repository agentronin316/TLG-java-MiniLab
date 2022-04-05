package com.sontendbox.konsole_combat;

enum Character {
    DEFAULT(0, 0),
    PRECISE(5, -1),
    WILD(-10, 3);

    private final int accuracyMod;
    private final int damageMod;

    Character (int accuracyMod, int damageMod){
        this.accuracyMod = accuracyMod;
        this.damageMod = damageMod;
    }

    public int getAccuracyMod() {
        return accuracyMod;
    }

    public int getDamageMod() {
        return damageMod;
    }
}