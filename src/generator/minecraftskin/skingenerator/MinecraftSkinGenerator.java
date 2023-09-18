package generator.minecraftskin.skingenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;
import generator.minecraftskin.skin.MinecraftSkin;
import generator.minecraftskin.skinpart.MinecraftSkinPart;
import generator.minecraftskin.skinpart.SkinPart;

public class MinecraftSkinGenerator {
	private static File HEADS = new File("src\\generator\\skinbase\\heads");
	private static File BODIES = new File("src\\generator\\skinbase\\bodies");
	private static File HANDS = new File("src\\generator\\skinbase\\hands");
	private static File LEGS = new File("src\\generator\\skinbase\\legs");
	
	private static Random random = new Random();
	
	public static Optional<MinecraftSkin> generateSkin() throws IOException {
		
		MinecraftSkin newSkin = null;
		
		try {
			newSkin = 
			MinecraftSkin.createSkin(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

			BufferedImage[] skinParts = { 
					ImageIO.read(HEADS.listFiles()[random.nextInt(0, HEADS.listFiles().length)]),
					ImageIO.read(BODIES.listFiles()[random.nextInt(0, BODIES.listFiles().length)]),
					ImageIO.read(HANDS.listFiles()[random.nextInt(0, HANDS.listFiles().length)]),
					ImageIO.read(HANDS.listFiles()[random.nextInt(0, HANDS.listFiles().length)]),
					ImageIO.read(LEGS.listFiles()[random.nextInt(0, LEGS.listFiles().length)]),
					ImageIO.read(LEGS.listFiles()[random.nextInt(0, LEGS.listFiles().length)]) };

			for (int i = 0; i < skinParts.length; i++) {
				newSkin.setSkinPart(skinParts[i], SkinPart.values()[i]);
			}

		} catch (UnhandledSkinSizeException e) {
			System.err.println("Error via create skin");
		}
		
		return Optional.ofNullable(newSkin);
	}
	
	public static void parseSkin(BufferedImage image) throws IOException,
	UnhandledSkinSizeException {
		MinecraftSkin skin = MinecraftSkin.createSkin(image);
		for (MinecraftSkinPart skinPart : skin.getSkinParts()) {
			savePart(skinPart);
		}
	}
	
	public static void clearBase() {
		clearBase(file -> true);
	}
	
	public static void clearBase(Predicate<File> pred) {
		List<File> arr = Arrays.asList(HEADS, BODIES, HANDS, LEGS);
		
		arr.forEach(dir -> Arrays.asList(dir.listFiles())
				.forEach(pred::test));
	}
	
	private static void savePart(MinecraftSkinPart part) throws IOException {
		
		BufferedImage img = part.getImage();
		
		switch(part.getSkinPartType()) {
		case HEAD:
			save(HEADS.listFiles().length, img, HEADS);
			break;
		case BODY:
			save(BODIES.listFiles().length, img, BODIES);
			break;
		case HAND1: 
			save(HANDS.listFiles().length, img, HANDS);
			break;
		case HAND2: 
			save(HANDS.listFiles().length, img, HANDS);
			break;
		case LEG1:
			save(LEGS.listFiles().length, img, LEGS);
			break;
		case LEG2:
			save(LEGS.listFiles().length, img, LEGS);
			break;
		}
	}
	
	private static void save(int num, BufferedImage bImage, File dir) throws IOException{
		String path = dir.getAbsolutePath() + "\\" + num + ".png";
		File file = new File(path);
		
		file.createNewFile();
		ImageIO.write(bImage, "png", file);
		System.out.println("Saving...");
	}
}
