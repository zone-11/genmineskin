package generator.minecraftskin.database;

import java.io.File;
import java.util.regex.Pattern;

public class SkinPartsDB {
	
	private static final File SKIN_PARTS_DB = new File("src\\generator\\database\\skinpartsdb");
	private static Pattern pattern = Pattern.compile("[a-zA-Z]{1,20}");
	
	private final File pathDB;
	
	private File headsPathDB;
	private File bodiesPathDB;
	private File handsPathDB;
	private File legsPathDB;
	
	public SkinPartsDB(String nameOfNewDB) {
		
		if (!nameOfNewDB.matches(pattern.pattern())) {
			throw new IllegalArgumentException("wrong name of db");
		}
		
		this.pathDB = new File(SKIN_PARTS_DB, nameOfNewDB);
		
	}
	
	public void makeDB() {
		File[] files = { headsPathDB, bodiesPathDB, handsPathDB, legsPathDB };
		String[] dirs = { "heads", "bodies", "hands", "legs" };
		
		for (int i = 0; i < files.length ; i++) {
			
			files[i] = new File(pathDB, dirs[i]);
			
			files[i].mkdirs();
		}
	}


	public static void main(String...args) {
		SkinPartsDB skinPartsDB = new SkinPartsDB("superDataBase");
		skinPartsDB.makeDB();
	}
			
}
