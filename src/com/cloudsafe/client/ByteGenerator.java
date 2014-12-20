package com.cloudsafe.client;

public class ByteGenerator {
	
	public static final String PBKDF2 = "PBKDF2";
	public static final String RANDOM = "Random";
	
	public static final ImmutableBytes getInstance (String alg, String passphrase, byte[] salt, int keyLength, int iterations) {
		if (alg == null) {
			Logger.log ("ByteGenerator.getInstance: Algorithm was null.");
			return null;
		}
		if (keyLength < 1) {
			Logger.log ("ByteGenerator.getInstance: Key length was " + keyLength + " bits (key length"
					+ " must be at least 1).");
			return null;
		}
		if (keyLength % 8 != 0) {
			Logger.log ("ByteGenerator.getInstance: Key length was " + keyLength + " bits (key length"
					+ " must be a multiple of 8).");
			return null;
		}
		
		switch (alg) {
		case PBKDF2:
			byte[] bytes = PBKDF2KeyGen.getPBKDF2Key (passphrase, salt, keyLength, iterations);
			if (bytes == null) {
				return null;
			}
			return ImmutableBytes.getInstance (bytes);
		case RANDOM:
			return ImmutableBytes.getInstance (RandomByteGen.getRandomKey (keyLength));
		default:
			Logger.log ("ImmutableBytes.getInstance: " + alg + " is not a valid key derivation algorithm.");
			return null;
		}
	}

}
