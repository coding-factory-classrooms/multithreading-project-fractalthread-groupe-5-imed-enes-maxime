package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Mandelbrot{
    private final int maxIteration;
    private final int[] colors;
    private final int nbThread;

    public Mandelbrot(int maxIteration, int nbThread){
        this.maxIteration = maxIteration;
        this.colors = getColors(maxIteration);

        this.nbThread = nbThread;
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

    public BufferedImage createFractal(int width , int height, double xPos, double yPos, double zoom, ExecutorService threadPool){

        long start = System.currentTimeMillis();
        Fractal fractal = new Fractal(width,height);


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
                MandelbrotFractalTask task = new MandelbrotFractalTask(width,height,colors,maxIteration,x,y,nextX,nextY,xPos,yPos,zoom, fractal);

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

    public static int getPixelIteration(int width, int height, double xPos, double yPos, double zoom, int col, int row,int maxIteration){

        int widthHalf = width/2;
        int heightHalf = height/2;

        double c_re = ((col - widthHalf)*zoom/width) + xPos;
        double c_im = ((row - heightHalf)*zoom/width) + yPos;
        double x = 0, y = 0;
        int iteration = 0;
        while (x*x+y*y < 4 && iteration < maxIteration) {
            double x_new = x*x-y*y+c_re;
            y = 2*x*y+c_im;
            x = x_new;
            iteration++;
        }
        return iteration;
    }

}