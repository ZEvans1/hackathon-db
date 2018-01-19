//import models.Team;
//import spark.ModelAndView;
//import spark.template.handlebars.HandlebarsTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import static spark.Spark.staticFileLocation;
//import static spark.Spark.get;
//import static spark.Spark.post;
//
//public class App {
//    public static void main(String[] args) {
//        staticFileLocation("/public");
//
//        //shows home page with all teams
//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            ArrayList<Team> teams = Team.getAll();
//            model.put("teams", teams);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //shows about page
//        get("/about", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "about.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //shows page for a new team form
//        get("/teams/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //process form
//        post("/teams/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String name = request.queryParams("name");
//            String description = request.queryParams("description");
//            String member = request.queryParams("member");
//            Team newTeam = new Team(name, description, member, new ArrayList<>());
//            model.put("team", newTeam);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //show individual team
//        get("/teams/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToFind = Integer.parseInt(request.params("id"));
//            Team foundTeam = Team.findById(idOfTeamToFind);
//            model.put("team", foundTeam);
//            return new ModelAndView(model, "team-detail.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //show add team member form
//        get("/teams/:id/add", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToAddMember = Integer.parseInt(request.params("id"));
//            Team addToTeam = Team.findById(idOfTeamToAddMember);
//            model.put("addToTeam", addToTeam);
//            return new ModelAndView(model, "team-add.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //process a form to add a team member
//        post("/teams/:id/add", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newMember = request.queryParams("newMember");
//            int idOfTeamToAddMember = Integer.parseInt(request.params("id"));
//            Team addToTeam = Team.findById(idOfTeamToAddMember);
//            addToTeam.addsNewMember(newMember);
//            addToTeam.addAnotherMember();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //show a form to update team name
//        get("/teams/:id/update", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToEdit = Integer.parseInt(request.params("id"));
//            Team editTeam = Team.findById(idOfTeamToEdit);
//            model.put("editTeam", editTeam);
//            return new ModelAndView(model, "name-edit.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //process a form to update team name
//        post("/teams/:id/update", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newName = request.queryParams("newName");
//            int idOfTeamToEdit = Integer.parseInt(request.params("id"));
//            Team editTeam = Team.findById(idOfTeamToEdit);
//            editTeam.update(newName);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//    }
//}
