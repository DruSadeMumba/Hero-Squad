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
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}