package org.example;

import java.awt.image.BufferedImage;

public class Fractal {
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
