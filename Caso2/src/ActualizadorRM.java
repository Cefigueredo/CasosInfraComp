import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ActualizadorRM extends Thread{
	
	private static boolean inicio = false;
	private static boolean corre = true;
	
	public void run() {
		try {
			while(corre) {
				if(inicio) {
					cleanR();
				}
				sleep(20);
			}	
		}
		catch(Exception e) {}
	}
	
	public synchronized void cleanR() {

		for(int i = 0; i < Main.getValoresTabla().size(); i++) {
			
			
			List<Integer> rm1 = new ArrayList<Integer>();
			List<Integer> rm2 = new ArrayList<Integer>();
			
			rm1.add(Main.getValoresTabla().get(i).get(0));
			rm1.add(1);
			rm1.add(Main.getValoresTabla().get(i).get(2));
			
			rm2.add(Main.getValoresTabla().get(i).get(0));
			rm2.add(0);
			rm2.add(Main.getValoresTabla().get(i).get(2));
			
			if(Main.getValoresTabla().contains(rm1)) {
				Main.getValoresTabla().put(i, rm2);
			}
		}
	}
	
	public static boolean isInicio() {
		return inicio;
	}

	public static void setInicio(boolean inicio) {
		ActualizadorRM.inicio = inicio;
	}

	public static boolean isCorre() {
		return corre;
	}

	public static void setCorre(boolean corre) {
		ActualizadorRM.corre = corre;
	}
	
}
