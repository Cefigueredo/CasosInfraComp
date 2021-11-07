import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
	
	
	public Repetidor(int id) {
		this.id = id;
	}
	
	public void run() {
		try {
			Servidor sv = new Servidor(id);
			sv.start();
			
			ServerSocket repetidor = new ServerSocket(REP_PORT+id);
			while(centinela) {
				//Recibe desde el cliente
				Socket misocket = repetidor.accept();
				DataInputStream dis = new DataInputStream(misocket.getInputStream());
				String mensajetexto = dis.readUTF();
				System.out.println("Llega al repetidor "+id+": "+mensajetexto);
				
				
				
				//Env�a al servidor
				Socket enviarAServidor = new Socket(SERVER, SERVER_PORT+id);
				DataOutputStream oos = new DataOutputStream(enviarAServidor.getOutputStream());
				oos.writeUTF(mensajetexto);
				oos.close();
				enviarAServidor.close();
				System.out.println("Repetidor "+id+" env�a: "+mensajetexto);
				misocket.close();
				
				
				//Recibe desde el servidor
				Socket socketServidor = repetidor.accept();
				ObjectInputStream dis2 = new ObjectInputStream(socketServidor.getInputStream());
				File obj = (File) dis2.readObject();
				System.out.println("Llega al repetidor "+id+": "+obj.getName());
				
				
				//Env�a al repetidor
				Socket enviarCliente = new Socket(SERVER, CLIENT_PORT+id);
				ObjectOutputStream oos3 = new ObjectOutputStream(enviarCliente.getOutputStream());
				File myObj = new File("mensajes/"+mensajetexto+".txt");
				oos3.writeObject(myObj);
				enviarCliente.close();
				System.out.println("Repetidor "+id+" env�a a cliente: "+mensajetexto);
				misocket.close();
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
