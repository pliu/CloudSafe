package com.cloudsafe.client;

import java.security.SecureRandom;

import com.cloudsafe.utlities.Logger;

/**
 * This class generates cryptographically-secure random bytes using Java's SecureRandom class. Byte
 * generation is through a static method so instantiation is not required.
 */
public final class RandomByteGen {

	/**
	 * getRandomBytes enforces argument validity for the generation of random bytes. It then calls
	 * generateBytes to generate the bytes.
	 *
	 * @param keyLength The number of random bits to be generated. Must be a positive integer and a
	 *                  multiple of 8.
	 * @return Returns null if the above conditions are not met.
	 */
	public static final byte[] getRandomBytes (int keyLength) {
		if (keyLength < 1) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be at least 1).");
			return null;
		}
		if (keyLength % 8 != 0) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be a multiple of 8).");
			return null;
		}
		
		return generateBytes (keyLength);
	}

	private static final byte[] generateBytes (int keyLength) {
		SecureRandom rand = new SecureRandom();
		byte[] randBytes = new byte [keyLength/8];
		rand.nextBytes (randBytes);
		return randBytes;
	}
}
