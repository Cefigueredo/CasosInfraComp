
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private boolean centinela = true;
	
	public Servidor(int id) {
		this.id = id;
	}
	
	public void run() {
		
		
		try {
			ServerSocket servidor = new ServerSocket(SERVER_PORT+id);
			while(centinela) {
				
				//Recibe desde el repetidor
				Socket misocket = servidor.accept();
				DataInputStream dis = new DataInputStream(misocket.getInputStream());
				String mensajetexto = dis.readUTF();
				System.out.println("Llega al servidor "+id+": "+mensajetexto);
				dis.close();
				misocket.close();
				
				//Envía al repetidor
				Socket enviarRepetidor= new Socket(SERVER, REP_PORT+id);
				ObjectOutputStream oos = new ObjectOutputStream(enviarRepetidor.getOutputStream());
				File myObj = new File("mensajes/"+mensajetexto+".txt");
				oos.writeObject(myObj);
				enviarRepetidor.close();
				System.out.println("Servidor "+id+" envía: "+mensajetexto);
				misocket.close();
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
