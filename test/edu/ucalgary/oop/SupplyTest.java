package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SupplyTest {
    private Supply supply;
    private Location location;
    private DisasterVictim owner;

    @Before
    public void setUp() {
        supply = new Supply("Suitcase");
        location = new Location("University of Calgary", "2500 University Dr NW, Calgary, AB");

        // ENTRY_DATE must be before dateOfBirth to pass validation
        owner = new DisasterVictim("Freda", "2023-01-01", "2000-05-15");
        owner.setLastName("McDonald");
        owner.setLocation(location); // ensure location matches for allocation
    }

    @Test
    public void testGetType() {
        assertEquals("Suitcase", supply.getType());
    }

    @Test
    public void testSetType() {
        supply.setType("Purse");
        assertEquals("Purse", supply.getType());
    }

    @Test
    public void testAllocationToLocation() {
        supply.setAllocation(null, location);
        assertEquals("Allocated to location: University of Calgary", supply.getAllocationStatus());
    }

    @Test
    public void testAllocationToIndividual() {
        supply.setAllocation(owner, location);
        assertEquals("Allocated to Freda McDonald", supply.getAllocationStatus());
    }

    @Test
    public void testDeallocation() {
        supply.setAllocation(null, location);
        supply.deallocation();
        assertEquals("Unallocated", supply.getAllocationStatus());
    }

    @Test
    public void testPersonalBelongingOnlyAllocatedToIndividual() {
        Supply belonging = new Supply("Personal Belonging");
        belonging.setAllocation(owner, null);
        assertEquals("Allocated to Freda McDonald", belonging.getAllocationStatus());
    }
}
