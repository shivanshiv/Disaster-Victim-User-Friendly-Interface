package edu.ucalgary.oop;

public class Blanket extends Supply {
    private DisasterVictim owner;
    private Location location;

    public Blanket() {
        super("Blanket");
    }

    public Location getLocation() {
        return location;
    }

    public void addLocation(Location location) {
        this.location = location;
    }

    public void removeLocation(Location location) {
        if (this.location != null && this.location.equals(location)) {
            this.location = null;
        }
    }

    public DisasterVictim getOwner() {
        return owner;
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
