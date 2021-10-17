import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {
	
	private static ArrayList<Integer> mp = new ArrayList<>();
	private static ArrayList<Integer> tp = new ArrayList<>();
	
	public static void main(String[] args) throws Exception{
		try { //Se recibe la lectura de consola
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);

			String line = br.readLine();

			//Guarda el num de MP, el num de Pag y el num de Ref en archivo
			int numeroMarcosPagina = Integer.parseInt(line);
			line = br.readLine();
			int numeroPaginasDelProceso = Integer.parseInt(line);
			line = br.readLine();
			int numeroReferenciasEnArchivo = Integer.parseInt(line);
			
			for(int k = 0; k < numeroMarcosPagina; k++) {
				mp.add(-1);
			}
			
			for(int k = 0; k < numeroPaginasDelProceso; k++) {
				tp.add(-1);
			}
			
			ArrayList<String> c = new ArrayList<>();

			//En cada fila de instruc, en la primera columna pone la referencia y en la segunda pone el tipo de referencia (r,m)
			int i = 0;
			String[][] instruc = new String[numeroReferenciasEnArchivo][2];
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
			
			for(int k = 0; k < instruc.length; k++) {
				if(instruc[k][0].equals("0")) {
					
				}
			}
			
			
			
			

			is.close();
			br.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Integer> getMp() {
		return mp;
	}

	public static void setMp(ArrayList<Integer> mp) {
		Main.mp = mp;
	}

	public static ArrayList<Integer> getTp() {
		return tp;
	}

	public static void setTp(ArrayList<Integer> tp) {
		Main.tp = tp;
	}
 
}
