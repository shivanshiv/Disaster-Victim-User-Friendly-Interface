package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class LocationTest {
    private Location location;
    private DisasterVictim victim;
    private Supply supply;

    @Before
    public void setUp() {
        location = new Location("University of Calgary", "2500 University Dr NW");
        victim = new DisasterVictim("Freda McDonald", "2025-01-18");
        supply = new Supply("Jacket");
    }

    @Test
    public void testConstructor() {
        assertNotNull("Constructor should create a non-null Location object", location);
        assertEquals("Constructor should set the name correctly", "University of Calgary", location.getName());
        assertEquals("Constructor should set the address correctly", "2500 University Dr NW", location.getAddress());
    }

    @Test
    public void testSetName() {
        String newName = "TELUS Convention Centre";
        location.setName(newName);
        assertEquals("setName should update the name of the location", newName, location.getName());
    }

    @Test
    public void testSetAddress() {
        String newAddress = "136 8 Ave SE";
        location.setAddress(newAddress);
        assertEquals("setAddress should update the address of the location", newAddress, location.getAddress());
    }

    @Test
    public void testAddOccupant() {
        location.addOccupant(victim);
        assertTrue("addOccupant should add a disaster victim to the occupants list", location.getOccupants().contains(victim));
    }

    @Test
    public void testRemoveOccupant() {
        location.addOccupant(victim);
        location.removeOccupant(victim);
        assertFalse("removeOccupant should remove the disaster victim from the occupants list", location.getOccupants().contains(victim));
    }

    @Test
    public void testSetAndGetOccupants() {
        ArrayList<DisasterVictim> newOccupants = new ArrayList<>();
        newOccupants.add(victim);
        location.setOccupants(newOccupants);
        assertTrue("setOccupants should replace the occupants list with the new list", location.getOccupants().containsAll(newOccupants));
    }

    @Test
    public void testAddSupply() {
        location.addSupply(supply);
        assertTrue("addSupply should add a supply to the supplies list", location.getSupplies().contains(supply));
    }

    @Test
    public void testRemoveSupply() {
        location.addSupply(supply);
        location.removeSupply(supply);
        assertFalse("removeSupply should remove the supply from the supplies list", location.getSupplies().contains(supply));
    }

    @Test
    public void testSetAndGetSupplies() {
        ArrayList<Supply> newSupplies = new ArrayList<>();
        newSupplies.add(supply);
        location.setSupplies(newSupplies);
        assertTrue("setSupplies should replace the supplies list with the new list", location.getSupplies().contains(supply));
    }
}