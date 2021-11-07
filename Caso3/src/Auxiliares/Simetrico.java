package Auxiliares;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Simetrico {

	private final static String PADDING = "AES/ECB/PKCS5Padding";

	public final byte[] cifrar(SecretKey llave, String texto ) {
		byte[] textoCifrado;

		try {
			Cipher cifrador = Cipher.getInstance(PADDING);
			byte[] textoClaro = texto.getBytes();

			cifrador.init(Cipher.ENCRYPT_MODE, llave);
			textoCifrado = cifrador.doFinal(textoClaro);
		}catch(Exception e) {
			System.out.println("Excepción: "+ e.getMessage());
			return null;
		}
		return textoCifrado;
	}

	public final byte[] descifrar(SecretKey llave, byte[] texto ) {
		byte[] textoClaro;

		try {
			Cipher cifrador = Cipher.getInstance(PADDING);
			cifrador.init(Cipher.DECRYPT_MODE, llave);
			textoClaro = cifrador.doFinal(texto);
		}catch(Exception e) {
			System.out.println("Excepción: "+ e.getMessage());
			return null;
		}
		return textoClaro;
	}


	public String byte2str( byte[] b )
	{
		// Encapsulamiento con hexadecimales
		String ret = "";
		for (int i = 0 ; i < b.length ; i++) {
			String g = Integer.toHexString(((char)b[i])&0x00ff);
			ret += (g.length()==1?"0":"") + g;
		}
		return ret;
	}

	public byte[] str2byte( String ss)
	{
		// Encapsulamiento con hexadecimales
		byte[] ret = new byte[ss.length()/2];
		for (int i = 0 ; i < ret.length ; i++) {
			ret[i] = (byte) Integer.parseInt(ss.substring(i*2,(i+1)*2), 16);
		}
		return ret;
	}

}
