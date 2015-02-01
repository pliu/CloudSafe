package com.cloudsafe.client;

import com.cloudsafe.shared.Logger;

/**
 * This class delegates byte generation to individual byte generators. Byte generation is through a
 * static method so instantiation is not required.
 */
public final class ByteGenerator {

	/**
	 * The supported byte generators. These are used as arguments for getBytes.
	 */
	public static final String PBKDF2 = "PBKDF2";
	public static final String RANDOM = "Random";

	/**
	 * getBytes uses the specified generator to generate and return a byte[]. Enforcement of
	 * argument validity (except alg) is performed by the individual generators.
	 *
	 * @param alg The generator used to generate bytes. Must be one of the constants in this class.
	 * @param length The number of bits to be generated.
	 * @param passphrase The passphrase in the case of password-derived generation.
	 * @param salt The salt in the case of password-derived generation.
	 * @param iterations The number of iterations in the case of a slow hash.
	 * @return Returns null if alg is null or not supported. Can also return null if the delegated
	 *         generator returns null.
	 */
	public static final byte[] getBytes (String alg, int length, String passphrase, byte[] salt,
			int iterations) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return null;
		}
		
		switch (alg) {
		case PBKDF2:
			byte[] bytes = PBKDF2KeyGen.getPBKDF2Key (passphrase, salt, length, iterations);
			if (bytes == null) {
				return null;
			}
			return bytes;
		case RANDOM:
			return RandomByteGen.getRandomBytes (length);
		default:
			Logger.log (alg + " is not a valid key derivation algorithm.");
			return null;
		}
	}

}
