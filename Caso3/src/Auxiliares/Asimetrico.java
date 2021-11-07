package Auxiliares;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Asimetrico {
	private final static String PADDING = "AES/ECB/PKCS5Padding";
	private final static String ALGORITHM ="RSA";

	public byte[] cifrarConPublica(String plainText,PublicKey publicKey)throws Exception
	{
		Cipher cipher= Cipher.getInstance(ALGORITHM);

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(plainText.getBytes());
	}

	public byte[] descifrarConPrivada(byte[] cipherText,PrivateKey privateKey)throws Exception
	{
		Cipher cipher= Cipher.getInstance(ALGORITHM);

		cipher.init(Cipher.DECRYPT_MODE,privateKey);
		byte[] result= cipher.doFinal(cipherText);

		return result;
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
	
	public final byte[] cifrar(SecretKey llave, String texto ) {
		byte[] textoCifrado;

		try {
			Cipher cifrador = Cipher.getInstance(ALGORITHM);
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
			Cipher cifrador = Cipher.getInstance(ALGORITHM);
			cifrador.init(Cipher.DECRYPT_MODE, llave);
			textoClaro = cifrador.doFinal(texto);
		}catch(Exception e) {
			System.out.println("Excepción: "+ e.getMessage());
			return null;
		}
		return textoClaro;
	}
	
	
	
}
