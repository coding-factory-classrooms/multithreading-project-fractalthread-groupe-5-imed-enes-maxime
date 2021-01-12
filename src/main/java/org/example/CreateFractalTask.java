package org.example;
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

        int widthHalf = width/2;
        int heightHalf = height/2;

        for (int row = startY; row < endY; row++) {
            for (int col = startX; col < endX; col++) {
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
                if (iteration < maxIteration) fractal.append(col, row, colors[iteration]);
                else fractal.append(col, row, 0);
            }
        }
    }

}
