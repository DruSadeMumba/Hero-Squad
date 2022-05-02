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
        get("/", (request, response) -> {
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroes", (request, response) -> {
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroessquad/new", (req, res) -> { //working
            return new ModelAndView(model, "heroesquad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/heroessquad", (req, res) -> { //new
            squadDao.addSquad(new Squad(req.queryParams("name"), req.queryParams("purpose"), Integer.parseInt(req.queryParams("maxSize"))));
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());
        get("/heroes/new", (req, res) -> { //working
            model.put("squads", squadDao.getAllSquads());
            return new ModelAndView(model, "heroes-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/heroes", (req, res) -> { //URL to make new task on POST route
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            heroesDao.add(new Heroes(req.queryParams("name"), Integer.parseInt(req.queryParams("age")), req.queryParams("powers"), req.queryParams("weakness"), Integer.parseInt(req.queryParams("squadId"))));
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());



        get("/villains", (request, response) -> {
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());
        get("/villainssquad/new", (req, res) -> { //working
            return new ModelAndView(model, "villainsquad-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/villainssquad", (req, res) -> { //new
            squadDao.addSquad(new Squad(req.queryParams("name"), req.queryParams("purpose"), Integer.parseInt(req.queryParams("maxSize"))));
            model.put("squads", squadDao.getAllSquads());
            model.put("heroes", heroesDao.getAll());
            return new ModelAndView(model, "villains.hbs");
        }, new HandlebarsTemplateEngine());

    }
}