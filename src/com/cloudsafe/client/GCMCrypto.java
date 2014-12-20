package com.cloudsafe.client;

import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class GCMCrypto extends SymmetricCrypto {
	
	protected final byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV) {
		Security.addProvider (new BouncyCastleProvider());
		try {
			SecretKey fileKey = new SecretKeySpec (key, "AES");
			Cipher cipher = Cipher.getInstance ("AES/GCM/NoPadding", "BC");
			cipher.init(mode, fileKey, new IvParameterSpec (IV));
			return cipher.doFinal (target);
		}
		catch (BadPaddingException e) {
			Logger.log ("GCMCrypto.endecrypt: " + e);
			return SymmetricCrypto.TAMPERED;
		}
		catch (Exception e) {
			Logger.log ("GCMCrypto.endecrypt: " + e);
			return null;
		}
	}
	
	public static void main (String[] args) throws Exception {
		//Testing encryption and decryption in the presence and absence of tampering
		String passphrase = "This is a test.";
		byte[] salt = "1234567812345678".getBytes();
		byte[] IV = new byte[16];
		byte[] key = Key.getKey(Key.PBKDF2, passphrase, salt, 256, (int) Math.pow (2, 17)).getKey();
		
		SymmetricCrypto sc = new GCMCrypto();
		byte[] ciphertext = sc.encrypt (key, passphrase.getBytes(), IV);
		//ciphertext[1] = 12;
		byte[] plaintext = sc.decrypt (key, ciphertext, IV);
		
		System.out.println (new String(plaintext, "UTF-8"));
	}
}
