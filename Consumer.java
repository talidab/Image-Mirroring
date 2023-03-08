package packWork;

import java.io.IOException;
import java.io.PipedInputStream;
import java.awt.image.BufferedImage;

public class Consumer extends Thread implements Runnable{

    private PipedInputStream in;
    private String path;
    private Write write;
    private ImageMirror mirror;
    private Object lock;

    public Consumer(PipedInputStream in, String path, Write write, ImageMirror mirror) {
        this.in = in;
        this.path = path;
        this.write = write;
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

            // citeste si proceseaza fiecare parte din pipe
            for (int i = 0; i < 4; i++) {
                byte[] buf = new byte[width * partHeight * 3];
                int len = 0;

                // sincronizarea accesului la pipe
                synchronized (lock) {
                    lock.wait();
                    len = in.read(buf);
                }

                // proceseaza o parte din imagine
                for (int y = i * partHeight, pos = 0; y < (i + 1) * partHeight; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = (buf[pos] << 16) | (buf[pos + 1] << 8) | buf[pos + 2];
                        mirror.getBufferedImage().setRGB(x, y, pixel);
                        pos = pos + 3;
                    }
                }

                // oglindeste imaginea
                mirror.Mirror();

                // scrie imaginea oglindita in fisier
                write.writeImage(path, mirror.getMirroredImage());

                // afiseaza output la consola
                System.out.println("Procesare parte " + (i + 1) + " terminata.");

                // face un sleep pentru a evidentia etapele comunicarii
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}
