package com.cloudsafe.client;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PBKDF2Key extends Key {
	
	private static final int MIN_SALT_LENGTH = 128; // in bits
	private static final int MIN_ITERATIONS = (int) Math.pow (2,17);
	
	private final int iterations;
	
	private PBKDF2Key (String passphrase, byte[] salt, int keyLength, int iterations) {
		this.iterations = iterations;
		setKey (generateKey (passphrase, salt, keyLength));
	}
	
	public static final PBKDF2Key getPBKDF2Key (String passphrase, byte[] salt, int keyLength, int iterations) {
		if (passphrase == null || passphrase.length() < 1) {
			Logger.log ("PBKDF2Key.getPBKDF2Key: Passphrase was either null or empty.");
			return null;
		}
		if (salt == null) {
			Logger.log ("PBKDF2Key.getPBKDF2Key: Salt was null.");
			return null;
		}
		if (salt.length < MIN_SALT_LENGTH/8)  {
			Logger.log ("PBKDF2Key.getPBKDF2Key: Salt length was " + salt.length*8 +
					" (salt length must be greater than or equal to " + MIN_SALT_LENGTH + ").");
			return null;
		}
		if (iterations < MIN_ITERATIONS) {
			Logger.log ("PBKDF2Key.getPBKDF2Key: Iterations input was " + iterations +
					" (iterations must be greater than or equal to " + MIN_ITERATIONS + ").");
			return null;
		}
		return new PBKDF2Key (passphrase, salt, keyLength, iterations);
		
	}
	
	protected final byte[] generateKey (String passphrase, byte[] salt, int keyLength) {
		try {
			PBEKeySpec spec = new PBEKeySpec (passphrase.toCharArray(), salt, iterations, keyLength);
			SecretKeyFactory skf = SecretKeyFactory.getInstance ("PBKDF2WithHmacSHA1");
			return skf.generateSecret (spec).getEncoded();
		}
		catch (Exception e) {
			Logger.log("PBKDF2Generator.generateKey: " + e);
			return null;
		}
	}
	
	public static void main (String[] args) {
		//Testing the time required to derive a key given some # of iterations
		String passphrase = "This is a test.";
		byte[] salt = "1234567812345678".getBytes();
		
		int iterations = (int) Math.pow (2,17);
		
		Key key = getPBKDF2Key(passphrase, salt, 256, iterations);
		System.out.println(key.getKey().length);
	}
}
