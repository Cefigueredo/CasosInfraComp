import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {
	
	
	private static int[] mp;
	private static int[] tp;
	private static int[] tRef;
	private static int[] tMod;
	private int refActual = 0;
	private String tipoRefActual = "";
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
			
			mp = new int[numeroMarcosPagina];
			tp = new int[numeroPaginasDelProceso];
			tRef = new int[numeroPaginasDelProceso];
			tMod = new int[numeroPaginasDelProceso];
			
			for(int k = 0; k < numeroMarcosPagina; k++) {
				mp[k] = -1;
				tRef[k] = 0;
				tMod[k] = 0;
			}
			
			for(int k = 0; k < numeroPaginasDelProceso; k++) {
				tp[k] = k;
				tRef[k] = 0;
				tMod[k] = 0;
			}
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
				tpmp.setRef(Integer.parseInt(instruc[k][0]), instruc[k][1]);
			}


			is.close();
			br.close();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[] getMp() {
		return mp;
	}

	public static void setMp(int[] mp) {
		Main.mp = mp;
	}

	public static int[] getTp() {
		return tp;
	}

	public static void setTp(int[] tp) {
		Main.tp = tp;
	}
 
}
