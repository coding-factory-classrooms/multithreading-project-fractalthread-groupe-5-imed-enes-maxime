package org.example;
public class MandelbrotFractalTask implements Runnable{

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

    public MandelbrotFractalTask(int width, int height, int[] colors, int maxIteration, int startX, int startY, int endX, int endY, double xPos, double yPos, double zoom, Fractal fractal) {
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

                int iteration = Mandelbrot.getPixelIteration(width,height,xPos,yPos,zoom,col,row,maxIteration);

                if (iteration < maxIteration){
                    fractal.append(col, row, colors[iteration]);
                }
                else{
                    fractal.append(col, row, 0);
                }
            }
        }
    }

}
