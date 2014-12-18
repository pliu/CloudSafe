package com.cloudsafe.client;

public abstract class SymmetricCrypto {
	
	protected static int MODE_ENCRYPT = 0;
	protected static int MODE_DECRYPT = 1;

	public final byte[] encrypt (byte[] key, byte[] plaintext, byte[] IV) {
		return endecrypt (MODE_ENCRYPT, key, plaintext, IV);
	}
	
	public final byte[] decrypt (byte[] key, byte[] ciphertext, byte[] IV) {
		return endecrypt (MODE_DECRYPT, key, ciphertext, IV);
	}
	
	protected abstract byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV);
}
