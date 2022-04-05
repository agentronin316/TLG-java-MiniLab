package com.sontendbox.konsole_combat;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FIghterTest {
    private static String missMessage = "misses.";
    private static String punchMessage = "Dave punches Steve for 10 damage!";

    Fighter fighter1;
    Fighter fighter2;

    @Before
    public void setUp() {
        fighter1 = new Fighter(Weapon.FIST, "Dave");
        fighter2 = new Fighter(Weapon.FIST, "Steve");
    }

    @Test
    public void attack_functionsProperly_whenGivenAValidAttack() throws Exception {
        String result = fighter1.attack(Attack.BALANCED, fighter2);
        while (result.equals(missMessage)) {
            result = fighter1.attack(Attack.BALANCED, fighter2);
        }
        assertEquals(punchMessage, result);
        assertEquals(90, fighter2.getHealth());
    }

    @Test (expected = IllegalArgumentException.class)
    public void attack_throwsIllegalArgumentException_whenGivenAnInvalidAttack() throws Exception {
        String result = fighter1.attack(Attack.ACCURATE, fighter2);
    }
}