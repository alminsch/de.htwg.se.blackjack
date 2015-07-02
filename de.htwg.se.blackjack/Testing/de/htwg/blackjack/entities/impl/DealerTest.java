package de.htwg.blackjack.entities.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DealerTest {
    Dealer dealer;

    @Before
    public void setUp() {
        dealer = new Dealer();
        dealer.addtoCards(Card.HERZACHT);
    }

    @Test
    public void TesttoString() {
        assertEquals("Dealer Handkarten: HERZACHT", dealer.toString(0));
        assertEquals("Dealer Handkarten: HERZACHT Wert:8", dealer.toString(8));
    }
}
