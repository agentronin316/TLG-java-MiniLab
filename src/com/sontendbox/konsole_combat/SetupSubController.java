package com.sontendbox.konsole_combat;

class SetupSubController {

    //this will always be a reference to the only instance of this class to exist
    private static SetupSubController instance;

    //factory method that creates a new instance if instance is null, then returns instance
    public static SetupSubController getInstance() {
        //if instance is null,
        if (instance == null) {
            //then populate instance
            instance = new SetupSubController();
        }
        //and always return instance
        return instance;
    }

    //private ctor
    private SetupSubController(){

    }

    //business methods

}