package org.example;

public class Coord {
    private final int width;
    private final int height;
    private final double x;
    private final double y;
    private final double z;

    public Coord(int width , int height, double x, double y, double z){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
