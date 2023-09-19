package generator.minecraftskin.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import generator.minecraftskin.exceptions.UnhandledSkinSizeException;
import generator.minecraftskin.skinpart.MinecraftSkinPart;
import generator.minecraftskin.skinpart.SkinPart;

public class SkinPartsDB {
	
	private static final File SKIN_PARTS_DB = new File("src\\generator\\database\\skinpartsdb");
	private static Pattern pattern = Pattern.compile("[a-zA-Z]{1,20}");
	
	private final File fileDB;
	
	private HashMap<SkinPart, File> dirsOfDB = new HashMap<>();
		
	public SkinPartsDB(String nameOfNewDB) {
		if (!nameOfNewDB.matches(pattern.pattern())) {
			throw new IllegalArgumentException("wrong name of db");
		}
		
		this.fileDB = new File(SKIN_PARTS_DB, nameOfNewDB);
		
		var hands = new File(fileDB, "hands");
		var legs = new File(fileDB, "legs");
		
		dirsOfDB.put(SkinPart.HEAD, new File(fileDB, "heads"));
		dirsOfDB.put(SkinPart.BODY, new File(fileDB, "bodies"));
		dirsOfDB.put(SkinPart.HAND1, hands);
		dirsOfDB.put(SkinPart.HAND2, hands);
		dirsOfDB.put(SkinPart.LEG1, legs);
		dirsOfDB.put(SkinPart.LEG2, legs);
	}
	
	public void makeDB() {
		dirsOfDB.values().forEach(File::mkdirs);
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
		var dirToSave = dirsOfDB.get(skinPart.getSkinPartType());
		try {
			var newFile = new File(dirToSave, dirToSave.listFiles().length + ".png");
			
			newFile.createNewFile();
			ImageIO.write(skinPart.getImage(), "png", newFile);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<MinecraftSkinPart> getAllSkinParts() {
		List<MinecraftSkinPart> list = new ArrayList<>();
		
		try {
			for (Entry<SkinPart, File> entry : dirsOfDB.entrySet()) {
				for (File img : entry.getValue().listFiles()) {
					list.add(MinecraftSkinPart.createSkinPart(ImageIO.read(img),
							entry.getKey()));
				}
			}
			return list;
			
		} catch (UnhandledSkinSizeException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public static SkinPartsDB combineIntoOne(String nameOfNewDB, SkinPartsDB... dbs) {
		for (File db : SKIN_PARTS_DB.listFiles()) {
			if (db.getName().equals(nameOfNewDB)) {
				throw new IllegalArgumentException("this name of database already exists");
			}
		}
		
		var unitDB = new SkinPartsDB(nameOfNewDB);
		
		unitDB.makeDB();
		Arrays.asList(dbs).forEach(db -> unitDB.saveSkinParts(db.getAllSkinParts()));
		return unitDB;
	}
	
	public static void main(String...args) throws UnhandledSkinSizeException, IOException {
		SkinPartsDB skinPartsDB = new SkinPartsDB("superDataBase");
		var file = new File("C:\\projects\\test_package\\deadpool.png");
		var skinPart = MinecraftSkinPart.createSkinPart(ImageIO.read(file), SkinPart.HEAD);
		
		skinPartsDB.delete();
		skinPartsDB.makeDB();
		skinPartsDB.saveSkinPart(skinPart);
		skinPartsDB.saveSkinParts(Arrays.asList(skinPart, skinPart, skinPart));
		
		var skinPartsDB2 = SkinPartsDB.combineIntoOne("unitDB", skinPartsDB, skinPartsDB);
	}
			
}