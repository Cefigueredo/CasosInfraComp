import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.crypto.SecretKey;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//Pide cantidad de clientes y si usa cifrado sim�trico o asim�trico
		Scanner sc=new Scanner(System.in);  
		
		
		System.out.println("Ingrese la cantidad de clientes: ");
		int cantidadClientes = sc.nextInt();
		System.out.println("Usted eligi� "+cantidadClientes+" clientes.");
		
		System.out.println("Ingrese 1 si quiere usar cifrado sim�trico o 2 si quiere usar cifrado asim�trico: ");
		int nTipoCifrado = sc.nextInt();
		
		
		while(nTipoCifrado!=1 && nTipoCifrado!=2) {
			System.out.println("Error, digite nuevamente: ");
			nTipoCifrado = sc.nextInt();
		}
		
		if(nTipoCifrado==1) {
			System.out.println("Usted eligi� cifrado sim�trico");
		}
		else if(nTipoCifrado==2) {
			System.out.println("Usted eligi� cifrado asim�trico");
		}
		else {
			System.out.println("Error");
		}
		
		
		
		//Toma las llaves de los archivos
		FileInputStream archivo = new FileInputStream("llaveSimetrica");
		ObjectInputStream ois = new ObjectInputStream(archivo);
		SecretKey sk = (SecretKey) ois.readObject();
		ois.close();
		System.out.println("Algoritmo de la llave es: "+sk.getAlgorithm());
		
		
		//Ejecuta 
	}
}
