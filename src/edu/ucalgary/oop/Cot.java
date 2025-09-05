package edu.ucalgary.oop;

public class Cot extends Supply {
    private int room;
    private String gridLocation;
    private DisasterVictim owner;
    private Location location;

    public Cot(int room, String gridLocation) {
        super("Cot");
        this.room = room;
        this.gridLocation = gridLocation;
        this.owner = null;
        this.location = null;
    }

    public int getRoom() {
        return room;
    }

    public String getGridLocation() {
        return gridLocation;
    }

    public Location getLocation() {
        return location;
    }

    public DisasterVictim getOwner() {
        return owner;
    }

    public void addLocation(Location location) {
        this.location = location;
    }

    public void removeLocation(Location location) {
        if (this.location != null && this.location.equals(location)) {
            this.location = null;
        }
    }

    public void addOwner(DisasterVictim owner) {
        this.owner = owner;
    }

    public void removeOwner(DisasterVictim owner) {
        if (this.owner != null && this.owner.equals(owner)) {
            this.owner = null;
        }
    }
}
