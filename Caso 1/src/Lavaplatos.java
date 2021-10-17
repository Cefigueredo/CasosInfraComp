import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Lavaplatos extends Thread{
	//---------------------------------------------------------------------------------------
	// Atributos
	//---------------------------------------------------------------------------------------
	private boolean x = true;
	private static ArrayList<Comensal> comensales = new ArrayList<Comensal>();
	private static ArrayList<Boolean> comenBarrera= new ArrayList<Boolean>();
	//---------------------------------------------------------------------------------------
	// Métodos
	//---------------------------------------------------------------------------------------
	public Lavaplatos(ArrayList<Comensal> comensales) {
		this.comensales = comensales;
		for(int i = 0; i < comensales.size(); i++) {
			comenBarrera.add(false);
		}
	}
	
	public void run() {
		try {

			while(x) {
				if(Mesa.getNumParCubiertosSucios()==0){
					Lavaplatos.yield();
				}
				else if(Mesa.getComensalesTerminaron()==Mesa.getNumComensales()) {
					x=false;
					System.out.println("********  Los comensales terminaron de comer  ********");
				}
				else {
					recogerCubiertosFregadero();
					lavar();
					ponerCubiertosMesa();
				}
			}

		}
		catch(Exception e) {

		}
	}
	
	/**
	 * El lavaplatos recoge los cubiertos sucios del fregadero.
	 */
	public synchronized void recogerCubiertosFregadero() {
		System.out.println("Lavando ");
		Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()-1);
	}

	/**
	 * El lavaplatos lava los cubiertos.
	 */
	public synchronized void lavar(){
		try {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
			sleep(randomNum*1000);
		}
		catch(Exception e) {}

	}
	
	/**
	 * El lavaplatos pone los cubiertos lavados en la mesa.
	 */
	public synchronized void ponerCubiertosMesa() {

		//Aumenta cubiertos limpios
		synchronized(this) {
			Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()+1);
			Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()+1);
		}
		
		//Avisa a los comensales
		
			try {
				for(int i = 0; i < comensales.size(); i++) {
					if(comenBarrera.get(i)==false) {
						comensales.get(i).despertar();
					}
				}
			}
			catch(Exception e) {}
		
		//Cómo se hace para que una clase A, que extiende de Thread, pueda realizar un notifyAll() para despertar los threads de una clase B que recibieron wait()?
		
		
		System.out.println("-- Se ponen cubiertos en mesa --");

	}

	public static ArrayList<Boolean> getComenBarrera() {
		return comenBarrera;
	}

	public static void setComenBarrera(ArrayList<Boolean> comenBarrera) {
		Lavaplatos.comenBarrera = comenBarrera;
	}


}
