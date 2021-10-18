import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ActualizadorTPyMP extends Thread{

	private Deque<Integer> cola = new LinkedList<>();
	private Queue<Integer> ref = new ArrayDeque<Integer>();
	private Queue<String> tipoRef = new ArrayDeque<String>();
	private  Hashtable<Integer, List<Integer>> valoresTabla;
	private HashSet<Integer> hashControl;

	public void run() {
		try {
			cargarUnaReferencia();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void  cargarUnaReferencia() throws InterruptedException {
		hashControl = new HashSet<>();
		valoresTabla = Main.getValoresTabla();
		List<Integer> posiciones = new ArrayList<Integer>();
		for(int i =0;i< Main.getInstruc().length;i++) {
			boolean falloPag = false;
			List<Integer> valoresTp = new ArrayList<Integer>();
			String[][] referenciaActual = Main.getInstruc();
			int numReferencian = Integer.parseInt(referenciaActual[i][0]);
			if (!hashControl.contains(numReferencian)) {
				falloPag = true;
				if(cola.size()== Main.getMp()) {
					int last = cola.removeLast();
					hashControl.remove(last);
					valoresTp.add(-1);
					valoresTp.add(1);
					valoresTp.add(0);
					valoresTabla.put(last,valoresTp );
					posiciones.set(posiciones.indexOf(last), -1);
				}
				posiciones.add(numReferencian);
			}
			else {
				cola.remove(numReferencian);
			}
			int pos = posiciones.indexOf(-1);
			if(pos >=0) {
				posiciones.remove(posiciones.size()-1);
				posiciones.set(pos, numReferencian);
			}
			cola.push(numReferencian);
			hashControl.add(numReferencian);
			int counter = 0;
			for (Integer number : posiciones) {
				if (number == numReferencian) {
					break;
				}
				else counter++;
			}

			if(falloPag == true) {
				valoresTp = new ArrayList<Integer>();
				valoresTp.add(counter);
				valoresTp.add(1);
				valoresTp.add(0);
				valoresTabla.put(numReferencian,valoresTp);
				Main.setNumFallosPag(Main.getNumFallosPag()+1);
			}
			System.out.println(posiciones.size());
			System.out.println(Main.getNumFallosPag());


			sleep(1);
		}
	}
}

