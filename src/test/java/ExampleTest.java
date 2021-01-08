import org.example.LRUCache;
import org.example.Mandelbrot;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExampleTest {
    @Test
    public void meanTimeFractal() {
        Mandelbrot mandelbrot = new Mandelbrot(5000);

        int i = 0;
        long meanTime = 0;
        while(i < 10){
            long start = System.currentTimeMillis();
            BufferedImage image = mandelbrot.createFractal(1000,1000,0,0,3);
            i++;
            long elapsed = System.currentTimeMillis() - start;
            meanTime += elapsed/i;
        }
        System.out.println("Meantime for 10 fractal : " + meanTime + " ms");
    }


    @Test
    public void LRUCachePutWithIntegers_Success() {
        LRUCache<Integer, Integer> cache = new LRUCache<>();
        cache.put(0, 1);
        Assert.assertEquals((int) cache.get(0), 1);
    }

    @Test
    public void LRUCacheEmptyGetReturnNull() {
        LRUCache<Integer, Integer> cache = new LRUCache<>();
        Assert.assertEquals(cache.get(0), null);
    }
}
