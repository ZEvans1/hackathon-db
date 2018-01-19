package dao;

import models.Member;
import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oTeamDaoTest {

    private Sql2oTeamDao teamDao;
    private Sql2oMemberDao memberDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        teamDao = new Sql2oTeamDao(sql2o);
        memberDao = new Sql2oMemberDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }


    public Team setupNewTeam() {
        return new Team("Team One", "Desc.");
    }

    @Test
    public void newTeam_generatesUniqueId_1() throws Exception {
        Team team = setupNewTeam();
        int originalTeamId = team.getId();
        teamDao.add(team);
        assertNotEquals(originalTeamId, team.getId());
    }

    @Test
    public void newTeam_getAllTeamsFromDB_2() throws Exception {
        Team team = setupNewTeam();
        Team team2 = setupNewTeam();
        teamDao.add(team);
        teamDao.add(team2);
        assertEquals(2, teamDao.getAll().size());
    }

//    @Test
//    public void newTeam_canBeFoundById_true() throws Exception {
//        Team team = setupNewTeam();
//        int originalTeamId = team.getId();
//        teamDao.add(team);
//        assertNotEquals(originalTeamId, team.getId());
//    }

    @Test
    public void newTeam_canBeFoundById() throws Exception {
        Team team = setupNewTeam();
        teamDao.add(team);
        Team foundTeam = teamDao.findById(team.getId());
        assertEquals(team, foundTeam);
    }


    @Test
    public void newTeam_getsAllMembersInTeam_n() {
        Team team = setupNewTeam();
        teamDao.add(team);
        int teamId = team.getId();
        Member member = new Member("M1", teamId);
        Member member2 = new Member("M2", teamId);
        memberDao.add(member);
        memberDao.add(member2);

        assertTrue(teamDao.getAllMembersByTeam(teamId).size()==2);
    }

    @Test
    public void newTeam_updateChangesTeamName() throws Exception {
        String initialName = "Team1";
        Team team = new Team(initialName, "hey");
        teamDao.add(team);
        teamDao.update(team.getId(),"Team One");
        Team updatedTeam = teamDao.findById(team.getId());
        assertNotEquals(initialName, updatedTeam.getName());
    }

    @Test
    public void newTeam_canBeDeleted() throws Exception {
        Team team = setupNewTeam();
        teamDao.add(team);
        teamDao.deleteById(team.getId());
        assertEquals(1, teamDao.getAll().size());
    }
}