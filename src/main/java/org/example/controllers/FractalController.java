package org.example.controllers;

import org.example.Mandelbrot;
import org.example.core.Template;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FractalController {

    private final Mandelbrot mandelbrot;

    public FractalController(){
        mandelbrot = new Mandelbrot(1000,1000,1000);
    }

    public String getFractal(Request request, Response response){
        BufferedImage image = mandelbrot.createFractal(0.35,0.095,0.009);

        try {
            ImageIO.write(image, "png", new File("mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Fractal";
    }
}
