package pl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiles {
	static int tilesize = 128; //size of each side of tile
	static int tpm = 256; // number of tiles per map
	BufferedImage fullimage;
	public BufferedImage[] tilearray = new BufferedImage[tpm];
	
	public Tiles(File image) throws IOException {
		fullimage = ImageIO.read(image);
		for(int i = 0; i <= tpm; i++) {
			tilearray[i] = fullimage.getSubimage((tpm*i),(tpm*i),(tpm*i),(tpm*i));
		}
	}
}
