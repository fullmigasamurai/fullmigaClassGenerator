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
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Toph
 */
public class ArqSelector {
    
	private static File arq;

	ArqSelector (String path){
		
		arq = new File(path);

		
		
	}

	public static void listPath (File dir) {

		try {
			System.out.println("paths");
			// Files.list(Paths.get(dir.getAbsolutePath())).forEach(System.out::println);

			Path p = Paths.get(dir.getAbsolutePath());
			Stream<Path> f = Files.list(p);
			// f.forEach(System.out::println);
			f.forEach(s -> System.out.println(s.toAbsolutePath()));
			f.forEach(s -> listPath((s.toAbsolutePath().toFile())));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listFolder (File dir) {
		System.out.print("paths");
		File[] subDir = dir.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});

		System.out.println("\n Directory of " + dir.getAbsolutePath());
		listFile(dir);

		for (File folder: subDir) {
			listFolder(folder);
		}
	}
	private void listFile (File dir){
		File[] files = dir.listFiles();
		for (File file : files){
			System.out.println(file.getName());
		}
	}

	public void write () {
		try {
			FileWriter myWriter = new FileWriter("filename.txt");
			myWriter.write("Files in Java might be tricky, but it is fun enough!");
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
	}

	public void read () {
		try {
			File myObj = new File("filename.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
			  String data = myReader.nextLine();
			  System.out.println(data);
			}
			myReader.close();
		  } catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
	}

	public void buffRead () {
		

		System.out.println("arq.: " + arq.getAbsolutePath() + "\n____");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(arq));
			try {
				String nextLine;
				while ((nextLine = reader.readLine()) != null) {
					System.out.println("nextLine: " + nextLine + "\n____");
				}   
			}            
			catch(IOException e) {
				System.out.print("Error reading file. Try another ('q' to quit, 'a' for available).\n: ");  
			}            

		} catch (IOException io){
			System.out.println("cach ioex");
		}
		
	}

	public void buffWrite (String path , File file){

		// BufferedWriter writer 
	}


    
}
