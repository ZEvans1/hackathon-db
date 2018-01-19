package dao;

import models.Member;
import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class Sql2oMemberDaoTest {
    private Sql2oMemberDao memberDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        memberDao = new Sql2oMemberDao(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Member setupNewMember() {
        return new Member("Member1", 1);
    }

    @Test
    public void newMember_generatesUniqueId_1() throws Exception {
        Member member = setupNewMember();
        int originalMemberId = member.getId();
        memberDao.add(member);
        assertNotEquals(originalMemberId, member.getId());
    }

    @Test
    public void newMember_returnAllMemberObjectsInDB_2() throws Exception {
        Member member = setupNewMember();
        Member member2 = setupNewMember();
        memberDao.add(member);
        memberDao.add(member2);
        assertEquals(2, memberDao.getAll().size());
    }

    @Test
    public void newMember_returnAMemberById_true() throws Exception {
        Member member = setupNewMember();
        memberDao.add(member);
        assertEquals("Member1",memberDao.findMemberById(1).getName());
    }

    @Test
    public void newMember_updateChangesMemberName() throws Exception {
        String initialName = "Member1";
        Member member = new Member(initialName, 1);
        memberDao.add(member);
        memberDao.update(member.getId(),"MemberOne",1);
        Member updatedMember = memberDao.findMemberById(member.getId());
        assertNotEquals(initialName, updatedMember.getName());
    }

    @Test
    public void newMember_deleteByIdDeletesCorrectTask() throws Exception {
        Member member = setupNewMember();
        memberDao.add(member);
        memberDao.deleteById(member.getId());
        assertEquals(0, memberDao.getAll().size());
    }

    @Test
    public void deletesAllMembers() throws Exception {
        Member member = setupNewMember();
        Member member2 = setupNewMember();
        memberDao.add(member);
        memberDao.add(member2);
        int daoSize = memberDao.getAll().size();
        memberDao.deleteAllMembers();
        assertTrue(memberDao.getAll().size()==0);
    }

}