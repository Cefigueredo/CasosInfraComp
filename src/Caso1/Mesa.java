package Caso1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mesa {
	private static int numComensales;
	private static int numCubiertosT1;
	private static int numCubiertosT2;
	private static int numCubiertosSuciosT1;
	private static int numCubiertosSuciosT2;
	private static int numPlatos;
	private static int tamFregadero;
	public static void main(String[] args) throws Exception{
		Mesa instancia = new Mesa();
		try ( //Se recibe la lectura de consola
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
				) { 
			String line = br.readLine();
			
			
			String str = line.split("= ")[1];
			numComensales = Integer.parseInt(str);
			line = br.readLine();
			str = line.split("= ")[1];
			numCubiertosT1 = Integer.parseInt(str);
			line = br.readLine();
			str = line.split("= ")[1];
			numCubiertosT2 = Integer.parseInt(str);
			line = br.readLine();
			str = line.split("= ")[1];
			numPlatos = Integer.parseInt(str);
			line = br.readLine();
			str = line.split("= ")[1];
			tamFregadero = Integer.parseInt(str);
			Fregadero.setTamFregadero(tamFregadero);
			
			for(int i = 0; i < numComensales; i++) {
				new Comensal().start();
			}
			
			
			is.close();
			br.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//----------------------------
	//Getters and setters
	//-----------------------------
	public int getComensal() {
		return numComensales;
	}
	public void setComensal(int comensal) {
		this.numComensales= comensal;
	}
	public static int getNumCubiertosT1() {
		return numCubiertosT1;
	}
	public static void setNumCubiertosT1(int numCubiertosT1) {
		Mesa.numCubiertosT1 = numCubiertosT1;
	}
	public static int getNumCubiertosT2() {
		return numCubiertosT2;
	}
	public static void setNumCubiertosT2(int numCubiertosT2) {
		Mesa.numCubiertosT2 = numCubiertosT2;
	}
	public static int getNumCubiertosSuciosT1() {
		return numCubiertosSuciosT1;
	}
	public static void setNumCubiertosSuciosT1(int numCubiertosSuciosT1) {
		Mesa.numCubiertosSuciosT1 = numCubiertosSuciosT1;
	}
	public static int getNumCubiertosSuciosT2() {
		return numCubiertosSuciosT2;
	}
	public static void setNumCubiertosSuciosT2(int numCubiertosSuciosT2) {
		Mesa.numCubiertosSuciosT2 = numCubiertosSuciosT2;
	}
	public static int getNumPlatos() {
		return numPlatos;
	}
	public static void setNumPlatos(int numPlatos) {
		Mesa.numPlatos = numPlatos;
	}
	//------------------------------------------------

}
