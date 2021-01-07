package org.example.controllers;

import org.example.Mandelbrot;
import org.example.core.Template;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class FractalController {

    private final Mandelbrot mandelbrot;

    public FractalController(){
        mandelbrot = new Mandelbrot(1000,1000,1000);
    }

    public Response getFractal(Request request, Response response){
        double x = Double.parseDouble(request.params(":x"));
        double y = Double.parseDouble(request.params(":y"));
        double zoom = Double.parseDouble(request.params(":zoom"));

        BufferedImage image = mandelbrot.createFractal(x,y,zoom);
        response.raw().setContentType("image/jpeg");

        try (OutputStream out = response.raw().getOutputStream()) {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getFractalDetails(Request request, Response response){
        double x = Double.parseDouble(request.params(":x"));
        double y = Double.parseDouble(request.params(":y"));
        double zoom = Double.parseDouble(request.params(":zoom"));

        HashMap<String,Object> model = new HashMap<>();
        model.put("imageSrc","/fractal/"+x+"/"+y+"/"+zoom);

        return Template.render("fractalDetails.html", model);
    }
}
