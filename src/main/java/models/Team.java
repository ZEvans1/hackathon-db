package models;

import java.util.ArrayList;

public class Team {
    private String name;
    private String description;
    private String member;
    private String newMember;
    private int id;
    private ArrayList<String> memberList;
    private static ArrayList<Team> instances = new ArrayList<>();



    public Team(String name, String description, String member, ArrayList<String> memberList) {
        this.name = name;
        this.description = description;
        this.member = member;
        this.memberList = memberList;
        memberList.add(member);
        instances.add(this);
        this.id = instances.size();

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMember() {
        return member;
    }

    public String getNewMember() {
        return newMember;
    }

    public int getId() {
        return id;
    }

    public static Team findById(int id) {
        return instances.get(id-1);
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public ArrayList<String> addNewMember() {
        memberList.add(newMember);
        return memberList;
    }

    public ArrayList<String> addAnotherMember() {
        memberList.add(newMember);
        return memberList;
    }

    public static ArrayList<Team> getAll() {
        return instances;
    }

    public void addsNewMember(String newMember) {
        this.newMember = newMember;
    }

    public static void clearAllTeams() {
        instances.clear();
    }

    public void update(String name) {
        this.name = name;
    }
}
