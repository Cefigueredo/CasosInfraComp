import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Comensal extends Thread{


	private boolean tieneCubierto1 = false;
	private boolean tieneCubierto2 = false;
	private CyclicBarrier cb;
	private int id;
	private int platosComidos;
	private int platosFaltan = Mesa.getMitadPlatos()-platosComidos;
	
	
	
	public Comensal(CyclicBarrier cb,int id) {
		this.cb = cb;
		this.id = id;
	}

	public void run() {
		try {
			while(platosComidos != Mesa.getNumPlatos()+1) {
				recogerCubiertosT1();
				recogerCubiertosT2();
				//soltar
				System.out.println("Coge Cubiertos el comensal " + id);
				comer(); 
				dejarCubiertosFregadero();
				System.out.println("El comensal " + id + " deja cubiertos en el fregadero" );

			}
		}
		catch(Exception e) {}


	}

	

	public synchronized void comer(){
		try {
			if(platosComidos == Mesa.getMitadPlatos()) {
				cb.await() ;
				System.out.println("Espero a otros comensales.");

			}
			System.out.println("----------------------------------------------");
			System.out.println(" El comensal " + id + " come el plato " + Mesa.getNumPlatos());
			System.out.println("--------------------------------------------");
			System.out.println("Quendan "+ platosFaltan +" platos para que el comensal " + id +" termine la cena");
			int randomNum = ThreadLocalRandom.current().nextInt(3, 5 + 1);
			sleep(randomNum*1000);
			platosComidos ++;


			/// Comer: Tardar entre 3 y 5 seg aleatoriamente
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive

		}
		catch(Exception e) {e.printStackTrace();}
	}

	public synchronized void recogerCubiertosT1()
	{
		try {
			while(Mesa.getNumCubiertosT1() == 0){

				wait();
			} 
			Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()-1);

		}
		catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	public synchronized void recogerCubiertosT2()
	{
		try {
			while(Mesa.getNumCubiertosT2() == 0){

				wait();
			} 
			Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()-1);


			tieneCubierto2 = true;
		}
		catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void soltar(){
		if(tieneCubierto1== true && tieneCubierto2== false) {

			Mesa.setNumCubiertosT1(Mesa.getNumCubiertosT1()+1);
			notifyAll();
		}
		else if(tieneCubierto1== false && tieneCubierto2 == true){
			Mesa.setNumCubiertosT2(Mesa.getNumCubiertosT2()+1);
			notifyAll();
		}
	}
	/**
	 *Deja sus cubiertos en el fregadero 
	 */
	public synchronized void dejarCubiertosFregadero() {
		try {
			//Tarda un tiempo aleatorio entre 1 y 3 seg

			int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			sleep(randomNum*1000);

			while( Mesa.getNumParCubiertosSucios() > Mesa.getTamFregadero()) {
				System.out.println("Esperar que el fregadero se desocupe");
				Comensal.yield();
			}

			Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()+1);
			
			//Deja 1 par de  cubiertos en el fregadero
			//Ya no tiene cubiertos
			tieneCubierto1 = false;
			tieneCubierto2 = false;

			notifyAll();//Avisa al lavaplatos

		}
		catch(Exception e) {}
	}
}