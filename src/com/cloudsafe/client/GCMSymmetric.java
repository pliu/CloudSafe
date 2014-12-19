package com.cloudsafe.client;

import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class GCMSymmetric extends SymmetricCrypto {
	
	protected final byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV) {
		Security.addProvider (new BouncyCastleProvider());
		try {
			SecretKey fileKey = new SecretKeySpec (key, "AES");
			Cipher cipher = Cipher.getInstance ("AES/GCM/NoPadding", "BC");
			cipher.init(mode, fileKey, new IvParameterSpec (IV));
			return cipher.doFinal (target);
		}
		catch (Exception e) {
			Logger.log("GCMSymmetric.endecrypt: " + e);
			return null;
		}
	}
	
	public static void main (String[] args) throws Exception {
		String passphrase = "This is a test.";
		byte[] salt = "1234567812345678".getBytes();
		byte[] IV = new byte[16];
		byte[] key = Key.getKey("PBKDF2", passphrase, salt, 256, (int) Math.pow (2, 17)).getKey();
		SymmetricCrypto sc = new GCMSymmetric();
		byte[] ciphertext = sc.encrypt (key, passphrase.getBytes(), IV);
		ciphertext[2] = 12;
		byte[] plaintext = sc.decrypt (key, ciphertext, IV);
		System.out.println (new String(plaintext, "UTF-8"));
	}
}
