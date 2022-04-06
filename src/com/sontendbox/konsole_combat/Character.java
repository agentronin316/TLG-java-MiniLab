package com.sontendbox.konsole_combat;

enum Character {
    DEFAULT(0, 0,"Default"),
    PRECISE(5, -1,"Precise"),
    WILD(-15, 2,"Wild");

    private final int accuracyMod;
    private final int damageMod;
    private final String printName;

    Character(int accuracyMod, int damageMod, String printName){
        this.accuracyMod = accuracyMod;
        this.damageMod = damageMod;
        this.printName = printName;
    }

    public int getAccuracyMod() {
        return accuracyMod;
    }

    public int getDamageMod() {
        return damageMod;
    }

    public String getPrintName() {
        return printName;
    }
}