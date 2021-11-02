import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Fuente: https://www.tutorialspoint.com/java_cryptography/java_cryptography_storing_keys.htm
 * @author Carlos
 *
 */
//LLave simétrica
public class CreadorDeLlaves {


	private final static String PADDING = "AES/ECB/PKCS5Padding";
	private final static String ALGORITMO = "AES";
	public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, IOException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
		SecretKey secretKey = keygen.generateKey();
		System.out.println("llave creada: "+secretKey.toString());
		FileOutputStream archivo = new FileOutputStream("llaveSimetrica");
		ObjectOutputStream oos = new ObjectOutputStream(archivo);
		
		oos.writeObject(secretKey);
		oos.close();
		System.out.println("Llave guardada");
	}
	
}
