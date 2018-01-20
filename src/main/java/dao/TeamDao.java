package dao;

import models.Member;
import models.Team;

import java.util.List;

public interface TeamDao {


    void add (Team team);


    List<Team> getAll();
    List<Member> getAllMembersByTeam(int teamId);
    Team findById(int id);


    void update(int id, String name);


    void deleteById(int id);
    void deleteAllTeams();

}
