package Auxiliares;
import java.io.File;  // Import the File class
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.ObjectOutputStream;

public class GeneradorArchivosParaEnviar {
	public static void main(String[] args) {
		try {

			int i = 0;
			while(i< 10) {
				String filename = "0"+(i)+".txt";

				File myObj = new File("mensajes/"+filename);

				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
				} else {
					System.out.println("File already exists.");
				}
				
				FileWriter myWriter = new FileWriter("mensajes/"+filename);
				myWriter.write("----Archivo de nombre: "+filename);
			    myWriter.close();
				System.out.println("Archivo llenado.");
				
				++i;
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}