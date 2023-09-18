package generator.appstart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;
import generator.minecraftskin.skingenerator.MinecraftSkinGenerator;

public class Main {

	public static void main(String[] args) throws IOException {
		File file = new File("C:\\projects\\test_package\\skin_copy.png");
		File file2 = new File("C:\\projects\\test_package\\Mag.png");
		
		BufferedImage bufImage = ImageIO.read(file2);
		MinecraftSkinGenerator.clearBase();
		try {
			MinecraftSkinGenerator.parseSkin(bufImage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnhandledSkinSizeException e) {
			e.printStackTrace();
		}
	}
}



