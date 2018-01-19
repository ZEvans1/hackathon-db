import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TeamTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        Team.clearAllTeams();
    }

    @Test
    public void testTeam_instantiatesCorrectly_true() {
        Team testTeam = setUpNewTeam();
        assertEquals(true, testTeam instanceof Team);
    }

    @Test
    public void testTeam_getsProperties() {
        Team testTeam = setUpNewTeam();
        assertEquals("Team1", testTeam.getName());
        assertEquals("A team", testTeam.getDescription());
        assertEquals("Member1", testTeam.getMember());
        assertEquals(null, testTeam.getNewMember());
    }

    @Test
    public void testTeam_addsNewMembers() {
        Team testTeam = setUpNewTeam();
        String newMember = "Member2";
        assertEquals(2, testTeam.addNewMember().size());

    }

    @Test
    public void testTeams_correctlyReturned_true() {
        Team testTeam = setUpNewTeam();
        Team otherTeam = new Team("Team2", "Another team", "Member1", new ArrayList<>());
        assertTrue(Team.getAll().contains(testTeam));
        assertTrue(Team.getAll().contains(otherTeam));
    }

    @Test
    public void testTeam_getsCreatedWithId() throws Exception {
        Team testTeam = setUpNewTeam();
        assertEquals(1, testTeam.getId());
    }

    @Test
    public void testTeam_findReturnsCorrectTeam() throws Exception {
        Team testTeam = setUpNewTeam();
        assertEquals(1, Team.findById(testTeam.getId()).getId());
    }

    @Test
    public void testTeam_updateChangesTeamName() throws Exception {
        Team testTeam = setUpNewTeam();
        String formerName = testTeam.getName();
        int formerId = testTeam.getId();
        testTeam.update("A different name");
        assertEquals(formerId, testTeam.getId());
        assertNotEquals(formerName, testTeam.getName());
    }

    //helper
    public Team setUpNewTeam() {
        return new Team("Team1", "A team", "Member1", new ArrayList<>());
    }
}
