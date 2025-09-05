package edu.ucalgary.oop;

public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;

    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, String dateOfInquiry, String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        this.dateOfInquiry = dateOfInquiry;
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    public Inquirer getInquirer() { return inquirer; }
    
    public void setInquirer(Inquirer inquirer) { this.inquirer = inquirer; }

    public DisasterVictim getMissingPerson() { return missingPerson; }

    public void setMissingPerson(DisasterVictim missingPerson) { this.missingPerson = missingPerson; }

    public String getDateOfInquiry() { return dateOfInquiry; }

    public void setDateOfInquiry(String dateOfInquiry) {
        if (!isValidDateFormat(dateOfInquiry)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    public String getInfoProvided() { return infoProvided; }

    public void setInfoProvided(String infoProvided) { this.infoProvided = infoProvided; }

    public Location getLastKnownLocation() { return lastKnownLocation; }

    public void setLastKnownLocation(Location lastKnownLocation) { this.lastKnownLocation = lastKnownLocation; }

    public String getLogDetails() {
        return "Inquirer: " + inquirer.getFirstName() + ", Missing Person: " + missingPerson.getFirstName() +
               ", Date of Inquiry: " + dateOfInquiry + ", Info Provided: " + infoProvided +
               ", Last Known Location: " + lastKnownLocation.getName();
    }

    public static boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
