package com.sontendbox.konsole_combat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CombatSubControllerTest {
    private static String shortString = "   ";
    private static String resultString = "          ";
    private static String resultString2 =  "***   ****";
    private static char fillerChar = '*';
    private static String longString = " * * * * * * * * * * ";

    CombatSubController controller;
    @Before
    public void setUp() {
        controller = CombatSubController.getInstance();
    }

    @Test
    public void centerText_shouldReturnProperLengthString_whenStringIsShorter() throws Exception {
        String result = controller.centerText(shortString, resultString.length());
        assertEquals(resultString, result);
    }

    @Test
    public void centerTextOverload_shouldReturnProperString_whenStringIsShorter() throws Exception {
        String result = controller.centerText(shortString, resultString2.length(), fillerChar);
        assertEquals(resultString2, result);
    }

    @Test
    public void centerText_shouldReturnOriginalString_whenStringIsLonger() throws Exception {
        String result = controller.centerText(longString, resultString.length());
        assertEquals(longString, result);
    }
}