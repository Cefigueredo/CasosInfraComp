package Auxiliares;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

	
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public GeneradorLLavesAsimetricas(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) {
		

	}
	
	public void generarLLaves(int cantidad) {
		GeneradorLLavesAsimetricas gk;
		try {
			for(int i = 0; i < cantidad; ++i) {
				gk = new GeneradorLLavesAsimetricas(1024);
				gk.createKeys();
				gk.writeToFile("KeyPair/K_C"+i+"-", gk.getPublicKey().getEncoded());
				gk.writeToFile("KeyPair/K_C"+i+"+", gk.getPrivateKey().getEncoded());
				gk.writeToFile("KeyPair/K_R"+i+"-", gk.getPrivateKey().getEncoded());
				gk.writeToFile("KeyPair/K_R"+i+"+", gk.getPrivateKey().getEncoded());
				gk.writeToFile("KeyPair/K_S"+i+"-", gk.getPrivateKey().getEncoded());
				gk.writeToFile("KeyPair/K_S"+i+"+", gk.getPrivateKey().getEncoded());
			}
			
			
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}