package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CotTest extends SupplyTest {
    private Cot cot;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        cot = new Cot(115, "A1");
    }

    @Test
    public void testGetRoom() {
        assertEquals(115, cot.getRoom()); // ✅ int vs int
    }

}