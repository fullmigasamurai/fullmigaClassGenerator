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
import java.util.Scanner;


/**
 *
 * @author Toph
 */
public class ArqSelector {
	

	ArqSelector (){
		
		
	}


	public void listFolder (File dir) {
		File[] subDir = dir.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});

		System.out.println("\n\n Directory of " + dir.getAbsolutePath());
		if (dir.getName().contains("ClasseMaiuscula")){
			File replace = dir;
			replace = new File(dir.getPath().replace("ClasseMaiuscula", "TestandoAqui"));
			System.out.println("Renaming: " + dir.getPath() +" to " + replace.getPath() + "\n____");
			// Boolean b = dir.renameTo(replace);
			// System.out.println("b: " + b + "\n____");
		}
		listFile(dir);
		// for (File folder: subDir) {
		// 	listFolder(folder);
		// }

	}
	private void listFile (File dir){
		File[] files = dir.listFiles();
		for (File file : files){
			if(file.getName().contains(".java")) {
				System.out.println(file.getName());
				buffRead(file);
			}
		}
	}

	public void buffRead (File dir) {
		
		
		String nextLine = null;
		ArrayList<String> lines = new ArrayList<String>();
		System.out.println("dir.getName(): " + dir.getPath() + "\n____");                     
		try (BufferedReader br = new BufferedReader(new FileReader(dir))) {   
			try {
				while ((nextLine = br.readLine()) != null) {
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
		if (dir.getName().contains("ClasseMaiuscula")){
			File replace = dir;
			// String path = ;
			// path = ;
			replace = new File(dir.getPath().replace("ClasseMaiuscula", "TestandoAqui"));
			System.out.println("Renaming: " + dir.getPath() +" to " + replace.getPath() + "\n____");
			// Boolean b = dir.renameTo(replace);
			// System.out.println("b: " + b + "\n____");
		}

		// buffWrite(dir, lines);
		
	}

	public void buffWrite (File file , ArrayList<String> lines) {

		System.out.println("file.getPath(): " + file.getPath() + "\n____");
		try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file))) {

			for (String s : lines) {
				buffWriter.write(s);
				buffWriter.newLine();
			}      
		} catch (IOException io) {

		}
	}

	public void delte (File dir){

		File myObj = new File("filename.txt"); 
		if (myObj.delete()) { 
		System.out.println("Deleted the file: " + myObj.getName());
		} else {
		System.out.println("Failed to delete the file.");
		} 
	} 


	
}
