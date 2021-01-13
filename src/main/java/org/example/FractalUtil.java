package org.example;

import java.awt.*;

public class FractalUtil {
    public static int[] getColors(int max){
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
}
