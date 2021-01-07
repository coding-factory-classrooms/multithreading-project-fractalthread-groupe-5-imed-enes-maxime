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
        Mandelbrot mandelbrot = new Mandelbrot(1000,1000,1000);

        int i = 0;
        long meanTime = 0;
        while(i < 10){
            long start = System.currentTimeMillis();
            BufferedImage image = mandelbrot.createFractal(0.35,0.095,0.009);

            try {
                ImageIO.write(image, "png", new File("mandelbrot.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
            long elapsed = System.currentTimeMillis() - start;
            meanTime += elapsed/i;
        }
        System.out.println("Meantime for 10 fractal : " + meanTime + " ms");
    }
}
