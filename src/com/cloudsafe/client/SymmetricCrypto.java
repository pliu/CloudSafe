package com.cloudsafe.client;

import javax.crypto.Cipher;

public abstract class SymmetricCrypto {
	
	public static final byte[] TAMPERED = new byte [1];

	public final byte[] encrypt (byte[] key, byte[] plaintext, byte[] IV) {
		return endecrypt (Cipher.ENCRYPT_MODE, key, plaintext, IV);
	}
	
	public final byte[] decrypt (byte[] key, byte[] ciphertext, byte[] IV) {
		return endecrypt (Cipher.DECRYPT_MODE, key, ciphertext, IV);
	}
	
	protected abstract byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV);
	
	public static void main (String[] args) throws Exception {
		//Testing whether == for byte[] tests addresses or values
		byte[] a = new byte[1];
		byte[] b = new byte[1];
		byte[] c = a;
		System.arraycopy(a, 0, b, 0, 1);
		System.out.println (a == b);
		System.out.println (a == c);
	}
}
