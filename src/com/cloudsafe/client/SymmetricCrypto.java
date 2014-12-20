package com.cloudsafe.client;

import javax.crypto.Cipher;

public abstract class SymmetricCrypto {
	
	private static int KEY_LENGTH = 256; // in bits
	public static final byte[] TAMPERED = new byte [0];
	public static final String GCM = "GCM";
	
	protected final ImmutableBytes key;
	
	public static final SymmetricCrypto getInstance (String alg, ImmutableBytes key) {
		if (alg == null) {
			Logger.log ("SymmetricCrypto.getInstance: Algorithm was null.");
			return null;
		}
		if (key == null) {
			Logger.log ("SymmetricCrypto.getInstance: Key was null.");
			return null;
		}
		if (key.getBytes().length != KEY_LENGTH/8) {
			Logger.log ("SymmetricCrypto.getInstance: Key length was " + key.getBytes().length*8 + "bits"
					+ " (key length must be " + KEY_LENGTH + ").");
			return null;
		}
		
		switch (alg) {
		case GCM:
			return GCMCrypto.getInstance (key);
		default:
			Logger.log("SymmetricCrypto.getInstance: " + alg + " is not a valid symmetric encryption" +
					" algorithm.");
			return null;
		}
	}
	
	protected SymmetricCrypto (ImmutableBytes key) {
		this.key = key;
	}

	public final byte[] encrypt (byte[] plaintext, byte[] IV) {
		return endecrypt (Cipher.ENCRYPT_MODE, plaintext, IV);
	}
	
	public final byte[] decrypt (byte[] ciphertext, byte[] IV) {
		return endecrypt (Cipher.DECRYPT_MODE, ciphertext, IV);
	}
	
	protected abstract byte[] endecrypt (int mode, byte[] target, byte[] IV);
	
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
