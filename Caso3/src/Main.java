
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

import Auxiliares.GeneradorLLaveSimetrica;
import Auxiliares.GeneradorLLavesAsimetricas;

public class Main extends Thread {
	
	public static ArrayList<Integer> listaDeTiempos = new ArrayList<Integer>();
	
	public static boolean esperar = false;
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException, InterruptedException {
		
		//Pide cantidad de clientes y si usa cifrado simétrico o asimétrico
		File file = new File("escenario.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line = br.readLine();
	
		int cantidadClientes = Integer.parseInt(line);
		System.out.println("Usted eligió "+cantidadClientes+" clientes.");
		br.readLine();
		line = br.readLine();
		
		int nTipoCifrado = Integer.parseInt(line);
		
		if(nTipoCifrado==1) {//Generando llaves simétricas
			System.out.println("Usted eligió cifrado simétrico");
			System.out.println("Generando llaves simétricas...");
			new GeneradorLLaveSimetrica().generarLLaves(cantidadClientes);
		}
		else if(nTipoCifrado==2) {//Generando llaves asimétricas
			System.out.println("Usted eligió cifrado asimétrico");
			System.out.println("Generando llaves asimétricas...");
			new GeneradorLLavesAsimetricas().generarLLaves(cantidadClientes);
		}
		else {
			System.out.println("Error");
		}
		
		CyclicBarrier cb = new CyclicBarrier(cantidadClientes);
		
		
		for(int i = 0; i < cantidadClientes; ++i) {	
			Cliente cl = new Cliente(i, nTipoCifrado, cb);
			cl.start();
		}
		
		while(listaDeTiempos.size()<cantidadClientes) {
			sleep(100);
		}
		
		
		double tiempoPromedio = 0;
		for(int i = 0; i < cantidadClientes; ++i) {
			tiempoPromedio += listaDeTiempos.get(i);
		}
		tiempoPromedio = tiempoPromedio/cantidadClientes;
		System.out.println("Tiempo promedio en repetidores: "+tiempoPromedio);
	}
}
