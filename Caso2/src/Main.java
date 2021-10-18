import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Main {


	private static int mp;
	private static int tp;
	private static int referencias;
	private static Hashtable<Integer, List<Integer>> valoresTabla;
	private static String[][] instruc ;
	private static int numFallosPag=0;


	public static void main(String[] args) throws Exception{
		try {
			File file = new File("./referencias8_32_75.txt"); //Archivo a leer
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = br.readLine();

			//Guarda el num de MP, el num de Pag y el num de Ref en archivo
			int numeroMarcosPagina = Integer.parseInt(line);
			line = br.readLine();
			int numeroPaginasDelProceso = Integer.parseInt(line);
			line = br.readLine();
			int numeroReferenciasEnArchivo = Integer.parseInt(line);

			mp = numeroMarcosPagina;
			tp = numeroPaginasDelProceso;
			referencias = numeroReferenciasEnArchivo;
			valoresTabla = new Hashtable<>();
			List<Integer> valoresIniciales = new ArrayList<Integer>();
			valoresIniciales.add(-1);
			valoresIniciales.add(0);
			valoresIniciales.add(0);

			for(int k = 0; k < numeroPaginasDelProceso; k++) {
				valoresTabla.put(k,valoresIniciales);
			}
			//En cada fila de instruc, en la primera columna pone la referencia y en la segunda pone el tipo de referencia (r,m)
			int i = 0;
			instruc= new String[numeroReferenciasEnArchivo][2];
			while(line!=null && line.length()>0 && !"".equals(line) && i < numeroReferenciasEnArchivo) {
				line = br.readLine();
				String[] tuple = new String[2];
				tuple = line.split(",");
				instruc[i][0] = tuple[0];
				instruc[i][1] = tuple[1];
				i++;
			}

			ActualizadorTPyMP tpmp = new ActualizadorTPyMP();
			tpmp.start();
			ActualizadorRM rm = new ActualizadorRM();
			rm.start();
			rm.cleanR();
			br.close();
			

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[][] getInstruc() {
		return instruc;
	}

	public static void setInstruc(String[][] instruc) {
		Main.instruc = instruc;
	}

	public static  int getMp() {
		return mp;
	}

	public static  void setMp(int mp) {
		Main.mp = mp;
	}

	public static Hashtable<Integer, List<Integer>> getValoresTabla() {
		return valoresTabla;
	}

	public static void setValoresTabla(Hashtable<Integer, List<Integer>> valoresTabla) {
		Main.valoresTabla = valoresTabla;
	}

	public static int getNumFallosPag() {
		return numFallosPag;
	}

	public  static void setNumFallosPag(int numFallosPag) {
		Main.numFallosPag = numFallosPag;
	}

	public static int getTp() {
		return tp;
	}

	public static void setTp(int tp) {
		Main.tp = tp;
	}

	public static int getReferencias() {
		return referencias;
	}

	public static void setReferencias(int referencias) {
		Main.referencias = referencias;
	}

}
