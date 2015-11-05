package com.cloudsafe.client;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.cloudsafe.utility.Logger;

/**
 * This class uses Java SecretKeyFactory's PBKDF2WithHmacSHA1 specification to generate a password-
 * derived key. Key generation is through a static method so instantiation is not required.
 */
public final class PBKDF2KeyGen {

	/**
	 * Minimum values for some of the parameters of PBKDF2.
	 */
	public static final int MIN_SALT_LENGTH = 128; // in bits
	public static final int MIN_ITERATIONS = (int) Math.pow (2,17);

	/**
	 * getPBKDF2Key enforces argument validity for the generation of a password-derived key. It then
	 * calls generateKey to generate the key.
	 *
	 * @param passphrase The "password" from which the key is derived. Enforcement of passphrase
	 *                   strength is left to callers of this method.
	 * @param salt The salt is a second input used to add an element of randomness to the generated
	 *             key. It must be at least 128 bits and should be cryptographically-secure  random.
	 * @param keyLength The number of bits in the key. Must be a positive integer and a multiple of
	 *                  8.
	 * @param iterations Determines the degree of key hardening (higher iterations = more hardening).
	 *                   Must be at least 2^17.
	 * @return Returns null if the above conditions are not met or if passphrase or salt are null.
	 */
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
			Logger.log ("Salt length was " + salt.length*8 + " bits (salt length must be greater than"
					+ " or equal to " + MIN_SALT_LENGTH + ").");
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
