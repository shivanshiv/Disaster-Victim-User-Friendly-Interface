package edu.ucalgary.oop;

public class PersonalBelonging extends Supply {
    private String name;
    private String description;
    private DisasterVictim owner;

    public PersonalBelonging(String name, String description) {
        super("Personal Belonging");
        this.name = name;
        this.description = description;
        this.owner = null;
    }

    public String getDescription() {
        return description;
    }

    public DisasterVictim getOwner() {
        return owner;
    }

    public void addOwner(DisasterVictim owner) {
        this.owner = owner;
    }

    public void addLocation(Location location) {
        throw new IllegalArgumentException("Personal belongings cannot be allocated to a location");
    }
}

