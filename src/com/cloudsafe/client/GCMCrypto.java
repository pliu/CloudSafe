package com.cloudsafe.client;

import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class GCMCrypto extends SymmetricCrypto {
	
	private static final int BLOCK_LENGTH = 128; // in bits
	
	protected static final GCMCrypto getInstance (ImmutableBytes key) {
		return new GCMCrypto (key);
	}
	
	private GCMCrypto (ImmutableBytes key) {
		super (key);
	}
	
	protected final byte[] endecrypt (int mode, byte[] target, byte[] IV) {
		Security.addProvider (new BouncyCastleProvider());
		try {
			SecretKey fileKey = new SecretKeySpec (key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance ("AES/GCM/NoPadding", "BC");
			cipher.init(mode, fileKey, new IvParameterSpec (IV));
			return cipher.doFinal (target);
		}
		catch (BadPaddingException e) {
			Logger.log ("GCMCrypto.endecrypt: " + e);
			return TAMPERED;
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
		byte[] IV = "123".getBytes();
		ImmutableBytes key = ByteGenerator.getInstance(ByteGenerator.PBKDF2, passphrase, salt, 256, (int) Math.pow (2, 17));
		
		SymmetricCrypto sc = getInstance (key);
		byte[] ciphertext = sc.encrypt (passphrase.getBytes(), IV);
		ciphertext[1] = 15;
		byte[] plaintext = sc.decrypt (ciphertext, IV);
		
		System.out.println (new String(ciphertext, "UTF-8"));
		System.out.println (new String(plaintext, "UTF-8"));
		System.out.println (ciphertext.length);
		System.out.println (plaintext.length);
	}
}
