import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

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
	
	public Cliente(int id){
		this.id = id;
	}
	
	
	public void run() {
		
		try {
			Repetidor rp = new Repetidor(id);
			rp.start();
			Random rm = new Random();	
			
			
			//Envía
			Socket sk = new Socket(SERVER, REP_PORT+id);
			DataOutputStream flujo= new DataOutputStream(sk.getOutputStream());
			String mensajeCliente = "0"+rm.nextInt(9);
			
			Simetrico sm = new Simetrico();
			
//			File llaveSecreta = new File("K_C"+id+"R"+id);
//			BufferedReader br = new BufferedReader(new FileReader(llaveSecreta));
//			SecretKey sk = br.readLine();
//			sm.cifrar(br.readLine(), mensajeCliente);
			
			flujo.writeUTF(mensajeCliente);
			flujo.close();
			sk.close();
			System.out.println("Cliente "+id+" envía: "+mensajeCliente);
			
			
			//Recibe
			ServerSocket cliente = new ServerSocket(CLIENT_PORT+id);
			Socket misocket = cliente.accept();
			ObjectInputStream dis = new ObjectInputStream(misocket.getInputStream());
			
			File obj = (File) dis.readObject();
			System.out.println("Llega al cliente: "+id+": "+obj.getName());
			BufferedReader br = new BufferedReader(new FileReader(obj));
			System.out.println(br.readLine());
			misocket.close();
			dis.close();
			

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
	}
}
