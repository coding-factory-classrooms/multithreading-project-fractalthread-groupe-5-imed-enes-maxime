package org.example;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;

public class ImageSystem {
    private final Mandelbrot mandelbrot;
    private final Julia julia;
    private final LRUCache<String, BufferedImage> cacheMandelbrot;
    private final LRUCache<String, BufferedImage> cacheJulia;

    public ImageSystem(){
        cacheMandelbrot = new LRUCache<>();
        cacheJulia = new LRUCache<>();
        mandelbrot = new Mandelbrot(5000,32);
        julia = new Julia(5000,32);
    }

    public BufferedImage getMandelbrotFractalImage(int width , int height, double x, double y, double z){

        BufferedImage image = null;

        Coord coord = new Coord(width, height, x, y, z);
        if (cacheMandelbrot.get(coord.toString()) != null){
            image = cacheMandelbrot.get(coord.toString());
        }else{
            System.out.println(coord.toString());

            ExecutorService threadPool = new ThreadPool(16);
            try {
                image = mandelbrot.createFractal(width,height,x,y,z,threadPool);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cacheMandelbrot.put(coord.toString(), image);
        }

        return image;
    }

    public BufferedImage getJuliaFractalImage(int width , int height, double x, double y, double z){

        BufferedImage image = null;

        Coord coord = new Coord(width, height, x, y, z);
        if (cacheJulia.get(coord.toString()) != null){
            image = cacheJulia.get(coord.toString());
        }else{
            System.out.println(coord.toString());

            ExecutorService threadPool = new ThreadPool(16);
            try {
                image = julia.createFractal(width,height,x,y,z,threadPool);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cacheJulia.put(coord.toString(), image);
        }

        return image;
    }
}
