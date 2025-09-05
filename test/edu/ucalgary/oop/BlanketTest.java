package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlanketTest extends SupplyTest {
    private Blanket blanket;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        blanket = new Blanket();
    }

    @Test
    public void testLocation() {
        Location location = new Location("University of Calgary", "2500 University Dr NW");
        blanket.addLocation(location);
        assertEquals(location, blanket.getLocation());
    }
}