import dao.SqlHeroesDao;
import dao.SqlSquadDao;
import models.Heroes;
import models.Squad;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(2345);

        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        SqlSquadDao squadDao = new SqlSquadDao(sql2o);
        SqlHeroesDao heroesDao = new SqlHeroesDao(sql2o);
        Map<String, Object> model = new HashMap<String, Object>();

        //landing
        get("/landing", (request, response) -> {
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

                //HEROES landing
        get("/", (request, response) -> { //1
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //create new squad
        get("/squads/new", (req, res) -> { //2
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/squads", (req, res) -> { // 3
            squadDao.addSquad(new Squad(req.queryParams("name"), req.queryParams("cause"), Integer.parseInt(req.queryParams("maxSize"))));
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //create new hero
        get("/heroes/new", (req, res) -> { //4
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "heroes-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/heroes", (req, res) -> {  //5
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            heroesDao.add(new Heroes(req.queryParams("name"), Integer.parseInt(req.queryParams("age")), req.queryParams("powers"), req.queryParams("weakness"), Integer.parseInt(req.queryParams("squadId"))));
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //delete all squads
        get("/squads/delete", (req, res) -> {  //6
            squadDao.clearAllSquads();
            heroesDao.clearAllHeroes();
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //delete all heroes
        get("/heroes/delete", (req, res) -> { //*****
            heroesDao.clearAllHeroes();
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //squad details
        get("/squads/:id", (req, res) -> { //7
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("id"))));
            model.put("heroes", squadDao.getAllHeroesBySquad(Integer.parseInt(req.params("id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "squad-details.hbs");
        }, new HandlebarsTemplateEngine());

        //edit squad
        get("/squads/:id/edit", (req, res) -> { //8
            model.put("editSquad", true);
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/squads/:id", (req, res) -> { //9
            squadDao.update(Integer.parseInt(req.params(":id")), req.queryParams("newSquadName"), req.queryParams("newSquadCause"), Integer.parseInt(req.params("newMaxSize")));
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //delete heroes in squad
        get("/squads/:squad_id/heroes/:heroes_id/delete", (req, res) -> { //10
            heroesDao.deleteById(Integer.parseInt(req.params("heroes_id")));
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());

        //heroes details
        get("/squads/:squad_id/heroes/:heroes_id", (req, res) -> { //11
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("squad_id"))));
            model.put("hero", heroesDao.findById(Integer.parseInt(req.params("heroes_id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "heroes-details.hbs");
        }, new HandlebarsTemplateEngine());

        //edit heroes
        get("/heroes/:id/edit", (req, res) -> { //12
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.findById(Integer.parseInt(req.params("id"))));
            model.put("editHeroes", true);
            return new ModelAndView(model, "heroes-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/heroes/:id", (req, res) -> { //13
            heroesDao.update(Integer.parseInt(req.params(":id")), req.queryParams("name"), Integer.parseInt(req.params("age")), req.queryParams("powers"), req.queryParams("weakness"), Integer.parseInt(req.params("squadId")));
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());




                //VILLAINS future development
        /*get("/villains", (request, response) -> { //1
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/new", (req, res) -> { //2
            return new ModelAndView(model, "villainsquad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/villainssquad", (req, res) -> { // 3
            squadDao.addSquad(new Squad(req.queryParams("name"), req.queryParams("purpose"), Integer.parseInt(req.queryParams("maxSize"))));
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villains/new", (req, res) -> {  //4
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "villains-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/villains", (req, res) -> {  //5
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            heroesDao.add(new Heroes(req.queryParams("name"), Integer.parseInt(req.queryParams("age")), req.queryParams("powers"), req.queryParams("weakness"), Integer.parseInt(req.queryParams("squadId"))));
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/delete", (req, res) -> { //6
            squadDao.clearAllSquads();
            heroesDao.clearAllHeroes();
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/:id", (req, res) -> { //7
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("id"))));
            model.put("heroes", squadDao.getAllHeroesBySquad(Integer.parseInt(req.params("id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "villainsquad-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/:id/edit", (req, res) -> { //8
            model.put("editSquad", true);
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "villainsquad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/villainssquad/:id", (req, res) -> { //9
            squadDao.update(Integer.parseInt(req.params(":id")), req.queryParams("newSquadName"), req.queryParams("newSquadCause"), Integer.parseInt(req.params("newMaxSize")));
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/:squad_id/villains/:hero_id/delete", (req, res) -> { //10
            heroesDao.deleteById(Integer.parseInt(req.params("hero_id")));
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/:squad_id/villains/:hero_id", (req, res) -> { //11
            model.put("squad", squadDao.findById(Integer.parseInt(req.params("squad_id"))));
            model.put("hero", heroesDao.findById(Integer.parseInt(req.params("hero_id"))));
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "villains-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villains/:id/edit", (req, res) -> { //12
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.findById(Integer.parseInt(req.params("id"))));
            model.put("editVillains", true);
            return new ModelAndView(model, "villains-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/villains/:id", (req, res) -> { //13
            heroesDao.update(Integer.parseInt(req.params(":id")), req.queryParams("name"), Integer.parseInt(req.params("age")), req.queryParams("powers"), req.queryParams("weakness"), Integer.parseInt(req.params("squadId")));
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());*/
    }
}