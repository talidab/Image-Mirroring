package packWork;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
// nivelul de mostenire 3
public class ProcessImage extends Write {
	@Override
	public void writeImage(String path, BufferedImage image){
		try {
			File output = new File(path);
			ImageIO.write(image, "png", output);
			System.out.println("Scrierea s-a efectuat cu succes!");
		} catch (IOException e){
			System.out.println("Error: " + e);
		}
	}
	

	// ma folosesc de keyword super pentru a implementa metoda de la nivelul superior (parinte)
	@Override
	public BufferedImage readImage(String path){
		return super.readImage(path);
	}
	
}
