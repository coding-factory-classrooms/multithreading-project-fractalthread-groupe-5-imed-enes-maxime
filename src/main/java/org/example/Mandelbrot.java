package org.example;

import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Mandelbrot {

    private final int width;
    private final int height;
    private final int maxIteration;

    private final int[] colors;

    public Mandelbrot(int width, int height, int maxIteration){
        this.width = width;
        this.height = height;
        this.maxIteration = maxIteration;
        this.colors = getColors(maxIteration);
    }

    public static int[] getColors(int max){
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
        return colors;
    }

    public BufferedImage createFractal(double xPos, double yPos, double zoom){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
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
                if (iteration < maxIteration) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, 0);
            }
        }

        return image;
    }
}