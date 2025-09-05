package edu.ucalgary.oop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class FamilyGroupTest {
    private FamilyGroup familyGroup;
    private DisasterVictim victim1, victim2;

    @Before
    public void setUp() {
        familyGroup = new FamilyGroup("00001");
        victim1 = new DisasterVictim("Freda McDonald", "2025-01-18");
        victim2 = new DisasterVictim("Teruya Bouillon", "2025-01-18");
    }

    @Test
    public void testGetFamilyGroupID() {
        assertEquals("00001", familyGroup.getFamilyGroupID());
    }

    @Test
    public void testAddMember() {
        familyGroup.addMember(victim1);
        assertTrue(Arrays.asList(familyGroup.getMembers()).contains(victim1));
    }

    @Test
    public void testRemoveMember() {
        familyGroup.addMember(victim1);
        familyGroup.addMember(victim2);
        familyGroup.removeMember(victim1);
        assertFalse(Arrays.asList(familyGroup.getMembers()).contains(victim1));
    }

    @Test
    public void testGetMembers() {
        familyGroup.addMember(victim1);
        familyGroup.addMember(victim2);
        DisasterVictim[] expectedMembers = {victim1, victim2};
        assertArrayEquals(expectedMembers, familyGroup.getMembers());
    }
}
