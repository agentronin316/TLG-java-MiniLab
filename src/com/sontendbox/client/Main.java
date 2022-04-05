package com.sontendbox.client;

import com.sontendbox.fight.Controller;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.execute();
    }

}