package org.example;

import java.awt.image.BufferedImage;

public class ImageSystem {
    private final Mandelbrot mandelbrot;
    private final LRUCache<String, BufferedImage> cache;

    public ImageSystem(){
        mandelbrot = new Mandelbrot(5000);
        cache = new LRUCache<>();
    }

    public BufferedImage getFractalImage(int width , int height, double x, double y, double z){

        BufferedImage image;

        Coord coord = new Coord(width, height, x, y, z);
        if (cache.get(coord.toString()) != null){
            image = cache.get(coord.toString());
        }else{
            image = mandelbrot.createFractal(width,height,x,y,z);
            cache.put(coord.toString(), image);
        }

        return image;
    }
}
