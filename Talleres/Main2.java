import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Main2 {


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
			SecretKey k1 = keygen.generateKey();
			SecretKey k2 = keygen.generateKey();

			byte[] tc1 = sm.cifrar(k1, line);
			System.out.println("Texto cifrado: ");
			imprimir(tc1);

			String str1 = new String(tc1);
			System.out.println("Cifrado en string es: "+ str1);
			
			byte[] tc2 = sm.cifrar(k2, line);
			System.out.println("Texto cifrado: ");
			imprimir(tc2);

			String str2 = new String(tc2);
			System.out.println("Cifrado en string es: "+ str2);
			
			byte[] td1 = sm.descifrar(k1, tc1);
			System.out.println("Texto descifrado con k1 es: ");
			imprimir(td1);
			
			String str = new String(td1);
			System.out.println("Descifrado con k1 en string es: "+ str);
			
			byte[] td2 = sm.descifrar(k2, tc1);
			System.out.println("Texto descifrado con k2 es: ");
			imprimir(td2);
			
			String str3 = new String(td2);
			System.out.println("Descifrado con k2 en string es: "+ str3);
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
