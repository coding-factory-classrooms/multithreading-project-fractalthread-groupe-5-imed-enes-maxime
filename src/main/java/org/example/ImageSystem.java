package org.example;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;

public class ImageSystem {
    private final Mandelbrot mandelbrot;
    private final LRUCache<String, BufferedImage> cache;

    public ImageSystem(){
        cache = new LRUCache<>();
        mandelbrot = new Mandelbrot(5000,32);
    }

    public BufferedImage getFractalImage(int width , int height, double x, double y, double z){

        BufferedImage image = null;

        Coord coord = new Coord(width, height, x, y, z);
        if (cache.get(coord.toString()) != null){
            image = cache.get(coord.toString());
        }else{
            System.out.println(coord.toString());

            ExecutorService threadPool = new ThreadPool(16);
            try {
                image = mandelbrot.createFractal(width,height,x,y,z,threadPool);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cache.put(coord.toString(), image);
        }

        return image;
    }
}
