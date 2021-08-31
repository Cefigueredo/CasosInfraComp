package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class Lavaplatos extends Thread{

	public void run() {
		try {
			while(Mesa.getNumPlatos() != 0) {
				if(Fregadero.getCantCubiertos() > 0) {
					
					recogerCubiertosFregadero();
					lavar();
					ponerCubiertosMesa();
				}
				sleep(10);
			}
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * 
	 */
	public synchronized void recogerCubiertosFregadero() {
		Fregadero.setCantCubiertos(Fregadero.getCantCubiertos()-2);
	}
	
	/**
	 * 
	 */
	public synchronized void lavar()
	{	try {
			
			//Tarda un tiempo aleatorio entre 1 y 2 seg
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
			sleep(randomNum*1000);
		//------------------------------------
		}
		catch(Exception e) {}
		
	}
	
	/**
	 * 
	 */
	public synchronized void ponerCubiertosMesa() {
		//Reduce cubiertos sucios
		Mesa.setNumCubiertosSuciosT1(Mesa.getNumCubiertosSuciosT1()-1);
		Mesa.setNumCubiertosSuciosT2(Mesa.getNumCubiertosSuciosT2()-1);
		//Aumenta cubiertos limpios
		Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()+1);
		Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()+1);
		//Avisa a los comensales
		notifyAll(); System.out.println("Se ponen cubiertos en mesa");
		
	}

}
