import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class ActualizadorRM extends Thread{
	
	private Queue<Integer> pagVirtual = new ArrayDeque<>();
	private Queue<String> tipo = new ArrayDeque<>();
	private boolean inicio = false;
	public boolean isInicio() {
		return inicio;
	}

	public void setInicio(boolean inicio) {
		this.inicio = inicio;
	}

	public static boolean isCorre() {
		return corre;
	}

	public static void setCorre(boolean corre) {
		ActualizadorRM.corre = corre;
	}

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
		inicio = true;
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
	
}
