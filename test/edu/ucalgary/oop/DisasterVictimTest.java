package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class DisasterVictimTest {
    private DisasterVictim victim;
    private Supply supply;
    private FamilyGroup group;
    private String expectedFirstName = "Freda";
    private String EXPECTED_ENTRY_DATE = "2025-01-18";
    private String validDate = "2025-01-15";
    private String invalidDate = "15/13/2025";
    private String expectedGender = "female";
    private String expectedComments = "Needs medical attention and speaks 2 languages";
    private List<Supply> suppliesToSet;

    @Before
    public void setUp() {
        victim = new DisasterVictim(expectedFirstName, EXPECTED_ENTRY_DATE);
        suppliesToSet = new ArrayList<>();
        suppliesToSet.add(new Supply("Water Bottle"));
        suppliesToSet.add(new Supply("Blanket"));

        DisasterVictim victim1 = new DisasterVictim("Jane", "2025-01-20");
        DisasterVictim victim2 = new DisasterVictim("John", "2025-01-22");
    }

    @Test
    public void testBirthdateConstructorWithValidEntryDate() {
        String validEntryDate = "2025-02-18";
        String validBirthdate = "2017-03-20";
        DisasterVictim victim = new DisasterVictim("Freda", validEntryDate, validBirthdate);
        assertNotNull("Constructor should successfully create an instance with a valid entry date", victim);
        assertEquals("Constructor should set the entry date correctly", validEntryDate, victim.getEntryDate());
        assertEquals("Constructor should set the birth date correctly", validBirthdate, victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithInvalidEntryDateFormat() {
        String invalidEntryDate = "20250112";
        String validBirthdate = "2017-03-20";
        new DisasterVictim("Fang", invalidEntryDate, validBirthdate);
        // Expecting IllegalArgumentException due to invalid date format
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithInvalidBirthdate() {
        String validEntryDate = "2025-02-18";
        String invalidBirthDate = "20250112";
        new DisasterVictim("Yaw", validEntryDate, invalidBirthDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdateConstructorWithBirthdateAfterEntryDate() {
        String validEntryDate = "2025-02-17";
        String validBirthDate = "2025-02-18";
        new DisasterVictim("Jessica", validEntryDate, validBirthDate);
    }

    @Test
    public void testConstructorWithValidEntryDate() {
        String validEntryDate = "2025-01-18";
        DisasterVictim victim = new DisasterVictim("Freda", validEntryDate);
        assertNotNull("Constructor should successfully create an instance with a valid entry date", victim);
        assertEquals("Constructor should set the entry date correctly", validEntryDate, victim.getEntryDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidEntryDateFormat() {
        String invalidEntryDate = "18/01/2025";
        new DisasterVictim("Freda", invalidEntryDate);
    }

    @Test
    public void testSetDateOfBirth() {
        String newDateOfBirth = "1987-05-21";
        victim.setDateOfBirth(newDateOfBirth);
        assertEquals("setDateOfBirth should correctly update the date of birth", newDateOfBirth, victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidFormat() {
        victim.setDateOfBirth(invalidDate); // This format should cause an exception
    }

    @Test
    public void testSetAndGetFirstName() {
        String newFirstName = "Alice";
        victim.setFirstName(newFirstName);
        assertEquals("setFirstName should update and getFirstName should return the new first name", newFirstName, victim.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String newLastName = "Smith";
        victim.setLastName(newLastName);
        assertEquals("setLastName should update and getLastName should return the new last name", newLastName, victim.getLastName());
    }

    @Test
    public void testGetComments() {
        victim.setComments(expectedComments);
        assertEquals("getComments should return the initial correct comments", expectedComments, victim.getComments());
    }

    @Test
    public void testSetComments() {
        victim.setComments(expectedComments);
        String newComments = "Has a minor injury on the left arm";
        victim.setComments(newComments);
        assertEquals("setComments should update the comments correctly", newComments, victim.getComments());
    }

    @Test
    public void testGetAssignedSocialID() {
        DisasterVictim newVictim = new DisasterVictim("Kash", "2025-01-21");
        int expectedSocialId = newVictim.getAssignedSocialID() + 1;
        DisasterVictim actualVictim = new DisasterVictim("Adeleke", "2025-01-22");
        assertEquals("getAssignedSocialID should return the expected social ID", expectedSocialId, actualVictim.getAssignedSocialID());
    }

    @Test
    public void testGetEntryDate() {
        assertEquals("getEntryDate should return the expected entry date", EXPECTED_ENTRY_DATE, victim.getEntryDate());
    }

    @Test
    public void testSetAndGetGender() {
        String[] validGenders = {"man", "woman", "non-binary"};

        for (String gender : validGenders) {
            victim.setGender(gender);
            assertEquals("setGender should correctly set gender for: " + gender, gender.toLowerCase(), victim.getGender());
        }
    }





    @Test
    public void testAddPersonalBelonging() {
        Supply newSupply = new Supply("Jacket");
        victim.addPersonalBelonging(newSupply);
        List<Supply> testSupplies = victim.getPersonalBelongings();
        assertTrue("addPersonalBelonging should add the supply to personal belongings", testSupplies.contains(newSupply));
    }

    @Test
    public void testRemovePersonalBelonging() {
        Supply supplyToRemove = suppliesToSet.get(0);
        victim.addPersonalBelonging(supplyToRemove);
        victim.removePersonalBelonging(supplyToRemove);
        List<Supply> testSupplies = victim.getPersonalBelongings();
        assertFalse("removePersonalBelonging should remove the supply from personal belongings", testSupplies.contains(supplyToRemove));
    }

    @Test
    public void testSetMedicalRecords() {
        Location testLocation = new Location("Shelter Z", "1234 Shelter Ave");
        MedicalRecord testRecord = new MedicalRecord(testLocation, "test for strep", "2025-02-09");

        List<MedicalRecord> newRecords = new ArrayList<>();
        newRecords.add(testRecord);

        victim.setMedicalRecords(newRecords);
        List<MedicalRecord> actualRecords = victim.getMedicalRecords();

        assertEquals("setMedicalRecords should set the correct number of records", newRecords.size(), actualRecords.size());
        assertTrue("setMedicalRecords should correctly update medical records", actualRecords.contains(testRecord));
    }

    @Test
    public void testSetPersonalBelongings() {
        Supply one = new Supply("Tent");
        Supply two = new Supply("Jug");
        List<Supply> newSupplies = new ArrayList<>();
        newSupplies.add(one);
        newSupplies.add(two);

        victim.setPersonalBelongings(newSupplies);
        List<Supply> actualSupplies = victim.getPersonalBelongings();

        assertEquals("setPersonalBelongings should set the correct number of supplies", newSupplies.size(), actualSupplies.size());
        assertTrue("setPersonalBelongings should include the first supply", actualSupplies.contains(one));
        assertTrue("setPersonalBelongings should include the second supply", actualSupplies.contains(two));
    }

    @Test
    public void testSetGender() {
        String[] validGenders = {"man", "woman", "non-binary"};

        for (String gender : validGenders) {
            victim.setGender(gender);
            assertEquals("setGender should correctly set gender for: " + gender, gender.toLowerCase(), victim.getGender());
        }
    }

    @Test
    public void testSetGenderAsOther() {
        String otherGender = "other";
        victim.setGender(otherGender);
        assertEquals("setGender should handle 'other' gender correctly", otherGender.toLowerCase(), victim.getGender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetGenderInvalid() {
        String invalidGender = "alien";
        victim.setGender(invalidGender);
    }

    @Test
    public void testSetFamilyGroup() {
        FamilyGroup expectedGroup = new FamilyGroup("00001");
        DisasterVictim victim1 = new DisasterVictim("Freda", "2025-01-20");
        victim1.setFamilyGroup(expectedGroup);
        assertEquals("The family group should be set correctly", expectedGroup, victim1.getFamilyGroup());
    }

    @Test
    public void testGetFamilyGroup() {
        FamilyGroup expectedGroup = new FamilyGroup("00001");

        DisasterVictim victim1 = new DisasterVictim("Cornelia", "2025-01-20");
        DisasterVictim victim2 = new DisasterVictim("Teruya", "2025-01-20");

        expectedGroup.addMember(victim1);
        expectedGroup.addMember(victim2);

        victim1.setFamilyGroup(expectedGroup);
        FamilyGroup actualGroup = victim1.getFamilyGroup();

        assertEquals("The family group should be retrieved correctly", expectedGroup, actualGroup);
    }

    @Test
    public void testSetAndGetLocation() {
        Location newLocation = new Location("Shelter A", "123 Main St");
        victim.setLocation(newLocation);
        assertEquals("setLocation and getLocation should work correctly", newLocation, victim.getLocation());
    }
}



    






