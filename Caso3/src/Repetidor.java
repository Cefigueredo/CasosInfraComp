import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import Auxiliares.Asimetrico;
import Auxiliares.Simetrico;

public class Repetidor extends Thread{
	//Punto intermedio de comunicaci�n entre un cliente y el servidor.
	
	//Recibe del cliente un identificador de mensaje luego establece una conexi�n con el servidor y reenv�a el mensaje,
	//queda en espera por la respuesta y despu�s de recibirla la reenv�a al cliente que hizo la solicitud original.
	
	//Implementa repetidores delegados.
	
	
	private static String SERVER = "127.0.0.1";
	private static int SERVER_PORT = 3000;
	private static int CLIENT_PORT = 4000;
	private static int REP_PORT = 5000;
	private int id;
	private boolean centinela = true;
	private int tipoCifrado;
	
	public Repetidor(int id, int tipoCifrado) {
		this.id = id;
		this.tipoCifrado = tipoCifrado;
	}
	
	public void run() {
		try {
			Socket enviarRaC = new Socket(SERVER, CLIENT_PORT+id);
			DataOutputStream oos1 = new DataOutputStream(enviarRaC.getOutputStream());
			String mensajeRaC="OK";
			System.out.println("Repetidor "+id+" env�a a cliente: "+mensajeRaC);
			oos1.writeUTF(mensajeRaC);
			enviarRaC.close();
			long tiempoTotal = 0;
			
			if(tipoCifrado == 1) {
				ServerSocket repetidor = new ServerSocket(REP_PORT+id);
				Servidor sv = new Servidor(id, tipoCifrado);
				sv.start();
				while(centinela) {
					//Recibe desde el cliente
					Socket misocket = repetidor.accept();
					DataInputStream dis = new DataInputStream(misocket.getInputStream());
					String mensajetexto = dis.readUTF();
					System.out.println("Llega al repetidor "+id+": "+mensajetexto);
					
					long tiempoInicial1 = System.currentTimeMillis();
					
					//Descifra mensaje del cliente
					File f = new File("llavesSimetricas/K_C"+id+"R"+id);
					FileInputStream fis = new FileInputStream(f);
					Key key;
					ObjectInputStream oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					SecretKey secret = (SecretKey) key;
					Simetrico sm = new Simetrico();
					byte[] mensajeEnBytes = sm.str2byte(mensajetexto);
					byte[] mensajeDescifrado = sm.descifrar(secret, mensajeEnBytes);
					String str = new String(mensajeDescifrado);
					
					//Cifrar mensaje del cliente
					f = new File("llavesSimetricas/K_R"+id+"S"+id);
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					secret = (SecretKey) key;
					sm = new Simetrico();
					String mensajeClienteDesAStr = new String(mensajeDescifrado);
					byte[] repetidorCifrando = sm.cifrar(secret, mensajeClienteDesAStr);
					String repetidorCifradoString = sm.byte2str(repetidorCifrando);
					
					long tiempoFinal1 = System.currentTimeMillis() - tiempoInicial1;
					
					
					//Env�a al servidor
					Socket enviarAServidor = new Socket(SERVER, SERVER_PORT+id);
					DataOutputStream oos = new DataOutputStream(enviarAServidor.getOutputStream());
					System.out.println("Repetidor "+id+" env�a a servidor: "+repetidorCifradoString);
					oos.writeUTF(repetidorCifradoString);
					oos.close();
					enviarAServidor.close();
					
					misocket.close();
					
					
					//Recibe desde el servidor
					Socket socketServidor = repetidor.accept();
					DataInputStream dis2 = new DataInputStream(socketServidor.getInputStream());
					String obj = dis2.readUTF();
					System.out.println("Llega al repetidor "+id+" desde el servidor: "+obj);
					dis2.close();
					socketServidor.close();
					
					long tiempoInicial2 = System.currentTimeMillis();
					
					//Descifrar mensaje del servidor
					f = new File("llavesSimetricas/K_R"+id+"S"+id);
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					secret = (SecretKey) key;
					sm = new Simetrico();
					mensajeEnBytes = sm.str2byte(obj);
					mensajeDescifrado = sm.descifrar(secret, mensajeEnBytes);
					String mensajeDescifradoString = new String(mensajeDescifrado);
					
					//Cifrar mensaje para enviar a cliente
					f = new File("llavesSimetricas/K_C"+id+"R"+id);
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					secret = (SecretKey) key;
					sm = new Simetrico();
					repetidorCifrando = sm.cifrar(secret, mensajeDescifradoString);
					repetidorCifradoString = sm.byte2str(repetidorCifrando);
					
					
					long tiempoFinal2 = System.currentTimeMillis() - tiempoInicial2;
					tiempoTotal = tiempoFinal1+tiempoFinal2;
					System.out.println("Tiempo total en repetidor "+id+" es: "+tiempoTotal);
					Main.listaDeTiempos.add((int) tiempoTotal);
					//Env�a el repetidor al cliente
					Socket enviarCliente = new Socket(SERVER, CLIENT_PORT+id);
					DataOutputStream oos3 = new DataOutputStream(enviarCliente.getOutputStream());
					System.out.println("Repetidor "+id+" env�a a cliente: "+repetidorCifradoString);
					oos3.writeUTF(repetidorCifradoString);
					enviarCliente.close();
				}
			}
			else if(tipoCifrado == 2) {
				ServerSocket repetidor = new ServerSocket(REP_PORT+id);
				Servidor sv = new Servidor(id, tipoCifrado);
				sv.start();
				while(centinela) {
					//Recibe desde el cliente
					Socket misocket = repetidor.accept();
					DataInputStream dis = new DataInputStream(misocket.getInputStream());
					String mensajetexto = dis.readUTF();
					System.out.println("Llega al repetidor "+id+": "+mensajetexto);
					long tiempoInicial1 = System.currentTimeMillis();
					
					//Descifra mensaje del cliente
					File f = new File("llavesAsimetricas/K_R"+id+"-");
					FileInputStream fis = new FileInputStream(f);
					Key key;
					ObjectInputStream oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					PrivateKey prk = (PrivateKey) key;
					Asimetrico asm = new Asimetrico();
					byte[] mensajeEnBytes = asm.str2byte(mensajetexto);
					byte[] mensajeDescifrado = asm.descifrarConPrivada(mensajeEnBytes, prk);
					String str = new String(mensajeDescifrado);
					
					//Cifrar mensaje del cliente
					f = new File("llavesAsimetricas/K_S"+id+"+");
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					PublicKey pub = (PublicKey) key;
					asm = new Asimetrico();
					String mensajeClienteDesAStr = new String(mensajeDescifrado);
					byte[] repetidorCifrando = asm.cifrarConPublica(mensajeClienteDesAStr, pub);
					String repetidorCifradoString = asm.byte2str(repetidorCifrando);
					
					long tiempoFinal1 = System.currentTimeMillis() - tiempoInicial1;
					
					
					//Env�a al servidor
					Socket enviarAServidor = new Socket(SERVER, SERVER_PORT+id);
					DataOutputStream oos = new DataOutputStream(enviarAServidor.getOutputStream());
					oos.writeUTF(repetidorCifradoString);
					oos.close();
					enviarAServidor.close();
					System.out.println("Repetidor "+id+" env�a a servidor: "+repetidorCifradoString);
					misocket.close();
					
					
					//Recibe desde el servidor
					Socket socketServidor = repetidor.accept();
					DataInputStream dis2 = new DataInputStream(socketServidor.getInputStream());
					String obj = dis2.readUTF();
					System.out.println("Llega al repetidor "+id+" desde el servidor: "+obj);
					dis2.close();
					socketServidor.close();
					
					long tiempoInicial2 = System.currentTimeMillis();
					//Descifrar mensaje del servidor
					f = new File("llavesAsimetricas/K_R"+id+"-");
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					prk = (PrivateKey) key;
					mensajeEnBytes = asm.str2byte(obj);
					mensajeDescifrado = asm.descifrarConPrivada(mensajeEnBytes, prk);
					String mensajeDescifradoString = new String(mensajeDescifrado);
					
					//Cifrar mensaje para enviar a cliente
					f = new File("llavesAsimetricas/K_C"+id+"+");
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					pub = (PublicKey) key;
					repetidorCifrando = asm.cifrarConPublica(mensajeDescifradoString, pub);
					repetidorCifradoString = asm.byte2str(repetidorCifrando);
					
					long tiempoFinal2 = System.currentTimeMillis() - tiempoInicial2;
					tiempoTotal = tiempoFinal1+tiempoFinal2;
					System.out.println("Tiempo total en repetidor "+id+" es: "+tiempoTotal);
					Main.listaDeTiempos.add((int) tiempoTotal);
					//Env�a el repetidor al cliente
					Socket enviarCliente = new Socket(SERVER, CLIENT_PORT+id);
					DataOutputStream oos3 = new DataOutputStream(enviarCliente.getOutputStream());
					oos3.writeUTF(repetidorCifradoString);
					enviarCliente.close();
					System.out.println("Repetidor "+id+" env�a a cliente: "+repetidorCifradoString);
					misocket.close();
				}
				
			}
			
			
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
