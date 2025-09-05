package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DisasterVictim {
    private static int counter = 0;
    private int ASSIGNED_SOCIAL_ID;
    private String firstName;
    private String lastName;
    private String ENTRY_DATE;
    private String dateOfBirth;
    private String gender;
    private String comments;
    private FamilyGroup group;
    private List<MedicalRecord> medicalRecords;
    private List<Supply> personalBelongings;
    private Location location;

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final List<String> VALID_GENDERS = List.of("man", "woman", "non-binary", "other");

    public DisasterVictim(String firstName, String ENTRY_DATE) {
        this(firstName, ENTRY_DATE, null);
    }

    public DisasterVictim(String firstName, String ENTRY_DATE, String dateOfBirth) {
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid entry date format");
        }
        if (dateOfBirth != null && (!isValidDateFormat(dateOfBirth) || dateOfBirth.compareTo(ENTRY_DATE) > 0)) {
            throw new IllegalArgumentException("Invalid or future birth date");
        }

        this.firstName = firstName;
        this.ENTRY_DATE = ENTRY_DATE;
        this.dateOfBirth = dateOfBirth;
        this.ASSIGNED_SOCIAL_ID = ++counter;
        this.medicalRecords = new ArrayList<>();
        this.personalBelongings = new ArrayList<>();
    }

    public DisasterVictim(String firstName, String lastName, String ENTRY_DATE, String dateOfBirth) {
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid entry date format");
        }
        if (dateOfBirth != null && (!isValidDateFormat(dateOfBirth) || dateOfBirth.compareTo(ENTRY_DATE) > 0)) {
            throw new IllegalArgumentException("Invalid or future birth date");
        }

        this.firstName = firstName;
        this.ENTRY_DATE = ENTRY_DATE;
        this.dateOfBirth = dateOfBirth;
        this.ASSIGNED_SOCIAL_ID = ++counter;
        this.medicalRecords = new ArrayList<>();
        this.personalBelongings = new ArrayList<>();
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) {
        if (!isValidDateFormat(dateOfBirth) || dateOfBirth.compareTo(this.ENTRY_DATE) > 0) {
            throw new IllegalArgumentException("Invalid or future birth date");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public int getAssignedSocialID() { return ASSIGNED_SOCIAL_ID; }

    public String getEntryDate() { return ENTRY_DATE; }
    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        if (!VALID_GENDERS.contains(gender.toLowerCase())) {
            throw new IllegalArgumentException("Invalid gender");
        }
        this.gender = gender.toLowerCase();
    }


    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public FamilyGroup getFamilyGroup() { return group; }
    public void setFamilyGroup(FamilyGroup group) { this.group = group; }

    public List<MedicalRecord> getMedicalRecords() { return new ArrayList<>(medicalRecords); }
    public void addMedicalRecord(MedicalRecord record) { medicalRecords.add(record); }
    public void setMedicalRecords(List<MedicalRecord> records) {
        medicalRecords = new ArrayList<>(records);
    }

    public List<Supply> getPersonalBelongings() { return new ArrayList<>(personalBelongings); }
    public void addPersonalBelonging(Supply supply) { personalBelongings.add(supply); }
    public void removePersonalBelonging(Supply supply) { personalBelongings.remove(supply); }
    public void setPersonalBelongings(List<Supply> supplies) {
        personalBelongings = new ArrayList<>(supplies);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isValidDateFormat(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}

