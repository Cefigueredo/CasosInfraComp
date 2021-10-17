import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;

public class Mesa {
	//---------------------------------------------------------------------------------------
	// Atributos
	//---------------------------------------------------------------------------------------
	private static int numCubiertosT1;
	private static int numCubiertosT2;
	private static int numParCubiertosSucios=0;
	private static int numPlatos;
	private static int tamFregadero;
	private static int numComensales;
	private static int comensalesTerminaron=0;
	private static int mitadPlatos;
	private static ArrayList<Comensal> comensales = new ArrayList<Comensal>();
	private static Lavaplatos lp;
	
	//---------------------------------------------------------------------------------------
	// Métodos
	//---------------------------------------------------------------------------------------
	
	/**
	 * Método principal que lee el archivo de entrada e inicia los threads
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		try { //Se recibe la lectura de consola
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
			
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
			mitadPlatos = (int) Math.ceil(numPlatos/2);
			line = br.readLine();
			str = line.split("= ")[1];
			tamFregadero = Integer.parseInt(str);
			int barrera = numComensales;
			CyclicBarrier cb = new CyclicBarrier(barrera);
			
			
			
			for(int i = 0; i < numComensales; i++) {
				Comensal cm = new Comensal(cb,i);
				cm.start();
				comensales.add(cm);
			}
			Lavaplatos lavap = new Lavaplatos(comensales);
			lavap.start();
			lp = lavap;
			is.close();
			br.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static Lavaplatos getLp() {
		return lp;
	}


	public static void setLp(Lavaplatos lp) {
		Mesa.lp = lp;
	}


	public int getComensal() {
		return numComensales;
	}
	public static int getComensalesTerminaron() {
		return comensalesTerminaron;
	}
	public static void setComensalesTerminaron(int comensalesTerminaron) {
		Mesa.comensalesTerminaron = comensalesTerminaron;
	}
	public void setComensal(int comensal) {
		Mesa.numComensales= comensal;
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
	public static int getNumParCubiertosSucios() {
		return numParCubiertosSucios;
	}
	public static void setNumParCubiertosSucios(int numParCubiertosSucios) {
		Mesa.numParCubiertosSucios = numParCubiertosSucios;
	}
	public static int getNumPlatos() {
		return numPlatos;
	}
	public static void setNumPlatos(int numPlatos) {
		Mesa.numPlatos = numPlatos;
	}
	
	public static int getNumComensales() {
		return numComensales;
	}

	public static void setNumComensales(int numComensales) {
		Mesa.numComensales = numComensales;
	}

	public static int getTamFregadero() {
		return tamFregadero;
	}

	public static void setTamFregadero(int tamFregadero) {
		Mesa.tamFregadero = tamFregadero;
	}
	
	public static int getMitadPlatos() {
		return mitadPlatos;
	}
}
