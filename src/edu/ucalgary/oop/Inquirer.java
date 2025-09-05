package edu.ucalgary.oop;

public class Inquirer {
    private String FIRST_NAME;
    private String LAST_NAME;
    private String INFO;
    private String phone;
    private DisasterVictim victimInquirer;

    public Inquirer(String firstName, String lastName, String phone, String info) {
        if (firstName != null && firstName.contains(" ") && lastName == null) {
            String[] nameParts = firstName.split(" ", 2);
            this.FIRST_NAME = nameParts[0];
            this.LAST_NAME = nameParts[1];
        } else {
            this.FIRST_NAME = firstName;
            this.LAST_NAME = lastName;
        }
        this.phone = phone;
        this.INFO = info;
    }

    public Inquirer(DisasterVictim victim, String info) {
        if (victim != null) {
            this.FIRST_NAME = victim.getFirstName();
            this.LAST_NAME = victim.getLastName();
            this.victimInquirer = victim;
        }
        this.INFO = info;
        this.phone = null;
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public String getLastName() {
        return LAST_NAME;
    }



    public String getServicesPhoneNum() {
        return phone;
    }

    public String getInfo() {
        return INFO;
    }

    public DisasterVictim getVictimInquirer() {
        return victimInquirer;
    }

    public void setVictimInquirer(DisasterVictim victim) {
        this.victimInquirer = victim;
    }
}