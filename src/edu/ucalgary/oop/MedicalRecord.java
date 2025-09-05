package edu.ucalgary.oop;

public class MedicalRecord {
	
	private Location location; 
	private String treatmentDetails; 
	private String dateOfTreatment; 

	MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment){
		this.location = location; 
		this.treatmentDetails = treatmentDetails; 
		setDateOfTreatment(dateOfTreatment); 
	}

	public Location getLocation() { 
		return location; 
	}
	
	public String getTreatmentDetails() { 
		return treatmentDetails; 
	}
	
	public String getDateOfTreatment() { 
		return dateOfTreatment; 
	}

	public void setLocation(Location location) {
		this.location = location; 
	}
	
	public void setTreatmentDetails(String treatmentDetails) {
		this.treatmentDetails = treatmentDetails; 
	}
	
	public void setDateOfTreatment(String dateOfTreatment) {
		if (isValidDateFormat(dateOfTreatment)) {
            this.dateOfTreatment = dateOfTreatment;
        } 
		else {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
	}

	public boolean isValidDateFormat(String date) { 
		return date.matches("\\d{4}-\\d{2}-\\d{2}");
	}
}
