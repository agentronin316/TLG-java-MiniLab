package com.sontendbox.fight;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class FIghterTest {

    Fighter fighter1;
    Fighter fighter2;

    @Before
    public void setUp() {
        fighter1 = new Fighter(Weapon.FIST);
        fighter2 = new Fighter(Weapon.FIST);
    }

    @Test
    public void attack_functionsProperly_whenGivenAValidAttack() throws Exception {
        String result = fighter1.attack(Attack.BALANCED, fighter2);
        while (result.equals("misses.")) {
            result = fighter1.attack(Attack.BALANCED, fighter2);
        }
        Assert.assertEquals(90, fighter2.getHealth());
    }
}