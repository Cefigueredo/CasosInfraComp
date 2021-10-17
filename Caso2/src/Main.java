import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {
	public static void main(String[] args) throws Exception{
		try { //Se recibe la lectura de consola
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
			
			String line = br.readLine();
			
			int numeroMarcosPagina = Integer.parseInt(line);
			line = br.readLine();
			int numeroPaginasDelProceso = Integer.parseInt(line);
			line = br.readLine();
			int numeroReferenciasEnArchivo = Integer.parseInt(line);
			System.out.println(numeroMarcosPagina);
			System.out.println(numeroPaginasDelProceso);
			System.out.println(numeroReferenciasEnArchivo);
			ArrayList<String> c = new ArrayList<>();
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
			
			System.out.println(i);

			is.close();
			br.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
