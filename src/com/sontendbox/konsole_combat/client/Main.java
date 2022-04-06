package com.sontendbox.konsole_combat.client;

import com.sontendbox.konsole_combat.Controller;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        Controller controller = Controller.getInstance();
        controller.execute();
    }

}