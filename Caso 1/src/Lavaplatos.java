import java.util.concurrent.ThreadLocalRandom;

public class Lavaplatos extends Thread{

	private boolean x = true;


	public void run() {
		try {
			//			while(Mesa.getNumPlatos() != 0 ) {
			//				if(Fregadero.getCantCubiertos()==0)
			//					Lavaplatos.yield();
			//				else if(Fregadero.getCantCubiertos() > 0) {
			//					
			//					recogerCubiertosFregadero();
			//					lavar();
			//					ponerCubiertosMesa();
			//				}
			//				sleep(10);
			//			}

			while(x) {
				if(Mesa.getNumParCubiertosSucios()==0)
				{
					Lavaplatos.yield();
				}
				else if(Mesa.getComensalesTerminaron()==Mesa.getNumComensales()) {
					x=false;
					System.out.println("Los comensales terminaron de comer");
				}
				else {
					recogerCubiertosFregadero();
					lavar();
					ponerCubiertosMesa();
				}
			}

			/*
			while(Mesa.getCantCubiertosSucios() > 0) {

				recogerCubiertosFregadero();
				lavar();
				ponerCubiertosMesa();
				while(Fregadero.getCantCubiertos() == 0) {
					System.out.println("Lavaplatos cede fregadero.....");
					Lavaplatos.yield(); 
				}
			}
			 */

		}
		catch(Exception e) {

		}
	}
	public synchronized void recogerCubiertosFregadero() {
		System.out.println("lavando ");
		Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()-1);
	}


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
	public synchronized void ponerCubiertosMesa() {

		//Aumenta cubiertos limpios
		Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()+1);
		Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()+1);
		//Avisa a los comensales
		notifyAll();
		System.out.println("Se ponen cubiertos en mesa");

	}


}
