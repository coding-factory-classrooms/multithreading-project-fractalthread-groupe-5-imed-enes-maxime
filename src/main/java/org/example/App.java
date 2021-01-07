package org.example;

import org.example.controllers.FractalController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        initialize();

        ImageSystem imageSystem = new ImageSystem();

        HomeController homeController = new HomeController();
        FractalController fractalController = new FractalController(imageSystem);

        Spark.get("/", homeController::getHome);
        Spark.get("/fractal/:x/:y/:z", fractalController::getFractal);
        Spark.get("/fractal/:x/:y/:z/details", fractalController::getFractalDetails);
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
