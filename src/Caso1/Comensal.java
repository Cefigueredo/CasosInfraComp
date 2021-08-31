package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class Comensal extends Thread{
	
	private int numPlatos;
	private boolean tieneCubierto1 = false;
	private boolean tieneCubierto2 = false;


	public void run() {
		try {
			while(numPlatos != 0) {
				cogerCubiertos();
				comer();
				dejarCubiertosFregadero();
			}
		}
		catch(Exception e) {}
		
		
	}

	/**
	 * Come entre 3 y 5 seg y reposa entre 1 y 3 seg
	 */
	public void comer(){
		try {
				/// Comer: Tardar entre 3 y 5 seg aleatoriamente
				// nextInt is normally exclusive of the top value,
				// so add 1 to make it inclusive
				int randomNum = ThreadLocalRandom.current().nextInt(3, 5 + 1);
				sleep(randomNum*1000);	
				// Reposa entre 1 y 3 seg aleatoriamente
				randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				sleep(randomNum*1000);
		}
		catch(Exception e) {e.printStackTrace();}
	}

	/**
	 * Debe salir habiendo cogido 2 cubiertos
	 */
	public void cogerCubiertos() {
		try {
			while(tieneCubierto1 == false || tieneCubierto2 == false) {
				cogerCubiertoT1();
				if(tieneCubierto1 == true) {
					long l = System.currentTimeMillis();
					cogerCubiertoT2();
					if(System.currentTimeMillis()-l>1000) {
						tieneCubierto1 = false;
					}
				}
			}
		}
		catch(Exception e) {}
	}
	
	public void cogerCubiertoT1() {
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
	public void cogerCubiertoT2() {
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
	public void dejarCubiertosFregadero() {
		try {
			//Tarda un tiempo aleatorio entre 1 y 3 seg
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			sleep(randomNum*1000);
			
			//Deja 2 cubiertos en el fregadero
			Fregadero.setCantCubiertos(Fregadero.getCantCubiertos()+2);
			//Ya no tiene cubiertos
			tieneCubierto1 = false;
			tieneCubierto2 = false;
			
			
		}
		catch(Exception e) {}
	}

	public void soltar(){
		if(tieneCubierto1== true && tieneCubierto2== false) {

		}
		else if(tieneCubierto1== false && tieneCubierto2 == true){

		}
	}
}