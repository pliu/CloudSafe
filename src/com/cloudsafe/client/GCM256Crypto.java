package com.cloudsafe.client;

import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class GCM256Crypto extends SymmetricCrypto {
	
	private static final int KEY_LENGTH = 256; // in bits
	private static final int BLOCK_LENGTH = 128; // in bits
	
	public static final GCM256Crypto getInstance (ImmutableBytes key) {
		if (key == null) {
			Logger.log ("Key was null.");
			return null;
		}
		if (key.getBytes().length != KEY_LENGTH/8) {
			Logger.log ("Key length was " + key.getBytes().length*8 + "bits (key length must be "
					+ KEY_LENGTH + ").");
			return null;
		}
		
		return new GCM256Crypto (key);
	}
	
	private GCM256Crypto (ImmutableBytes key) {
		super (key);
	}
	
	protected final byte[] endecrypt (int mode, ImmutableBytes key, byte[] target, byte[] IV) {
		if (IV.length != BLOCK_LENGTH/8) {
			Logger.log ("IV length was " + IV.length*8 + " bits (IV length must be " + BLOCK_LENGTH
					+ ").");
			return null;
		}
		
		Security.addProvider (new BouncyCastleProvider());
		
		SecretKey fileKey = new SecretKeySpec (key.getBytes(), "AES");
		try {
			Cipher cipher = Cipher.getInstance ("AES/GCM/NoPadding", "BC");
			cipher.init(mode, fileKey, new IvParameterSpec (IV));
			return cipher.doFinal (target);
		}
		catch (BadPaddingException e) {
			Logger.log (e.toString());
			return TAMPERED;
		}
		catch (Exception e) {
			Logger.log (e.toString());
			return null;
		}
	}
	
	public static void main (String[] args) throws Exception {
		//Testing encryption and decryption in the presence and absence of tampering
		String passphrase = "This is a test.";
		byte[] salt = "1234567812345678".getBytes();
		byte[] IV = "1234567812345678".getBytes();
		ImmutableBytes key = ByteGenerator.getInstance(ByteGenerator.PBKDF2, passphrase, salt, 256, (int) Math.pow (2, 17));
		
		SymmetricCrypto sc = getInstance (key);
		byte[] ciphertext = sc.encrypt (passphrase.getBytes(), IV);
		//ciphertext[1] = 12;
		byte[] plaintext = sc.decrypt (ciphertext, IV);
		
		System.out.println (new String(ciphertext, "UTF-8"));
		System.out.println (new String(plaintext, "UTF-8"));
		System.out.println (ciphertext.length);
		System.out.println (plaintext.length);
	}
}
