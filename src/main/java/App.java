import dao.Sql2oMemberDao;
import dao.Sql2oTeamDao;
import models.Member;
import models.Team;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oMemberDao memberDao = new Sql2oMemberDao(sql2o);
        Sql2oTeamDao teamDao = new Sql2oTeamDao(sql2o);

        //home
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAll();
            model.put("teams", teams);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //about
        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "about.hbs");
        }, new HandlebarsTemplateEngine());

        //delete all members
        get("/members/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            memberDao.deleteAllMembers();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //delete teams
        get("/teams/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            memberDao.deleteAllMembers();
            teamDao.deleteAllTeams();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //new team form
        get("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "form.hbs");
        }, new HandlebarsTemplateEngine());

        //process team form
        post("/teams/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String description = request.queryParams("description");
            Team newTeam = new Team(name,description);
            teamDao.add(newTeam);
            List<Team> teams = teamDao.getAll();
            model.put("teams", teams);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //show team update form
        get("/teams/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editTeam", true);
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "team-add.hbs");
        }, new HandlebarsTemplateEngine());

        //process a form to update a team
        post("/teams/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToEdit = Integer.parseInt(request.queryParams("editTeamId"));
            String newName = request.queryParams("newTeamName");
            List<Team> teams = teamDao.getAll();
            model.put("teams", teams);
            teamDao.update(idOfTeamToEdit, newName);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //show new member form
        get("/members/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "member-add.hbs");
        }, new HandlebarsTemplateEngine());

        //process new member form
        post("/members/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
//            List<Team> allcategories = categoryDao.getAll();
//            model.put("categories", allcategories);
            String name = request.queryParams("name");
            int teamId = Integer.parseInt(request.queryParams("teamId"));
            Member newMember = new Member(name, teamId);
            memberDao.add(newMember);
            model.put("member", newMember);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //show a form to update a member
        get("/members/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            List<Member> allMembers = memberDao.getAll();
            model.put("members", allMembers);
            model.put("editMember", true);
            return new ModelAndView(model, "member-add.hbs");
        }, new HandlebarsTemplateEngine());

        //process form to update member
        post("/members/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            String newName = req.queryParams("name");
            int newTeamId = Integer.parseInt(req.queryParams("teamId"));
            int idOfMemberToEdit = Integer.parseInt(req.queryParams("memberToEditId"));
            Member editMember = memberDao.findMemberById(idOfMemberToEdit);
            memberDao.update(idOfMemberToEdit, newName, newTeamId);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //show a team
        get("/teams/:teamId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToFind = Integer.parseInt(request.params("teamId"));
            Team foundTeam = teamDao.findById(idOfTeamToFind);
            model.put("team", foundTeam);
            List<Member> allMembersByTeam = teamDao.getAllMembersByTeam(idOfTeamToFind);
            model.put("members", allMembersByTeam);
            return new ModelAndView(model, "team-detail.hbs");
        }, new HandlebarsTemplateEngine());
    }
}