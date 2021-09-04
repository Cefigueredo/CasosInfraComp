import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CyclicBarrier;

public class Mesa {
	private Comensal comensal;
	private static int numCubiertosT1;
	private static int numCubiertosT2;
	private static int numParCubiertosSucios=0;
	private static int numPlatos;
	private static int tamFregadero;
	private static int numComensales;
	
	public int getComensal() {
		return numComensales;
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

	private static int mitadPlatos;
	
	public static int getMitadPlatos() {
		return mitadPlatos;
	}
	
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
			int barrera = mitadPlatos;
			CyclicBarrier cb = new CyclicBarrier(barrera);
			
			
			new Lavaplatos().start();
			for(int i = 0; i < numComensales; i++) {
				new Comensal(cb,i+1).start();
			}
			is.close();
			br.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



}
