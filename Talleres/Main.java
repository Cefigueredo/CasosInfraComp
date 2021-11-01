import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Main {
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
			SecretKey secretKey = keygen.generateKey();


			byte[] cifrado = sm.cifrar(secretKey, line);
			System.out.println("Texto cifrado: ");
			imprimir(cifrado);

			String str1 = new String(cifrado);
			System.out.println("Cifrado en string es: "+ str1);


			byte[] descifrado = sm.descifrar(secretKey, cifrado);
			System.out.println("Texto descifrado: ");
			imprimir(descifrado);

			String str = new String(descifrado);
			System.out.println("Descifrado en string es: "+ str);
			long tiempoFinal = System.nanoTime();

			long tiempo = tiempoFinal-tiempoInicial;
			System.out.println("Tiempo que tarda en cifrar y descifrar: "+tiempo);

			is.close();
			br.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
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
