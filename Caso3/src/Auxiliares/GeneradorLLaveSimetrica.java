package Auxiliares;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Fuente: https://www.tutorialspoint.com/java_cryptography/java_cryptography_storing_keys.htm
 * @author Carlos
 *
 */
//LLave simétrica
public class GeneradorLLaveSimetrica {

	private final static String PADDING = "AES/ECB/PKCS5Padding";
	private final static String ALGORITMO = "AES";
	public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, IOException {

	}

	public void generarLLaves(int cantidad) throws NoSuchAlgorithmException, IOException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);

		for(int i = 0; i < cantidad; ++i) {
			SecretKey secretKey = keygen.generateKey();
			FileOutputStream archivo = new FileOutputStream("llavesSimetricas/K_C"+i+"R"+i);
			ObjectOutputStream oos = new ObjectOutputStream(archivo);
			oos.writeObject(secretKey);
			archivo.close();
			oos.close();

			secretKey = keygen.generateKey();
			FileOutputStream archivo2 = new FileOutputStream("llavesSimetricas/K_R"+i+"S"+i);
			ObjectOutputStream oos2 = new ObjectOutputStream(archivo2);
			oos2.writeObject(secretKey);
			archivo2.close();
			oos2.close();

		}
	}
}
