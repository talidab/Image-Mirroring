package packWork;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.awt.image.BufferedImage;

public class Producer extends Thread implements Runnable {

    private PipedOutputStream out;
    private String path;
    private Read read;
    private ImageMirror mirror;
    private Object lock;

    public Producer(PipedOutputStream out, String path, Read read, ImageMirror mirror) {
        this.out = out;
        this.path = path;
        this.read = read;
        this.mirror = mirror;
        this.lock = new Object();
    }

    public void run() {
        try {
            // obtine dimensiunile imaginii
            int width = mirror.getBufferedImage().getWidth();
            int height = mirror.getBufferedImage().getHeight();

            // determina dimensiunea fiecarei parti
            int partHeight = height / 4;

            // citeste si scrie fiecare parte in pipe
            for (int i = 0; i < 4; i++) {
                BufferedImage imagePart = mirror.getBufferedImage().getSubimage(0, i * partHeight, width, partHeight);

                // converteste imaginea in matrice de bytes
                byte[] buf = new byte[width * partHeight * 3];
                int pos = 0;
                for (int y = 0; y < partHeight; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = imagePart.getRGB(x, y);
                        buf[pos] = (byte) (pixel >> 16);
                        buf[pos + 1] = (byte) (pixel >> 8);
                        buf[pos + 2] = (byte) pixel;
                        pos = pos + 3;
                    }
                }
                // sincronizarea accesului la pipe
                synchronized (lock) {
                    out.write(buf);
                    lock.notify();
                    System.out.println("A fost procesata partea: " + (i+1));
                }
                // intra in Not Runnable
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
