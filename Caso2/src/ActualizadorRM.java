import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class ActualizadorRM extends Thread{
	
	private Queue<Integer> pagVirtual = new ArrayDeque<>();
	private Queue<String> tipo = new ArrayDeque<>();
	private boolean inicio = false;
	
	public void run() {
		try {
			int cota = 500;
			while(cota!=0) {
				if(inicio) {

					reemplazarRef();
					cota--;
					if(cota == 2) {
						System.out.println("Llegamos");
					}
				}
				sleep(20);
			}
			
			
			
		}
		catch(Exception e) {}
	}

	public void actualizarRM(int pag, String tipo) {
		this.pagVirtual.add(pag);
		this.tipo.add(tipo);
		inicio = true;
	}
	
	public void reemplazarRef() {
		if(tipo.peek().equals("r")) {
			tipo.poll();
			
			//Buscar el valor de esa pag y esa ref
			

			List<Integer> valoresIniciales = new ArrayList<Integer>();
			List<Integer> rm = new ArrayList<Integer>();
			List<Integer> rnoM = new ArrayList<Integer>();
			List<Integer> noRM = new ArrayList<Integer>();
			List<Integer> noRnoM = new ArrayList<Integer>();
			valoresIniciales.add(pagVirtual.peek());
			valoresIniciales.add(1);
			valoresIniciales.add(Main.getValoresTabla().get(pagVirtual.peek()).get(2));

			
			rm.add(pagVirtual.peek());
			rm.add(1);
			rm.add(1);

			
			rnoM.add(pagVirtual.peek());
			rnoM.add(1);
			rnoM.add(0);

			
			noRM.add(pagVirtual.peek());
			noRM.add(0);
			noRM.add(1);

			
			noRnoM.add(pagVirtual.peek());
			noRnoM.add(0);
			noRnoM.add(0);

			
			for(int i = 0; i < Main.getValoresTabla().size(); i++) {

				if(Main.getValoresTabla().get(i).get(0)==valoresIniciales.get(0) ||Main.getValoresTabla().get(i).get(0)==-1) {
					
					if(Main.getValoresTabla().contains(noRnoM)||Main.getValoresTabla().get(i).get(0)==-1) {
						Main.getValoresTabla().put(i, valoresIniciales);
						Main.setNumFallosPag(Main.getNumFallosPag()+1);
						i=Main.getValoresTabla().size();
					}
					else if(Main.getValoresTabla().contains(noRM)) {
						Main.getValoresTabla().put(i, rm);
						Main.setNumFallosPag(Main.getNumFallosPag()+1);
						i=Main.getValoresTabla().size();
					}
					i=Main.getValoresTabla().size();
				}
			}
			pagVirtual.poll();
		}
		else if(tipo.peek().equals("m")) {
			tipo.poll();
			
			//Buscar el valor de esa pag y esa ref
			
			List<Integer> valoresIniciales = new ArrayList<Integer>();
			List<Integer> rm = new ArrayList<Integer>();
			List<Integer> rnoM = new ArrayList<Integer>();
			List<Integer> noRM = new ArrayList<Integer>();
			List<Integer> noRnoM = new ArrayList<Integer>();
			valoresIniciales.add(pagVirtual.peek());
			valoresIniciales.add(Main.getValoresTabla().get(pagVirtual.peek()).get(2));
			valoresIniciales.add(1);

			
			rm.add(pagVirtual.peek());
			rm.add(1);
			rm.add(1);

			
			rnoM.add(pagVirtual.peek());
			rnoM.add(1);
			rnoM.add(0);

			
			noRM.add(pagVirtual.peek());
			noRM.add(0);
			noRM.add(1);

			
			noRnoM.add(pagVirtual.peek());
			noRnoM.add(0);
			noRnoM.add(0);

			
			for(int i = 0; i < Main.getValoresTabla().size(); i++) {

				
				if(Main.getValoresTabla().get(i).get(0)==valoresIniciales.get(0) ||Main.getValoresTabla().get(i).get(0)==-1) {
					if(Main.getValoresTabla().contains(noRnoM)||Main.getValoresTabla().get(i).get(0)==-1) {
						Main.getValoresTabla().put(i, valoresIniciales);
						Main.setNumFallosPag(Main.getNumFallosPag()+1);
						i=Main.getValoresTabla().size();
					}
					else if(Main.getValoresTabla().contains(rnoM)) {
						Main.getValoresTabla().put(i, rm);
						Main.setNumFallosPag(Main.getNumFallosPag()+1);
						i=Main.getValoresTabla().size();
					}
					i=Main.getValoresTabla().size();
				}
			
			}
			pagVirtual.poll();
			
		}
	}
}
