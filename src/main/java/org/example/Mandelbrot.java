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

    public class CreateFractalTask implements Runnable{

        private final int width;
        private final int height;
        private final int[] colors;
        private final int maxIteration;
        private final int startX;
        private final int startY;
        private final int endX;
        private final int endY;
        private final double xPos;
        private final double yPos;
        private final double zoom;
        private final Fractal fractal;

        public CreateFractalTask(int width, int height, int[] colors, int maxIteration, int startX, int startY, int endX, int endY, double xPos, double yPos, double zoom, Fractal fractal) {
            this.width = width;
            this.height = height;
            this.colors = colors;
            this.maxIteration = maxIteration;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.xPos = xPos;
            this.yPos = yPos;
            this.zoom = zoom;
            this.fractal = fractal;
        }


        @Override
        public void run() {

            for (int row = startY; row < endY; row++) {
                for (int col = startX; col < endX; col++) {
                    double c_re = ((col - width/2)*zoom/width) + xPos;
                    double c_im = ((row - height/2)*zoom/width) + yPos;
                    double x = 0, y = 0;
                    int iteration = 0;
                    while (x*x+y*y < 4 && iteration < maxIteration) {
                        double x_new = x*x-y*y+c_re;
                        y = 2*x*y+c_im;
                        x = x_new;
                        iteration++;
                    }
                    if (iteration < maxIteration) fractal.append(col, row, colors[iteration]);
                    else fractal.append(col, row, 0);
                }
            }
        }

    }

    public class Fractal{

        private final BufferedImage image;

        public Fractal(int width,int height) {
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        public void append(int x,int y, int color){
            image.setRGB(x,y,color);
        }

        public BufferedImage getImage(){
            return image;
        }
    }
}