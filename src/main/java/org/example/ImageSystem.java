package org.example;

import java.awt.image.BufferedImage;

public class ImageSystem {
    private final Mandelbrot mandelbrot;

    public ImageSystem(){
        mandelbrot = new Mandelbrot(5000);
    }

    public BufferedImage getFractalImage(int width , int height, double x, double y, double z){

        BufferedImage image;

        LRUCache<Coord, BufferedImage> cache = new LRUCache<>();
        Coord coord = new Coord(width, height, x, y, z);
        if (cache.get(coord) != null){
            image = cache.get(coord);
        }else{
            System.out.println(coord.toString());
            image = mandelbrot.createFractal(width,height,x,y,z);
            cache.put(coord, image);
        }


        return image;
    }
}
