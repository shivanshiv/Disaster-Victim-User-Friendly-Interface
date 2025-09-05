package edu.ucalgary.oop;

public class Supply {
    private String type;
    private String allocation;
    private DisasterVictim owner;
    private Location location;

    public Supply(String type) {
        this.type = type;
        this.allocation = "Unallocated";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DisasterVictim getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public String getAllocationStatus() {
        return allocation;
    }

    public void setAllocation(DisasterVictim owner, Location location) {
        if ("Personal Belonging".equalsIgnoreCase(this.type)) {
            if (owner != null) {
                this.owner = owner;
                this.location = null;
                this.allocation = "Allocated to " + owner.getFirstName() + " " + owner.getLastName();
            } else {
                throw new IllegalArgumentException("Personal belongings must be allocated to an individual.");
            }
        } else {
            if (owner != null && location != null && location.equals(owner.getLocation())) {
                this.owner = owner;
                this.location = null;
                this.allocation = "Allocated to " + owner.getFirstName() + " " + owner.getLastName();
            } else if (location != null && owner == null) {
                this.location = location;
                this.owner = null;
                this.allocation = "Allocated to location: " + location.getName();
            } else {
                throw new IllegalArgumentException("Invalid allocation: either provide a valid location or match victim and location.");
            }
        }
    }

    public void deallocation() {
        this.owner = null;
        this.location = null;
        this.allocation = "Unallocated";
    }
}
