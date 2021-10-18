import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class ActualizadorTPyMP extends Thread{

	private Deque<Integer> cola = new LinkedList<>();
	private  Hashtable<Integer, List<Integer>> valoresTabla;
	private HashSet<Integer> hashControl;

	public void run() {
		try {
			hashControl = new HashSet<>();
			valoresTabla = Main.getValoresTabla();
			List<Integer> posiciones = new ArrayList<Integer>();
			for(int i =0;i< Main.getInstruc().length;i++) {
				cargarUnaReferencia(i,posiciones);
				Main.setValoresTabla(valoresTabla);
				sleep(1);
			}
			System.out.println("==================================");
			System.out.println("Num fallos de pag :" + Main.getNumFallosPag());
			System.out.println("\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void  cargarUnaReferencia(int i,List<Integer> posiciones) throws InterruptedException {
		boolean falloPag = false;
		List<Integer> valoresTp = new ArrayList<Integer>();
		String[][] referenciaActual = Main.getInstruc();
		int numReferencian = Integer.parseInt(referenciaActual[i][0]);
		String estadoActual = referenciaActual[i][1];

		if (!hashControl.contains(numReferencian) ) {
			falloPag = true;
			if(cola.size()== Main.getMp()) {
				int last = cola.removeLast();
				hashControl.remove(last);
				List<Integer> referenciado = valoresTabla.get(last);
				List<Integer> teniaBitM =  new ArrayList<Integer>();
				teniaBitM.add(referenciado.get(0));
				teniaBitM.add(1);
				teniaBitM.add(0);
				if(referenciado.equals(teniaBitM)) {
					valoresTp.add(-1);
					valoresTp.add(0);
					valoresTp.add(1);
				}
				else {
					valoresTp.add(-1);
					valoresTp.add(0);
					valoresTp.add(0);
				}

				valoresTabla.put(last,valoresTp );
				posiciones.set(posiciones.indexOf(last), -1);
			}
			posiciones.add(numReferencian);

		}
		else {
			List<Integer> referenciado = valoresTabla.get(numReferencian);
			List<Integer> teniaBitR =  new ArrayList<Integer>();
			teniaBitR.add(referenciado.get(0));
			teniaBitR.add(1);
			teniaBitR.add(0);
			
			if(referenciado.equals(teniaBitR) && estadoActual.equals("m")) {
				valoresTp = new ArrayList<Integer>();
				valoresTp.add(referenciado.get(0));
				valoresTp.add(1);
				valoresTp.add(1);
				valoresTabla.put(numReferencian,valoresTp);
				Main.setNumFallosPag(Main.getNumFallosPag()+1);
			}
			cola.remove(numReferencian);
		}

		cola.push(numReferencian);
		hashControl.add(numReferencian);

		int counter = 0;	
		int pos = posiciones.indexOf(-1);
		if(pos >=0) {
			posiciones.remove(posiciones.size()-1);
			posiciones.set(pos, numReferencian);
		}

		for (Integer number : posiciones) {
			if (number == numReferencian) {
				break;
			}
			else counter++;
		}


		if(falloPag == true) {
			if(estadoActual.equals("r")) {
				valoresTp = new ArrayList<Integer>();
				valoresTp.add(counter);
				valoresTp.add(1);
				valoresTp.add(0);
			}
			else {
				valoresTp = new ArrayList<Integer>();
				valoresTp.add(counter);
				valoresTp.add(1);
				valoresTp.add(1);
			}

			valoresTabla.put(numReferencian,valoresTp);
			Main.setNumFallosPag(Main.getNumFallosPag()+1);
		}

	}

}

