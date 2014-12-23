package com.cloudsafe.client;

import com.cloudsafe.shared.Logger;

public final class ByteGenerator {
	
	public static final String PBKDF2 = "PBKDF2";
	public static final String RANDOM = "Random";
	
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
