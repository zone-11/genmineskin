package generator.minecraftskin.database;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;
import generator.minecraftskin.skinpart.MinecraftSkinPart;
import generator.minecraftskin.skinpart.SkinPart;

public class SkinPartsDB {
	
	private static final File SKIN_PARTS_DB = new File("src\\generator\\database\\skinpartsdb");
	private static Pattern pattern = Pattern.compile("[a-zA-Z]{1,20}");
	
	private final File fileDB;
	
	private final File headsFileDB;
	private final File bodiesFileDB;
	private final File handsFileDB;
	private final File legsFileDB;
	
	public SkinPartsDB(String nameOfNewDB) {
		if (!nameOfNewDB.matches(pattern.pattern())) {
			throw new IllegalArgumentException("wrong name of db");
		}
		
		this.fileDB = new File(SKIN_PARTS_DB, nameOfNewDB);
		
		this.headsFileDB = new File(fileDB, "heads");
		this.bodiesFileDB = new File(fileDB, "bodies");
		this.handsFileDB = new File(fileDB, "hands");
		this.legsFileDB = new File(fileDB, "legs");
	}
	
	public void makeDB() {
		var files = Arrays.asList(headsFileDB, bodiesFileDB, handsFileDB,
								legsFileDB);
		
		files.forEach(File::mkdirs);
	}
	
	public void delete() {
		for (File dir : fileDB.listFiles()) {
			for (File image : dir.listFiles()) {
				image.delete();
			}
			
			dir.delete();
		}
		
		fileDB.delete();
	}
	
	public boolean exists() {
		return fileDB.exists();
	}
	
	public void saveSkinParts(List<MinecraftSkinPart> parts) {
		parts.forEach(this::saveSkinPart);
	}
	
	public void saveSkinPart(MinecraftSkinPart skinPart) {
		try {
			var fileToSave = new File(
				switch (skinPart.getSkinPartType()) {
				case HEAD -> headsFileDB.getAbsolutePath() + "\\"
				+ headsFileDB.listFiles().length;
				case BODY -> bodiesFileDB.getAbsolutePath() + "\\" 
				+ bodiesFileDB.listFiles().length;
				case HAND1 -> handsFileDB.getAbsolutePath() + "\\" 
				+ handsFileDB.listFiles().length;
				case HAND2 -> handsFileDB.getAbsolutePath() + "\\" 
				+ handsFileDB.listFiles().length;
				case LEG1 -> legsFileDB.getAbsolutePath() + "\\" 
				+ legsFileDB.listFiles().length;
				case LEG2 -> legsFileDB.getAbsolutePath() + "\\" 
				+ legsFileDB.listFiles().length;
				
				default -> throw new IllegalArgumentException("uncorrect MinecraftSkinPart type");
				}
				+ ".png"
			);
			
			fileToSave.createNewFile();
			ImageIO.write(skinPart.getImage(), "png", fileToSave);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public static void main(String...args) throws UnhandledSkinSizeException, IOException {
		SkinPartsDB skinPartsDB = new SkinPartsDB("superDataBase");
		var file = new File("C:\\projects\\test_package\\deadpool.png");
		var skinPart = MinecraftSkinPart.createSkinPart(ImageIO.read(file), SkinPart.HEAD);
		
		skinPartsDB.delete();
		skinPartsDB.makeDB();
		skinPartsDB.saveSkinPart(skinPart);
		skinPartsDB.saveSkinParts(Arrays.asList(skinPart, skinPart, skinPart));
	}
			
}