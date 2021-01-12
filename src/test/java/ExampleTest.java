import org.example.LRUCache;
import org.example.Mandelbrot;
import org.example.ThreadPool;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;

public class ExampleTest {
    @Test
    public void meanTimeFractal() {
        Mandelbrot mandelbrot = new Mandelbrot(5000, 32);

        int i = 0;
        long meanTime = 0;
        while(i < 10){
            long start = System.currentTimeMillis();
            ExecutorService newThreadPool = new ThreadPool(16);
            try {
                BufferedImage image = mandelbrot.createFractal(1000,1000,0,0,3, newThreadPool);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            long elapsed = System.currentTimeMillis() - start;
            meanTime += elapsed;
        }
        System.out.println("Meantime for 10 fractal : " + meanTime / 10 + " ms");
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
