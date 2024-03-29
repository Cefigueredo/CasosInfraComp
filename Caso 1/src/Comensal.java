import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Comensal extends Thread{

	//---------------------------------------------------------------------------------------
	// Atributos
	//---------------------------------------------------------------------------------------
	private boolean tieneCubierto1 = false;
	private boolean tieneCubierto2 = false;
	private CyclicBarrier cb;
	private int id;
	private int platosComidos = 0;

	public Comensal(CyclicBarrier cb,int id) {
		this.cb = cb;
		this.id = id;
	}

	//---------------------------------------------------------------------------------------
	// M�todos
	//---------------------------------------------------------------------------------------
	public void run() {
		try {
			while(platosComidos != Mesa.getNumPlatos()) {
				cogerCubiertos();
				System.out.println("  Coge Cubiertos el comensal " + id);
				comer(); 
				dejarCubiertosFregadero();
				System.out.println("  El comensal " + id + " deja cubiertos en el fregadero" );
				despertar();

			}
			System.out.println("==/== El comensal " + id + " termin� la cena ==/==" );
			Mesa.setComensalesTerminaron(Mesa.getComensalesTerminaron()+1);
		}
		catch(Exception e) {}
	}

	/**
	 * El comensal come y espera un tiempo aleatorio entre 3 y 5 segundos.
	 */
	public synchronized void comer(){
		try {
			if(platosComidos == Mesa.getMitadPlatos()) {
				System.out.println(" [[El comensal " + id+ " esta esperando a los demas comensales que lleguen a la mitad de los platos]]");
				Mesa.getLp();
				Lavaplatos.getComenBarrera().set(id, true);
				cb.await();
				Mesa.getLp();
				Lavaplatos.getComenBarrera().set(id, false);
			}
			System.out.println(" $$$ El comensal " + id + " come el plato " + platosComidos+" $$$" );
			int randomNum = ThreadLocalRandom.current().nextInt(3, 5 + 1);
			sleep(randomNum*1000);
			platosComidos ++;
			//System.out.println("///   Quedan "+ platosFaltan +" platos para que el comensal " + id +" termine la cena  ///");

		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	
	/**
	 * Deja sus cubiertos en el fregadero y espera entre 1 y 3 segundos aleatoriamente.
	 */
//	public synchronized void dejarCubiertosFregadero() {
//		try {
//			//Tarda un tiempo aleatorio entre 1 y 3 seg
//
//			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
//			sleep(randomNum*1000);
//
//			while( Mesa.getNumParCubiertosSucios() > Mesa.getTamFregadero()) {
//				Comensal.yield();
//			}
//
//			Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()+1);
//			//Deja 1 par de  cubiertos en el fregadero
//			//Ya no tiene cubiertos
//			tieneCubierto1 = false;
//			tieneCubierto2 = false;
//
//		}
//		catch(Exception e) {}
//		
//	}
//	
	public void dejarCubiertosFregadero() {
		try {
			//Tarda un tiempo aleatorio entre 1 y 3 seg
			synchronized(this) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				sleep(randomNum*1000);
			}

			while( Mesa.getNumParCubiertosSucios() > Mesa.getTamFregadero()) {
				Comensal.yield();
			}
			synchronized(this) {
			Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()+1);
			}
			//Deja 1 par de  cubiertos en el fregadero
			//Ya no tiene cubiertos
			tieneCubierto1 = false;
			tieneCubierto2 = false;

		}
		catch(Exception e) {}
		
	}
	
	
	
	
	/**
	 * Coge ambos cubiertos
	 */
	public synchronized void cogerCubiertos() {
		try {
			
			
//			if(tieneCubierto1 == false || tieneCubierto2 == false) {
//				cogerCubiertoT1();
//				if(tieneCubierto1 == true) {
//					long l = System.currentTimeMillis();
//					cogerCubiertoT2();
//					if(System.currentTimeMillis()-l>1000) {
//						notifyAll();
//						tieneCubierto1 = false; System.out.println(" ~~~~  Suelta cubierto T1 ~~~~~~~~~~~~~");
//					}
//				}
//			}
			
			while(tieneCubierto1 == false || tieneCubierto2 == false) {
				while(tieneCubierto1 == false) {
					if(Mesa.getNumCubiertosT1()>0 ) {//Si hay T1, lo toma
						tieneCubierto1 = true;
						Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()-1);
					}
					else if(Mesa.getNumCubiertosT1()==0 && tieneCubierto1 == false) {//Si no hay T1, espera (pasiva)
						System.out.println("++++ Comensal "+id+" se durmi�   ++++++");
						wait();
					}
				}
				
				long l = System.currentTimeMillis();
				
				while(tieneCubierto1 == true && tieneCubierto2 == false){
					
					if(System.currentTimeMillis()-l>1000) {
						System.out.println("Pasaron: "+(System.currentTimeMillis()-l)+" ms...");
						tieneCubierto1 = false; System.out.println(" ~~~~  Suelta cubierto T1 ~~~~~~~~~~~~~");
						Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()+1);
						notifyAll();System.out.println("++++ Comensal "+id+" se durmi�   ++++++");
						wait();
					}
					else if(tieneCubierto2 == false && Mesa.getNumCubiertosT2()>0){//Si hay T2, lo toma
						tieneCubierto2 = true;
						Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()-1);
					}
					
				}
			}
			
		}
		catch(Exception e) {}
	}
	
	private synchronized void cogerCubiertoT1() {
		try {
			while(tieneCubierto1 = false) {
				if(Mesa.getNumCubiertosT1()>0 ) {//Si hay T1, lo toma
					tieneCubierto1 = true;
					Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()-1);
				}
				else if(Mesa.getNumCubiertosT1()==0 && tieneCubierto1 == false) {//Si no hay T1, espera (pasiva)
					wait(); 
				}

			}
		}
		catch(Exception e) {}
	}
	
	private synchronized void cogerCubiertoT2() {
		try {
			while(tieneCubierto2 == false)
			{
				if(Mesa.getNumCubiertosT2()>0){//Si hay T2, lo toma
					tieneCubierto2 = true;
					Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()-1);
				}
				else if(Mesa.getNumCubiertosT2()==0 && tieneCubierto2 == false) {//Si no hay T2, espera (pasiva)
					wait();
				}
			}
		}
		catch(Exception e) {}
	}
	/**
	 * Despierta a los dem�s comensales que est�n dormidos.
	 */
	public synchronized void  despertar() {
		try {
			notifyAll();
		}
		catch(Exception e){}
	}
	
	public void despertarUnoSolo() {
		try {
			notify();
		}
		catch(Exception e){}
	}
}