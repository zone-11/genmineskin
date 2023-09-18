package generator.minecraftskin.skinpart;

import java.awt.image.BufferedImage;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;

public class MinecraftSkinPart {
	private BufferedImage skinPartImage;
	private SkinPart skinPart;
	
	private MinecraftSkinPart(BufferedImage skinPartImage, SkinPart skinPart) {
		this.skinPartImage = skinPartImage;
		this.skinPart = skinPart;
	}
	
	public static MinecraftSkinPart createSkinPart(BufferedImage bImage, SkinPart skinPart) 
	throws UnhandledSkinSizeException {
		if(bImage.getHeight() == 64 && bImage.getWidth() == 64) {
			int[] coords = skinPart.getCoords();
			return new MinecraftSkinPart(bImage.getSubimage(coords[0], coords[1], coords[2], coords[3]), skinPart);
		} else if(bImage.getWidth() == skinPart.getCoords()[2] &&
				bImage.getHeight() == skinPart.getCoords()[3]) {
			return new MinecraftSkinPart(bImage, skinPart);
		}
		throw new UnhandledSkinSizeException("Wrong png size. Provided size: " +
							bImage.getWidth() + "x" + bImage.getHeight() +
							", needed: 64x64");
	}
	public BufferedImage getImage() { return skinPartImage; }
	public void setImage(BufferedImage bImage) {
		skinPartImage = bImage;
	}
	public SkinPart getSkinPartType() { return skinPart; }
}
