package edu.ucalgary.oop;

public class Water extends Supply {
    private String dateAllocated;
    private Location location;
    private DisasterVictim owner;
    public Water() {
        super("Water");
    }

    public void setDateAllocated(String date) {
        this.dateAllocated = date;
    }

    public String getDateAllocated() {
        return dateAllocated;
    }

    public void removeAllocationDate() {
        this.dateAllocated = null;
    }

    public void addLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void removeLocation(Location location) {
        if (this.location == location) {
            this.location = null;
        }
    }

    public void addOwner(DisasterVictim owner) {
        this.owner = owner;
    }

    public DisasterVictim getOwner() {
        return owner;
    }

    public void removeOwner(DisasterVictim owner) {
        if (this.owner == owner) {
            this.owner = null;
        }
    }
}