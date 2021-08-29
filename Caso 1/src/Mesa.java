
public class Mesa {
	private Comensal comensal;
	private static int numCubiertosT1;
	private static int numCubiertosT2;
	private static int numCubiertosSuciosT1;
	private static int numCubiertosSuciosT2;
	private static int numPlatos;


	public synchronized void recogerCubiertosT1()
	{
		try {
			while(numCubiertosT1 == 0){

				wait();
			} 
			numCubiertosT1 --;
		}
		catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	public synchronized void recogerCubiertosT2()
	{
		try {
			while(numCubiertosT2 == 0){

				wait();
			} 
			numCubiertosT2 --;
		}
		catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	


}
