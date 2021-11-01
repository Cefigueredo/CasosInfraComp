import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Main3 {
	private final static String ALGORITMO = "AES";
	static Simetrico sm = new Simetrico();

	public static void main(String args[]) {
		try {
			System.out.println("Ingrese mensaje a encriptar: ");
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
			String line = br.readLine();
			long tiempoInicial = System.nanoTime();
			System.out.println("Ingresó: "+line);

			byte[] textoClaro = line.getBytes();
			System.out.println("En byte[] es: ");
			imprimir(textoClaro);


			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
			SecretKey llave = keygen.generateKey();

			FileOutputStream archivo = new FileOutputStream("llave");
			ObjectOutputStream oos = new ObjectOutputStream(archivo);
			
			oos.writeObject(llave);
			oos.close();
			
			byte[] tc1 = sm.cifrar(llave, line);
			System.out.println("Texto cifrado: ");
			imprimir(tc1);

			String str1 = new String(tc1);
			System.out.println("Cifrado en string es: "+ str1);
			
			archivo = new FileOutputStream("textoCifrado");
			oos = new ObjectOutputStream(archivo);
			oos.writeObject(tc1);
			oos.close();
			
			long tiempoFinal = System.nanoTime();
			
			long tiempo = tiempoFinal-tiempoInicial;
			System.out.println("Tiempo que tarda en cifrar y descifrar: "+tiempo);
			
			
			is.close();
			br.close();
		}catch(Exception e) {

		}
	}

	public static void imprimir(byte[] contenido) {
		int i = 0;
		for(;i < contenido.length-1; i++) {
			System.out.print(contenido[i] + " ");
		}
		System.out.println(contenido[i] + " ");
	}

}
