package Caso1;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Comensal extends Thread{
	
	private boolean tieneCubierto1 = false;
	private boolean tieneCubierto2 = false;
	private static boolean llegaMitadCena = false;
	private static CyclicBarrier cb;
	
	public Comensal(CyclicBarrier cb) {
		this.cb = cb;
	}
	public void run() {
		try {
			while(Mesa.getNumPlatos() != 0) {
				cogerCubiertos(); System.out.println("Coge Cubiertos");
				comer(); System.out.println("Come");
				dejarCubiertosFregadero(); System.out.println("Deja cubiertos en fregadero");
				
				
			}
		}
		catch(Exception e) {}
		
		
	}

	/**
	 * Come entre 3 y 5 seg y reposa entre 1 y 3 seg
	 */
	public synchronized void comer(){
		try {
			Mesa.setNumPlatos(Mesa.getNumPlatos()-1);System.out.println("Quedan "+Mesa.getNumPlatos()+" platos.");
			if(llegaMitadCena == false && Mesa.getNumPlatos() > Mesa.getMitadCena()) {
				cb.await(); System.out.println("Espero a otros comensales.");

			}
			
			if(llegaMitadCena == false && Mesa.getNumPlatos() == Mesa.getMitadCena()) {
				llegaMitadCena = true; System.out.println("Llegué a mitad de cena.");
			}
			if(llegaMitadCena == true) {
				System.out.println("Empieza a comer.");
				/// Comer: Tardar entre 3 y 5 seg aleatoriamente
				// nextInt is normally exclusive of the top value,
				// so add 1 to make it inclusive
				int randomNum = ThreadLocalRandom.current().nextInt(3, 5 + 1);
				sleep(randomNum*1000);
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}

	/**
	 * Debe salir habiendo cogido 2 cubiertos
	 */
	public synchronized void cogerCubiertos() {
		try {
			while(tieneCubierto1 == false || tieneCubierto2 == false) {
				cogerCubiertoT1();
				if(tieneCubierto1 == true) {
					long l = System.currentTimeMillis();
					cogerCubiertoT2();
					if(System.currentTimeMillis()-l>1000) {
						tieneCubierto1 = false; System.out.println("Suelta cubierto T1");
					}
				}
			}
		}
		catch(Exception e) {}
	}
	
	public synchronized void cogerCubiertoT1() {
		try {
			if(Mesa.getNumCubiertosT1()>0 && tieneCubierto1 == false) {//Si hay T1, lo toma
				tieneCubierto1 = true;
				Mesa.setNumCubiertosSuciosT1(Mesa.getNumCubiertosSuciosT1()+1);
				Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()-1);
			}
			else if(Mesa.getNumCubiertosT1()==0 && tieneCubierto1 == false) {//Si no hay T1, espera (pasiva)
				wait();
			}
		}
		catch(Exception e) {}
	}
	public synchronized void cogerCubiertoT2() {
		try {
			if(Mesa.getNumCubiertosT2()>0 && tieneCubierto2 == false){//Si hay T2, lo toma
				tieneCubierto2 = true;
				Mesa.setNumCubiertosSuciosT2(Mesa.getNumCubiertosSuciosT2()+1);
				Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()-1);
			}
			else if(Mesa.getNumCubiertosT2()==0 && tieneCubierto2 == false) {//Si no hay T2, espera (pasiva)
				wait();
			}
		}
		catch(Exception e) {}
	}
	
	/**
	 *Deja sus cubiertos en el fregadero 
	 */
	public synchronized void dejarCubiertosFregadero() {
		try {
			//Tarda un tiempo aleatorio entre 1 y 3 seg
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			sleep(randomNum*1000);
			
			while(Fregadero.getTamFregadero() == Fregadero.getCantCubiertos()) {
				System.out.println("Comensal cede fregadero.........");
				Comensal.yield();
			}
			
			//Deja 1 par de  cubiertos en el fregadero
			Fregadero.setCantCubiertos(Fregadero.getCantCubiertos()+1);
			//Ya no tiene cubiertos
			tieneCubierto1 = false;
			tieneCubierto2 = false;

			notifyAll();//Avisa al lavaplatos
			
		}
		catch(Exception e) {}
	}
}