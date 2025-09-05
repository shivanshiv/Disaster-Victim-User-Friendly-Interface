package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterTest extends SupplyTest {
    private Water water;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        water = new Water();
    }

    @Test
    public void testDateAllocation() {
        water.setDateAllocated("2024-10-31");
        assertEquals("2024-10-31", water.getDateAllocated());
    }

    @Test
    public void testRemoveAllocationDate() {
        water.setDateAllocated("2024-10-31");
        water.removeAllocationDate();
        assertNull(water.getDateAllocated());
    }

    @Test
    public void testLocation() {
        Location location = new Location("University of Calgary", "2500 University Dr NW");  // Added address parameter
        water.addLocation(location);
        assertEquals(location, water.getLocation());
        water.removeLocation(location);
        assertNull(water.getLocation());
    }

    @Test
    public void testOwner() {
        DisasterVictim owner = new DisasterVictim("Cornelia ten Boom", "2025-01-18");
        water.addOwner(owner);
        assertEquals(owner, water.getOwner());
        water.removeOwner(owner);
        assertNull(water.getOwner());
    }
}