import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Comensal extends Thread{


	private boolean tieneCubierto1 = false;
	private boolean tieneCubierto2 = false;
	private CyclicBarrier cb;
	private int id;
	private int platosComidos = 0;
	private int platosFaltan;



	public Comensal(CyclicBarrier cb,int id) {
		this.cb = cb;
		this.id = id;
	}

	public void run() {
		try {
			while(platosComidos != Mesa.getNumPlatos()) {
				cogerCubiertos();
				//soltar
				System.out.println("Coge Cubiertos el comensal " + id);
				comer(); 
				dejarCubiertosFregadero();
				System.out.println("El comensal " + id + " deja cubiertos en el fregadero" );
				despertar();

			}
			System.out.println("El comensal " + id + " termino la cena" );
			Mesa.setComensalesTerminaron(Mesa.getComensalesTerminaron()+1);
		}
		catch(Exception e) {}


	}



	public synchronized void comer(){
		try {
			if(platosComidos == Mesa.getMitadPlatos()) {
				System.out.println("El comensal " + id+ " esta esperando a los demas comensales que lleguen a la mitad de los platos");
				cb.await() ;
			}
			System.out.println("----------------------------------------------");
			System.out.println(" El comensal " + id + " come el plato " + platosComidos);
			System.out.println("--------------------------------------------");
			int randomNum = ThreadLocalRandom.current().nextInt(3, 5 + 1);
			sleep(randomNum*1000);
			platosComidos ++;
			platosFaltan = Mesa.getNumPlatos()-platosComidos;
			System.out.println("Quendan "+ platosFaltan +" platos para que el comensal " + id +" termine la cena");


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
	public synchronized void aTerminado() {
		if(platosComidos== Mesa.getNumPlatos()){
			Mesa.setComensalesTerminaron(Mesa.getComensalesTerminaron()+1);
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
				Comensal.yield();
			}

			Mesa.setNumParCubiertosSucios(Mesa.getNumParCubiertosSucios()+1);
			//Deja 1 par de  cubiertos en el fregadero
			//Ya no tiene cubiertos
			tieneCubierto1 = false;
			tieneCubierto2 = false;

		}
		catch(Exception e) {}
	}
	public synchronized void cogerCubiertos() {
		try {
			if(tieneCubierto1 == false || tieneCubierto2 == false) {
				cogerCubiertoT1();
				if(tieneCubierto1 == true) {
					long l = System.currentTimeMillis();
					cogerCubiertoT2();
					if(System.currentTimeMillis()-l>1000) {
						notifyAll();
						tieneCubierto1 = false; System.out.println("Suelta cubierto T1");
					}
				}
			}
		}
		catch(Exception e) {}
	}
	public synchronized void cogerCubiertoT1() {
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
	public synchronized void cogerCubiertoT2() {
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
	public synchronized void  despertar() {

		try {
			notifyAll();
		}
		catch(Exception e)
		{

		}


	}


}