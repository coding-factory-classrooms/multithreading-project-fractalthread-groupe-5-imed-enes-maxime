package org.example;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageSystem {
    private final Mandelbrot mandelbrot;
    private final HashMap<String,BufferedImage> images = new HashMap<>();

    public ImageSystem(){
        mandelbrot = new Mandelbrot(1000,1000,1000);
    }

    public BufferedImage getFractalImage(double x, double y, double z){
        BufferedImage image = images.get(x+"/"+y+"/"+z);
        if(image == null){
            image = mandelbrot.createFractal(x,y,z);
            images.put(x+"/"+y+"/"+z,image);
        }
        return image;
    }
}
