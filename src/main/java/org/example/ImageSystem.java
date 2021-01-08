package org.example;

import java.awt.image.BufferedImage;

public class ImageSystem {
    private final Mandelbrot mandelbrot;

    public ImageSystem(){
        mandelbrot = new Mandelbrot(5000);
    }

    public BufferedImage getFractalImage(int width , int height, double x, double y, double z){
        BufferedImage image = mandelbrot.createFractal(width,height,x,y,z);
        return image;
    }
}
