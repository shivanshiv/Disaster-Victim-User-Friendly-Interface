package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class FamilyGroup {
    private String id;
    private List<DisasterVictim> members;

    public FamilyGroup(String id) {
        this.id = id;
        this.members = new ArrayList<>();
    }

    public String getFamilyGroupID() {
        return id;
    }

    public void addMember(DisasterVictim member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    public void removeMember(DisasterVictim member) {
        members.remove(member);
    }

    public DisasterVictim[] getMembers() {
        return members.toArray(new DisasterVictim[0]);
    }
}