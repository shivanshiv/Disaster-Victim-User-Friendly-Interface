package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class InquirerTest {
    private String expectedFirstName = "John";
    private String expectedLastName = "Doe";
    private String expectedPhoneNumber = "+1-123-456-7890";
    private String expectedMessage = "looking for my family members";
    private Inquirer inquirer = new Inquirer(expectedFirstName, expectedLastName, expectedPhoneNumber, expectedMessage);
    private DisasterVictim victimInquirer = new DisasterVictim("Freda", "McDonald", "2025-01-18", "2001-10-18");


    private Inquirer victimAsInquirer = new Inquirer(victimInquirer, "Looking for my children");

    @Before
    public void setUp() {
        // If you want to test getVictimInquirer with a non-null value
        //inquirer.setVictimInquirer(victimInquirer);
    }

    @Test
    public void testObjectCreation() {
        assertNotNull(inquirer);
    }

    @Test
    public void testGetFirstName() {
        assertEquals("getFirstName() should return inquirer's first name", expectedFirstName, inquirer.getFirstName());
    }



    @Test
    public void testGetServicesPhoneNum() {
        assertEquals("getServicesPhoneNum() should return the correct Services Number", expectedPhoneNumber, inquirer.getServicesPhoneNum());
    }

    @Test
    public void testGetInfo() {
        assertEquals("getInfo() should return the inquirer message", expectedMessage, inquirer.getInfo());
    }

    @Test
    public void testVictimAsInquirer() {
        assertEquals("getFirstName() should return the victim's name", "Freda", victimAsInquirer.getFirstName());
        assertEquals("getLastName() should return the victim's last name", "McDonald", victimAsInquirer.getLastName());
        assertEquals("getInfo() should return the victim's inquiry message", "Looking for my children", victimAsInquirer.getInfo());
    }

    @Test
    public void testGetVictimInquirer_NullCase() {
        assertNull("VictimInquirer should be null when not set in constructor", inquirer.getVictimInquirer());
    }

    @Test
    public void testGetVictimInquirer_NonNullCase() {
        assertNotNull("VictimInquirer should not be null when constructed from a victim", victimAsInquirer.getVictimInquirer());
        assertEquals("VictimInquirer should return the correct DisasterVictim", victimInquirer.getFirstName(), victimAsInquirer.getVictimInquirer().getFirstName());
    }


}
