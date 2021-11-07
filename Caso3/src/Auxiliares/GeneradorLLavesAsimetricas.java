package Auxiliares;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
/**
 * Fuente: https://mkyong.com/java/java-asymmetric-cryptography-example/
 * @author Carlos
 *
 */
//LLaves asimétricas
public class GeneradorLLavesAsimetricas {
	private final static String ALGORITHM ="RSA";

	
	public void generarLLaves(int cantidad) {
		try {
			for(int i = 0; i < cantidad; ++i) {
				
				KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
				generator.initialize(1024);
				KeyPair keyPair = generator.generateKeyPair();
				PublicKey llavePublicaCliente = keyPair.getPublic();
				PrivateKey llavePrivadaCliente = keyPair.getPrivate();
				keyPair = generator.generateKeyPair();
				PublicKey llavePublicaRepetidor= keyPair.getPublic();
				PrivateKey llavePrivadaRepetidor= keyPair.getPrivate();
				keyPair = generator.generateKeyPair();
				PublicKey llavePublicaServidor= keyPair.getPublic();
				PrivateKey llavePrivadaServidor= keyPair.getPrivate();
				
				File file = new File("llavesAsimetricas/K_C"+i+"-");
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePrivadaCliente);
				oos.close();
				
				file = new File("llavesAsimetricas/K_C"+i+"+");
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePublicaCliente);
				oos.close();
				
				file = new File("llavesAsimetricas/K_R"+i+"-");
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePrivadaRepetidor);
				oos.close();
				
				file = new File("llavesAsimetricas/K_R"+i+"+");
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePublicaRepetidor);
				oos.close();
				
				file = new File("llavesAsimetricas/K_S"+i+"-");
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePrivadaServidor);
				oos.close();
				
				file = new File("llavesAsimetricas/K_S"+i+"+");
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(llavePublicaServidor);
				oos.close();
				
			}
			
			
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}