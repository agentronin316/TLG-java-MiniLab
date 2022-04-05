package com.sontendbox.konsole_combat;

enum Attack {
    BALANCED(70, 3),
    ACCURATE(90, 0);

    private int accuracy;
    private int damageMod;

    Attack(int accuracy, int damageMod) {
        this.accuracy = accuracy;
        this.damageMod = damageMod;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getDamageMod() {
        return damageMod;
    }
}
