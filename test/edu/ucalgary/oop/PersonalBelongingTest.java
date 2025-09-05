package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonalBelongingTest extends SupplyTest {
    private PersonalBelonging belonging;
    private DisasterVictim owner;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        belonging = new PersonalBelonging("Suitcase", "Green and made of leather.");
        owner = new DisasterVictim("Hans Massaquoi", "2006-01-19");
    }

    @Test
    public void testGetDescription() {
        assertEquals("Green and made of leather.", belonging.getDescription());
    }

}