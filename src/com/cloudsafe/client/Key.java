package com.cloudsafe.client;


public final class Key {
	
	private static final int MIN_KEY_LENGTH = 128; // in bits
	public static final int PBKDF2 = 0;
	public static final int RANDOM = 1;
	
	private final byte[] key;
	
	public static final Key getKey (int alg, String passphrase, byte[] salt, int keyLength, int iterations) {
		if (keyLength < MIN_KEY_LENGTH) {
			Logger.log ("Key.getKey: Key length was " + keyLength + " (salt length must be greater" +
					" than or equal to " + MIN_KEY_LENGTH + ").");
			return null;
		}
		
		switch (alg) {
		case PBKDF2:
			return PBKDF2KeyGen.getPBKDF2Key (passphrase, salt, keyLength, iterations);
		case RANDOM:
			return RandomKeyGen.getRandomKey (keyLength);
		default:
			Logger.log ("Key.getKey: " + alg + " is not a valid key derivation algorithm.");
			return null;
		}
	}
	
	public Key (byte[] tKey) {
		key = new byte [tKey.length];
		System.arraycopy(key, 0, tKey, 0, tKey.length);
	}
	
	public final byte[] getKey () {
		byte[] tKey = new byte [key.length];
		System.arraycopy(key, 0, tKey, 0, key.length);
		return tKey;
	}
}
