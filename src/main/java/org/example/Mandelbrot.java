package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Mandelbrot {
    private final int maxIteration;
    private final int[] colors;

    public Mandelbrot(int maxIteration){
        this.maxIteration = maxIteration;
        this.colors = getColors(maxIteration);
    }

    private static int[] getColors(int max){
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            if(i <= 50){
                colors[i] = new Color(255,255,255).getRGB();
            }else {
                colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
            }
        }
        return colors;
    }

    public BufferedImage createFractal(int width , int height, double xPos, double yPos, double zoom){
        long start = System.currentTimeMillis();
        Fractal fractal = new Fractal(width,height);
        int nbThreadMax = 16;
        int nbThread = nbThreadMax * 2;
        ExecutorService threadPool = Executors.newFixedThreadPool(nbThreadMax);

        int chunkSizeX = width / nbThread;
        int chunkSizeY = height / nbThread;

        int x = 0;
        while (x < width){

            int nextX = x + chunkSizeX;

            if(width - nextX < 0){
                nextX = x + width - x;
            }
            int y = 0;

            while (y < height){
                int nextY = y + chunkSizeY;
                if(height - nextY < 0){
                    nextY = y + height - y;
                }
                CreateFractalTask task = new CreateFractalTask(width,height,colors,maxIteration,x,y,nextX,nextY,xPos,yPos,zoom, fractal);

                threadPool.execute(task);

                y = nextY;
            }
            x = nextX;
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("time create : " + (System.currentTimeMillis() - start) + " ms");
        return fractal.getImage();
    }
}