package packWork;

//clasa Read este o clasa abstracta, nivelul de mostenire este 2
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class Write extends Read {

	public void WriteMethod() {
		System.out.println("Clasa abstracta Write");
	}

	// mai intai implementam metoda abstraca de la nivelul superior Read
	@Override
	public BufferedImage readImage(String path) {
		BufferedImage image = null;
		File f = null;
		try {
			f = new File(path); // calea de unde luam imaginea
			image = ImageIO.read(f);
			System.out.println("Citirea s-a efectuat cu succes!");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return image;
	}

	 public abstract void writeImage(String path, BufferedImage image);
}
