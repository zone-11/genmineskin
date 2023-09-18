package generator.minecraftskin.skin;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;
import generator.minecraftskin.skinpart.MinecraftSkinPart;
import generator.minecraftskin.skinpart.SkinPart;





public class MinecraftSkin {
	
	private MinecraftSkinPart[] skinParts = new MinecraftSkinPart[6];
	
	private MinecraftSkin(MinecraftSkinPart[] parts) {
		for(int i = 0; i < skinParts.length; i++) {
			skinParts[i] = parts[i];
		}
	}
	
	public BufferedImage getSkinImage() {
		BufferedImage skin = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = skin.getGraphics();
		for(MinecraftSkinPart part : skinParts) {
			int[] coords = part.getSkinPartType().getCoords();
			graphics.drawImage(part.getImage(), coords[0], coords[1], null);
		}
		return skin;
	}
	
	public MinecraftSkinPart[] getSkinParts() { return skinParts; }
	
	public void setSkinPart(BufferedImage bImage, SkinPart part)
	throws UnhandledSkinSizeException {
		switch (part) {
		case HEAD -> skinParts[0] = MinecraftSkinPart.createSkinPart(bImage, part);
		case BODY -> skinParts[1] = MinecraftSkinPart.createSkinPart(bImage, part);
		case HAND1 -> skinParts[2] = MinecraftSkinPart.createSkinPart(bImage, part);
		case HAND2 -> skinParts[3] = MinecraftSkinPart.createSkinPart(bImage, part);
		case LEG1 -> skinParts[4] = MinecraftSkinPart.createSkinPart(bImage, part);
		case LEG2 -> skinParts[5] = MinecraftSkinPart.createSkinPart(bImage, part);
		
		
		
		default ->
		throw new IllegalArgumentException("Unexpected value: " + part);
		}
	}
	
	public void setSkinParts(BufferedImage[] bImageList) 
	throws UnhandledSkinSizeException {
		if(bImageList.length < 6) return;
		
	}
	
	public static MinecraftSkin createSkin(BufferedImage bImage)
	throws UnhandledSkinSizeException {
		MinecraftSkinPart[] parts = new MinecraftSkinPart[SkinPart.values().length];
		for(int i = 0; i < parts.length; i++) {
			parts[i] = MinecraftSkinPart.createSkinPart(bImage, SkinPart.values()[i]);
		}
		return new MinecraftSkin(parts);
	}
	
}
