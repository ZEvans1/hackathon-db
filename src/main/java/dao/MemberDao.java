package dao;

import models.Member;

import java.util.List;

public interface MemberDao {


    void add (Member member);


    List<Member> getAll();
    Member findMemberById(int id);


    void update(int id, String name, int teamId);


    void deleteById(int id);
    void deleteAllMembers();

}
