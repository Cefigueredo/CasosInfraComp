
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import Auxiliares.Asimetrico;
import Auxiliares.Simetrico;

public class Servidor extends Thread{
	
	//Colección de 10 mensajes con información “sensible” y los envía cuando son
	//solicitados. El servidor recibe un identificador de mensaje (un valor entre “00” y “09”) y responde con el mensaje
	//correspondiente. Los mensajes deben tener una longitud de 30 caracteres.
	
	//El servidor implementa servidores delegados y solo se comunica con el repetidor (los clientes no se conectan
	//directamente con el servidor).
	private static String SERVER = "127.0.0.1";
	private static int SERVER_PORT = 3000;
	private static int CLIENT_PORT = 4000;
	private static int REP_PORT = 5000;
	private int id;
	private int tipoCifrado;
	private boolean centinela = true;
	
	public Servidor(int id, int tipoCifrado) {
		this.id = id;
		this.tipoCifrado = tipoCifrado;
	}
	
	public void run() {
		
		
		try {
			if(tipoCifrado == 1) {
				ServerSocket servidor = new ServerSocket(SERVER_PORT+id);
				while(centinela) {
					
					//Recibe desde el repetidor
					Socket misocket = servidor.accept();
					DataInputStream dis = new DataInputStream(misocket.getInputStream());
					String mensajetexto = dis.readUTF();
					System.out.println("Llega al servidor "+id+": "+mensajetexto);
					dis.close();
					misocket.close();
					
					//Descifra mensaje de repetidor
					File f = new File("llavesSimetricas/K_R"+id+"S"+id);
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
					String mensajeDescifradoString = new String(mensajeDescifrado);
					
					
					//Cifrar el archivo para enviar
					File obj = new File("mensajes/"+mensajeDescifradoString+".txt");
					FileReader reader = new FileReader(obj);
					BufferedReader br = new BufferedReader(reader);
					String mensajeAEnviar = br.readLine();
					
					
					f = new File("llavesSimetricas/K_R"+id+"S"+id);
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					secret = (SecretKey) key;
					sm = new Simetrico();
					byte[] servidorCifrando = sm.cifrar(secret, mensajeAEnviar);
					String servidorCifradoString = sm.byte2str(servidorCifrando);
					
					//Envía al repetidor
					Socket enviarRepetidor= new Socket(SERVER, REP_PORT+id);
					DataOutputStream oos = new DataOutputStream(enviarRepetidor.getOutputStream());
					oos.writeUTF(servidorCifradoString);
					enviarRepetidor.close();
					System.out.println("Servidor "+id+" envía: "+servidorCifradoString);
					misocket.close();
				}
			}
			else if(tipoCifrado == 2) {
				ServerSocket servidor = new ServerSocket(SERVER_PORT+id);
				while(centinela) {
					
					//Recibe desde el repetidor
					Socket misocket = servidor.accept();
					DataInputStream dis = new DataInputStream(misocket.getInputStream());
					String mensajetexto = dis.readUTF();
					System.out.println("Llega al servidor "+id+": "+mensajetexto);
					dis.close();
					misocket.close();
					
					//Descifra mensaje de repetidor
					File f = new File("llavesAsimetricas/K_S"+id+"-");
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
					String mensajeDescifradoString = new String(mensajeDescifrado);
					
					
					//Cifrar el archivo para enviar
					File obj = new File("mensajes/"+mensajeDescifradoString+".txt");
					FileReader reader = new FileReader(obj);
					BufferedReader br = new BufferedReader(reader);
					String mensajeAEnviar = br.readLine();
					
					
					f = new File("llavesAsimetricas/K_R"+id+"+");
					fis = new FileInputStream(f);
					oin = new ObjectInputStream(fis);
					key = (Key) oin.readObject();
					fis.close();
					oin.close();
					PublicKey pub= (PublicKey) key;
					byte[] servidorCifrando = asm.cifrarConPublica(mensajeAEnviar, pub);
					String servidorCifradoString = asm.byte2str(servidorCifrando);
					
					//Envía al repetidor
					Socket enviarRepetidor= new Socket(SERVER, REP_PORT+id);
					DataOutputStream oos = new DataOutputStream(enviarRepetidor.getOutputStream());
					oos.writeUTF(servidorCifradoString);
					enviarRepetidor.close();
					System.out.println("Servidor "+id+" envía: "+servidorCifradoString);
					misocket.close();
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
