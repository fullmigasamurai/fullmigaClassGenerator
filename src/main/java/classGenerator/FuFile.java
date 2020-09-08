package classGenerator;

import java.io.File;

public class FuFile {

	public File changeName (File file, String newName) {
		if (file.getName().contains(newName)){
			file = new File(file.getParent()+newName);
		}
		return file;
	}

	public File replaceFile (File file, String newName){
		if (file.getName().contains(newName)){
			File replace = new File(file.getParent()+newName);
			Boolean b = file.renameTo(replace);

			return b ? replace : null;

		} else {
			return file;
		}

	}
	
}
