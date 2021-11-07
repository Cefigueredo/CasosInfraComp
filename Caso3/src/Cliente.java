import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.SecretKey;

import Auxiliares.Asimetrico;
import Auxiliares.Simetrico;

public class Cliente extends Thread{
	//Es un programa que hace una solicitud al servidor por medio del repetidor. Es decir, el cliente se conecta al
	//repetidor (y el repetidor se encarga de reenviar la solicitud al servidor).

	//Envía al repetidor un identificador de mensaje (el mensaje que se busca), queda en espera por la respuesta del
	//repetidor y al recibirla despliega el mensaje en pantalla.

	private static String SERVER = "127.0.0.1";
	private static int SERVER_PORT = 3000;
	private static int CLIENT_PORT = 4000;
	private static int REP_PORT = 5000;
	private int id;
	private int tipoCifrado;

	public Cliente(int id, int tipoCifrado){
		this.id = id;
		this.tipoCifrado = tipoCifrado;
	}


	public void run() {

		try {
			if(tipoCifrado == 1) {
				ServerSocket cliente = new ServerSocket(CLIENT_PORT+id);
				Repetidor rp = new Repetidor(id, tipoCifrado);
				rp.start();
				Random rm = new Random();	

				//Elige y cifra el mensaje
				String mensajeCliente = "0"+rm.nextInt(9);
				File f = new File("llavesSimetricas/K_C"+id+"R"+id);
				FileInputStream fis = new FileInputStream(f);
				Key key;
				ObjectInputStream oin = new ObjectInputStream(fis);
				key = (Key) oin.readObject();
				oin.close();
				SecretKey secret = (SecretKey) key;
				Simetrico sm = new Simetrico();
				byte[] mensajeCifrado = sm.cifrar(secret, mensajeCliente);
				String strMensajecifrado = sm.byte2str(mensajeCifrado);
				

				//Envía
				Socket sk = new Socket(SERVER, REP_PORT+id);
				DataOutputStream flujo= new DataOutputStream(sk.getOutputStream());
				flujo.writeUTF(strMensajecifrado);
				flujo.close();
				sk.close();
				System.out.println("Cliente "+id+" envía: "+mensajeCliente);


				//Recibe
				Socket misocket = cliente.accept();
				DataInputStream dis = new DataInputStream(misocket.getInputStream());
				String obj =  dis.readUTF();
				System.out.println("Llega al cliente "+id+": "+obj);
				misocket.close();
				dis.close();
				
				
				
				//Descifra mensaje desde repetidor
				f = new File("llavesSimetricas/K_C"+id+"R"+id);
				fis = new FileInputStream(f);
				oin = new ObjectInputStream(fis);
				key = (Key) oin.readObject();
				fis.close();
				oin.close();
				secret = (SecretKey) key;
				sm = new Simetrico();
				byte[] mensajeEnBytes = sm.str2byte(obj);
				byte[] mensajeDescifrado = sm.descifrar(secret, mensajeEnBytes);
				String str = new String(mensajeDescifrado);
				System.out.println("Cliente "+id+" descifra: "+str);
			}
			else if(tipoCifrado == 2) {
				ServerSocket cliente = new ServerSocket(CLIENT_PORT+id);
				Repetidor rp = new Repetidor(id, tipoCifrado);
				rp.start();
				Random rm = new Random();	

				//Elige y cifra el mensaje
				String mensajeCliente = "0"+rm.nextInt(9);
				File f = new File("llavesAsimetricas/K_R"+id+"+");
				FileInputStream fis = new FileInputStream(f);
				Key key;
				ObjectInputStream oin = new ObjectInputStream(fis);
				key = (Key) oin.readObject();
				oin.close();
				PublicKey pk = (PublicKey) key;
				Asimetrico asm = new Asimetrico();
				byte[] mensajeCifrado = asm.cifrarConPublica(mensajeCliente, pk);
				String strMensajecifrado = asm.byte2str(mensajeCifrado);
				

				//Envía
				Socket sk = new Socket(SERVER, REP_PORT+id);
				DataOutputStream flujo= new DataOutputStream(sk.getOutputStream());
				flujo.writeUTF(strMensajecifrado);
				flujo.close();
				sk.close();
				System.out.println("Cliente "+id+" envía: "+mensajeCliente);


				//Recibe
				Socket misocket = cliente.accept();
				DataInputStream dis = new DataInputStream(misocket.getInputStream());
				String obj =  dis.readUTF();
				System.out.println("Llega al cliente "+id+": "+obj);
				misocket.close();
				dis.close();
				
				
				
				//Descifra mensaje desde repetidor
				f = new File("llavesAsimetricas/K_C"+id+"-");
				fis = new FileInputStream(f);
				oin = new ObjectInputStream(fis);
				key = (Key) oin.readObject();
				fis.close();
				oin.close();
				PrivateKey prk= (PrivateKey) key;
				asm = new Asimetrico();
				byte[] mensajeEnBytes = asm.str2byte(obj);
				byte[] mensajeDescifrado = asm.descifrarConPrivada(mensajeEnBytes, prk);
				String str = new String(mensajeDescifrado);
				System.out.println("Cliente "+id+" descifra: "+str);
			}
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static void main(String[] args) throws UnknownHostException, IOException {

	}
}
