package dao;

import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oTeamDaoTest {

    private Sql2oTeamDao teamDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        teamDao = new Sql2oTeamDao(sql2o);
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



}