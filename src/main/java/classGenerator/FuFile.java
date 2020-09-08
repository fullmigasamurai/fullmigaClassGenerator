package classGenerator;

import java.io.File;

public class FuFile {

	public static File changeName (File file, String newName) {
			file = new File(file.getParent()+"/"+newName);

		return file;
	}

	public static File replaceFile (File file, String newName){
			File replace = new File(file.getParent()+"/"+newName);
			Boolean b = file.renameTo(replace);

			return b ? replace : null;

	}
	public static File changeName (File file, String oldName, String newName) {
		if (file.getName().contains(oldName)){
			file = new File(file.getParent()+"/"+file.getName().replace(oldName, newName));
		}
		return file;
	}

	public static File replaceFile (File file, String oldName, String newName){
		if (file.getName().contains(oldName)){
			File replace = new File(file.getParent()+"/"+file.getName().replace(oldName, newName));
			Boolean b = file.renameTo(replace);

			return b ? replace : null;

		} else {
			return file;
		}

	}
	
}
