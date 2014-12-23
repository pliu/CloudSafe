package com.cloudsafe.client;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.cloudsafe.shared.Logger;

public final class PBKDF2KeyGen {
	
	private static final int MIN_SALT_LENGTH = 128; // in bits
	private static final int MIN_ITERATIONS = (int) Math.pow (2,17);
	
	public static final byte[] getPBKDF2Key (String passphrase, byte[] salt, int keyLength,
			int iterations) {
		if (passphrase == null) {
			Logger.log ("Passphrase was null.");
			return null;
		}
		if (salt == null) {
			Logger.log ("Salt was null.");
			return null;
		}
		if (salt.length < MIN_SALT_LENGTH/8)  {
			Logger.log ("Salt length was " + salt.length*8 + " bits (salt length must be greater than or"
					+ " equal to " + MIN_SALT_LENGTH + ").");
			return null;
		}
		if (keyLength < 1) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be at least 1).");
			return null;
		}
		if (keyLength % 8 != 0) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be a multiple of 8).");
			return null;
		}
		if (iterations < MIN_ITERATIONS) {
			Logger.log ("Iterations input was " + iterations + " (iterations must be greater than or"
					+ " equal to " + MIN_ITERATIONS + ").");
			return null;
		}
		
		return generateKey (passphrase, salt, keyLength, iterations);
		
	}
	
	private static final byte[] generateKey (String passphrase, byte[] salt, int keyLength,
			int iterations) {
		PBEKeySpec spec = new PBEKeySpec (passphrase.toCharArray(), salt, iterations, keyLength);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance ("PBKDF2WithHmacSHA1");
			return skf.generateSecret (spec).getEncoded();
		}
		catch (Exception e) {
			Logger.log (e.toString());
			return null;
		}
	}
	
	public static void main (String[] args) {
		//Testing the time required to derive a key given some # of iterations
		String passphrase = "";
		byte[] salt = "1234567812345678".getBytes();
		
		int iterations = (int) Math.pow (2,17);
		
		byte[] key = getPBKDF2Key(passphrase, salt, 256, iterations);
		System.out.println (key.length);
	}
}
