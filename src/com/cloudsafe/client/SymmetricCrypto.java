package com.cloudsafe.client;

import javax.crypto.Cipher;

public abstract class SymmetricCrypto {
	
	protected static final int KEY_LENGTH = 256; // specified in bits

	public final byte[] encrypt (byte[] key, byte[] plaintext, byte[] IV) {
		return endecrypt (Cipher.ENCRYPT_MODE, key, plaintext, IV);
	}
	
	public final byte[] decrypt (byte[] key, byte[] ciphertext, byte[] IV) {
		return endecrypt (Cipher.DECRYPT_MODE, key, ciphertext, IV);
	}
	
	protected abstract byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV);
}
