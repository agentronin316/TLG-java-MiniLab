package com.sontendbox.konsole_combat;

class CombatSubController {

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

    //business methods

}