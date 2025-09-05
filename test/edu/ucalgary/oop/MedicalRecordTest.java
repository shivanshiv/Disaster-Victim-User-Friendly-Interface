package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class MedicalRecordTest {

    Location expectedLocation = new Location("University of Calgary", "2500 University Dr NW");
    private String expectedTreatmentDetails = "Twisted treated";
    private String expectedDateOfTreatment = "2025-01-20";
    private String validDateOfTreatment = "2025-10-31";
    private String inValidDateOfTreatment = "2025/10/31";
    MedicalRecord medicalRecord = new MedicalRecord(expectedLocation, expectedTreatmentDetails, expectedDateOfTreatment);


    @Test
    public void testObjectCreation() {
        assertNotNull(medicalRecord);
    }	
	
    @Test
    public void testGetLocation() {
    assertEquals("getLocation should return the correct Location", expectedLocation, medicalRecord.getLocation());
    }

 @Test
    public void testSetLocation() {
	Location newExpectedLocation = new Location("Telus Convention Centre", "136 8 Ave SE");
	medicalRecord.setLocation(newExpectedLocation);
        assertEquals("setLocation should update the Location", newExpectedLocation.getName(), medicalRecord.getLocation().getName());
    }

    @Test
    public void testGetTreatmentDetails() {
        assertEquals("getTreatmentDetails should return the correct treatment details", expectedTreatmentDetails, medicalRecord.getTreatmentDetails());
    }
@Test
    public void testSetTreatmentDetails() {
	String newExpectedTreatment = "Treatment not required";
	medicalRecord.setTreatmentDetails(newExpectedTreatment);
    assertEquals("setTreatmentDetails should update the treatment details", newExpectedTreatment, medicalRecord.getTreatmentDetails());
    }


    @Test
    public void testGetDateOfTreatment() {
    assertEquals("getDateOfTreatment should return the correct date of treatment", expectedDateOfTreatment, medicalRecord.getDateOfTreatment());
    }
	
	@Test
    public void testSetDateOfTreatment() {
	String newExpectedDateOfTreatment = "2025-02-05";
	medicalRecord.setDateOfTreatment(newExpectedDateOfTreatment);
    assertEquals("setDateOfTreatment should update date of treatment", newExpectedDateOfTreatment, medicalRecord.getDateOfTreatment());
    }
	@Test
    public void testSetDateOfTreatmentWithValidFormat() {
        
        medicalRecord.setDateOfTreatment(validDateOfTreatment);
    }

    @Test
    public void testSetDateOfBirthWithInvalidFormat() {
        boolean expectedValue = false;
        String noException = "There was no exception thrown";

        try {
           medicalRecord.setDateOfTreatment(inValidDateOfTreatment);
        }
        catch (IllegalArgumentException e) {
           expectedValue = true;
        }
        catch (Exception e) {
           noException = "Incorrect type of exception was thrown";
        }

        String message = "setDateOfTreatment() should throw an IllegalArgumentException with invalid date format '" + inValidDateOfTreatment + "' but " + noException + ".";
        assertTrue(message, expectedValue);
    }

    @Test
    public void testSetDateOfBirthWithNotADate() {
        boolean expectedValue = false;
        String noException = "There was no exception thrown";

        try {
           medicalRecord.setDateOfTreatment(expectedTreatmentDetails);
        }
        catch (IllegalArgumentException e) {
            expectedValue = true;
        }
        catch (Exception e) {
            noException = "Incorrect type of exception was thrown";
        }

        String message = "setDateOfTreatment() should throw an IllegalArgumentException with invalid non-date input '" + inValidDateOfTreatment + "' but " + noException + ".";
        assertTrue(message, expectedValue);
    }
}

