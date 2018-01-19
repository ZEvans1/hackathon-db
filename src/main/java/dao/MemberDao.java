package dao;

import models.Member;

import java.util.List;

public interface MemberDao {

    //create
    void add (Member member);

    //read
    List<Member> getAll();
    Member findMemberById(int id);

    //update
    void update(int id, String name, int teamId);

    //delete
    void deleteById(int id);

}
