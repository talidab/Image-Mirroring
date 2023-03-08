package packWork;

// clasa Read este o clasa abstracta, nivelul de mostenire este 1
import java.awt.image.BufferedImage;

public abstract class Read {

	public void ReadMethod() {
		System.out.println("Clasa abstracta Read");
	}

	abstract public BufferedImage readImage(String path);
}
