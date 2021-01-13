package org.example;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Julia extends FractalSystem{
    private static final double CX = -0.7;
    private static final double CY = 0.27015;

    public Julia(int maxIteration, int nbThread) {
        super(maxIteration, nbThread);
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
                JuliaFractalTask task = new JuliaFractalTask(width,height,colors,maxIteration,x,y,nextX,nextY,xPos,yPos,zoom, fractal);

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

        double zx = 1.5 * (row - width / 2) / (0.5 * zoom * width) + xPos;
        double zy = (col - height / 2) / (0.5 * zoom * height) + yPos;
        int iteration = 0;
        while (zx * zx + zy * zy < 4 && iteration < maxIteration) {
            double tmp = zx * zx - zy * zy + CX;
            zy = 2.0 * zx * zy + CY;
            zx = tmp;
            iteration++;
        }

        return iteration;
    }
}
