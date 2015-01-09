package com.cloudsafe.test;

import com.cloudsafe.client.ByteGenerator;
import com.cloudsafe.client.SymmetricCrypto;

public final class SymmetricCryptoTest {
	
	private static SymmetricCrypto sc;
	
	private static final void gcm256Test () throws Exception {
		String passphrase = "This is a test.";
		byte[] salt = "1234567812345678".getBytes();
		byte[] IV = "1234567812345678".getBytes();
		byte[] key = ByteGenerator.getBytes (ByteGenerator.PBKDF2, 256, passphrase, salt,
				(int) Math.pow (2, 17));
				
		sc = SymmetricCrypto.getInstance (SymmetricCrypto.GCM256, key);
		
		byte[] ciphertext = sc.encrypt (passphrase.getBytes(), IV);
		
		//ciphertext[1] = 12;
		
		byte[] plaintext = sc.decrypt (ciphertext, IV);
				
		System.out.println (new String(ciphertext, "UTF-8"));
		System.out.println (new String(plaintext, "UTF-8"));
		System.out.println (ciphertext.length);
		System.out.println (plaintext.length);
	}
	
	public static void main (String[] args) throws Exception {
		gcm256Test();
	}

}
