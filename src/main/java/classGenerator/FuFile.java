package classGenerator;

import java.io.File;

public class FuFile extends {

	private File file;

	public FuFile(String parent, String child) {
		this.file = new File(parent, child);
		
	}
	public FuFile(String pathname) {
		this.file = new File(pathname);
	}
	public FuFile(File parent, String child) {
		this.file = new File(parent, child);
	}

	public void setName (String name) {
		this.file = new File (file.getParent()+name);
	}

	public File changeName (String oldName, String newName) {
		if (this.file.getName().contains("oldName")){
			this.file = new File(this.file.getParent()+newName);
			return file;
		}
	}

	public boolean replaceFile (String oldName, String newName){
		if (this.file.getName().contains("oldName")){
			File replace = new File(this.file.getParent()+newName);
			Boolean b = this.file.renameTo(replace);
			return b;
		} else {
			return false;
		}

	}

	public File getFile ()
	{
		return this.file;
	}


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
}
