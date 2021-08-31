package Caso1;


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
	
	//----------------------------
	//Getters and setters
	//-----------------------------
	public Comensal getComensal() {
		return comensal;
	}
	public void setComensal(Comensal comensal) {
		this.comensal = comensal;
	}
	public static int getNumCubiertosT1() {
		return numCubiertosT1;
	}
	public static void setNumCubiertosT1(int numCubiertosT1) {
		Mesa.numCubiertosT1 = numCubiertosT1;
	}
	public static int getNumCubiertosT2() {
		return numCubiertosT2;
	}
	public static void setNumCubiertosT2(int numCubiertosT2) {
		Mesa.numCubiertosT2 = numCubiertosT2;
	}
	public static int getNumCubiertosSuciosT1() {
		return numCubiertosSuciosT1;
	}
	public static void setNumCubiertosSuciosT1(int numCubiertosSuciosT1) {
		Mesa.numCubiertosSuciosT1 = numCubiertosSuciosT1;
	}
	public static int getNumCubiertosSuciosT2() {
		return numCubiertosSuciosT2;
	}
	public static void setNumCubiertosSuciosT2(int numCubiertosSuciosT2) {
		Mesa.numCubiertosSuciosT2 = numCubiertosSuciosT2;
	}
	public static int getNumPlatos() {
		return numPlatos;
	}
	public static void setNumPlatos(int numPlatos) {
		Mesa.numPlatos = numPlatos;
	}
	//------------------------------------------------

}
