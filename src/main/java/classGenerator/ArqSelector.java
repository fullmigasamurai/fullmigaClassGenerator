/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package classGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import classGenerator.FuFile;
import java.util.HashMap;


/**
 *
 * @author Toph
 */
public class ArqSelector {
	
	// private FuFile f;
	private HashMap<String, String> changes;

	public ArqSelector (){
		changes = new HashMap<String, String>();
		changes.put("ClasseMaiuscula", "NovoMaiusculo");
		changes.put("classeMinuscula", "novoMinusculo");
		changes.put("classe_underline", "novo_underline");
		changes.put("classe-traco", "novo-traco");
		
	}

	public ArqSelector (HashMap<String, String> map){
		changes = map;
		
		
	}


	public void listFolder (File dir) {
		File[] subDir = dir.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		// System.out.println("\n\nDirectory of " + dir.getAbsolutePath());
		
		for (File folder: subDir) {
			listFolder(folder);
		}
		
		for(HashMap.Entry<String, String> entry : changes.entrySet()) {
			String oldName = entry.getKey();
			String newName = entry.getValue();
			if (dir.getName().contains(oldName)){
				System.out.println("foldername: " + dir.getName());
				System.out.println("FuFile.ChangeFoldername: " + FuFile.changeName(dir, oldName, newName) + "\n");
				dir = FuFile.replaceFile(dir, oldName, newName);
			}
		}
		
		listFile(dir);

	}

	private void listFile (File dir){
		File[] files = dir.listFiles();
		for (File file : files){
			if(file.getName().contains(".java")) {
				for(HashMap.Entry<String, String> entry : changes.entrySet()) {
					String oldName = entry.getKey();
					String newName = entry.getValue();
					if (file.getName().contains(oldName)){
						System.out.println("filename: " + file.getName());
						System.out.println("FuFile.changeClassName " + FuFile.changeName(file, oldName, newName));
						file = FuFile.replaceFile(file, oldName, newName);
					}
					
				}
				buffRead(file);
			}
		}
	}

	public void buffRead (File dir) {
		
		
		String nextLine = null;
		ArrayList<String> lines = new ArrayList<String>();
		System.out.println("Readind: " + dir.getPath());                     
		try (BufferedReader br = new BufferedReader(new FileReader(dir))) {   
			try {
				while ((nextLine = br.readLine()) != null) {
					for(HashMap.Entry<String, String> entry : changes.entrySet()) {
						String oldName = entry.getKey();
						String newName = entry.getValue();
						nextLine = nextLine.replaceAll(oldName, newName);
					}
					lines.add(nextLine);
				}   
			}            
			catch(IOException e) {
				System.out.print("Error reading file. Try another).\n: ");  
			}            
		}   
		catch(FileNotFoundException e) {
			System.out.print("File doesn't exist. Try again).\n: ");
		}      
		catch(IOException e) {
			System.out.println("Error closing the file. Program shutting down.");
		}   
		

		buffWrite(dir, lines);
		
	}

	public void buffWrite (File file , ArrayList<String> lines) {

		System.out.println("Writing: " + file.getPath() + "\n____");
		try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file))) {

			for (String s : lines) {
				buffWriter.write(s);
				buffWriter.newLine();
			}      
		} catch (IOException io) {

		}
	}

	public void delete (File dir){

		File myObj = new File("filename.txt"); 
		if (myObj.delete()) { 
		System.out.println("Deleted the file: " + myObj.getName());
		} else {
		System.out.println("Failed to delete the file.");
		} 
	} 


	
}
