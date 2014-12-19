package com.cloudsafe.client;

public abstract class Key {
	
	public static final String PBKDF2 = "PBKDF2";
	
	private byte[] key;
	
	public static final Key getKey (String alg, String passphrase, byte[] salt, int keyLength, int iterations) {
		if (alg.equals(PBKDF2)) {
			return PBKDF2Key.getPBKDF2Key(passphrase, salt, keyLength, iterations);
		}
		else {
			Logger.log ("Key.getKey: " + alg + " is not a valid key derivation algorithm.");
			return null;
		}
	}
	
	public final byte[] getKey () {
		byte[] tKey = new byte [key.length];
		System.arraycopy(key, 0, tKey, 0, key.length);
		return tKey;
	}
	
	protected final void setKey (byte[] tKey) {
		key = new byte [tKey.length];
		System.arraycopy(key, 0, tKey, 0, tKey.length);
	}
	
	protected abstract byte[] generateKey (String passphrase, byte[] salt, int keyLength);
}
