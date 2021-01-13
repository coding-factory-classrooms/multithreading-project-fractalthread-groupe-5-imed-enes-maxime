package org.example.controllers;

import org.example.ImageSystem;
import org.example.core.Template;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class FractalController {
    private final ImageSystem imageSystem;

    public FractalController(ImageSystem imageSystem){
        this.imageSystem = imageSystem;
    }

    public Response getFractal(Request request, Response response){
        double x = Double.parseDouble(request.params(":x"));
        double y = Double.parseDouble(request.params(":y"));
        double z = Double.parseDouble(request.params(":z"));
        int width = Integer.parseInt(request.params(":width"));
        int height = Integer.parseInt(request.params(":height"));
        String algorithm = request.params(":algorithm");

        BufferedImage image = null;
        if(algorithm.equals("mandelbrot")){
            image = imageSystem.getMandelbrotFractalImage(width,height,x,y,z);
        }else{
            image = imageSystem.getJuliaFractalImage(width,height,x,y,z);
        }
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
        double z = Double.parseDouble(request.params(":z"));
        String algorithm = request.params(":algorithm");

        HashMap<String,Object> model = new HashMap<>();
        model.put("x",x);
        model.put("y",y);
        model.put("z",z);
        model.put("algorithm",algorithm);

        return Template.render("fractalDetails.html", model);
    }
}
