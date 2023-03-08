package packWork;

import java.awt.image.BufferedImage;

public class ImageMirror implements Interface {

	private BufferedImage bufferedImage;
	private BufferedImage mirroredImage;
	private int height;
	private int width;

	//constructor
	public ImageMirror(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		// cream imaginea oglindita cu aceleasi dimensiuni ale lui bufferedImage
		this.mirroredImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
	}

	// constructor cu parametrii
	public ImageMirror(int height, int width) {
		super();
		this.height = height;
		this.width = width;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setMirroredImage(BufferedImage mirroredImage) {
		this.mirroredImage = mirroredImage;
	}

	public BufferedImage getMirroredImage() {
		return mirroredImage;
	}

	// algoritmul de oglindire a imaginii
	public void Mirror() {
		// luam dimensiunile imaginii date
		height = bufferedImage.getHeight();
		width = bufferedImage.getWidth();
		for (int j = 0; j < height; j++) {
			for (int i = 0, w = width - 1; i < width; i++, w--) {
				int pixel = bufferedImage.getRGB(i, j);
				mirroredImage.setRGB(w, j, pixel);
			}
		}
	}
}
