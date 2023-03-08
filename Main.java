package packTest;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.awt.image.BufferedImage;

import packWork.Consumer;
import packWork.ImageMirror;
import packWork.ProcessImage;
import packWork.Producer;
import packWork.Read;
import packWork.Write;

public class Main {

	// bloc static de initializare
	static {
		System.out.println("Incepe procesul de prelucrare a imaginii!");
	}

	public Main(){
		//System.out.println("Constructor fara parametru");
	}
	
	// utilizare varargs
	public double Count(double... args) {
		double timp = 0;
		if(args.length > 0){
		if (args[1] > 0)
			timp = args[1] - args[0];
		else
			timp = args[0] - args[1];
		}
		return timp;
		
	}

	public static void main(String[] args) throws IOException {		
		
		String caleintrare, caleiesire;
		
		if (args.length > 0){
			caleintrare = args[0];
			caleiesire = args[1];
		}
		else {
		Scanner input = new Scanner(System.in);
		System.out.println("Introduceti calea unde se gaseste imaginea de intrare:");
		caleintrare = input.nextLine();
		System.out.println("Introduceti calea unde doriti sa se afiseze imaginea de iesire:");
		caleiesire = input.nextLine();
		}
		Read read = new ProcessImage();
		Write write = new ProcessImage();
		BufferedImage image = read.readImage(caleintrare);
		ImageMirror mirror = new ImageMirror(image);
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in = new PipedInputStream();
		in.connect(out);
		
		// creeaza thread-urile
		Producer p1 = new Producer(out, caleintrare, read, mirror);
		Consumer c1 = new Consumer(in, caleiesire, write, mirror);
		
		//porneste thread-urile
		p1.start();
		c1.start();

		Main process = new Main();
		double start = System.currentTimeMillis();
		mirror.Mirror();
		double end = System.currentTimeMillis();
		double timp2 = process.Count(start, end);
		System.out.println("Algoritmul de transformare a durat: " + timp2 + "ms");
		image = mirror.getMirroredImage();

		// calculare timp de scriere
		ProcessImage proc = new ProcessImage();
		Main scriere = new Main();
		double start3 = System.currentTimeMillis();
		proc.writeImage(caleiesire, image);
		double end3 = System.currentTimeMillis();
		double timp3 = scriere.Count(start3, end3);
		System.out.println("Scrierea imaginii a durat: " + timp3 + "ms");
		
	}

}
