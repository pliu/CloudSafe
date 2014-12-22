package com.cloudsafe.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;

public abstract class SymmetricCrypto {
	
	public static final byte[] TAMPERED = new byte [0];
	public static final String GCM256 = "GCM256";
	
	private final ImmutableBytes key;
	
	public static final SymmetricCrypto getInstance (String alg, ImmutableBytes key) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return null;
		}
		
		switch (alg) {
		case GCM256:
			return GCM256Crypto.getInstance (key);
		default:
			Logger.log (alg + " is not a valid symmetric encryption algorithm.");
			return null;
		}
	}
	
	protected SymmetricCrypto (ImmutableBytes key) {
		this.key = key;
	}

	public final byte[] encrypt (byte[] plaintext, byte[] IV) {
		if (plaintext == null) {
			Logger.log ("Plaintext was null.");
			return null;
		}
		if (IV == null) {
			Logger.log ("IV was null.");
			return null;
		}
		
		return endecrypt (Cipher.ENCRYPT_MODE, key, plaintext, IV);
	}
	
	public final byte[] encrypt (Object obj, byte[] IV) {
		if (obj == null) {
			Logger.log ("Object was null.");
			return null;
		}
		
		return encrypt (serialize (obj), IV);
	}
	
	public final byte[] decrypt (byte[] ciphertext, byte[] IV) {
		if (ciphertext == null) {
			Logger.log ("Ciphertext was null.");
			return null;
		}
		if (IV == null) {
			Logger.log ("IV was null.");
			return null;
		}
		
		return endecrypt (Cipher.DECRYPT_MODE, key, ciphertext, IV);
	}
	
	protected abstract byte[] endecrypt (int mode, ImmutableBytes key, byte[] target, byte[] IV);
	
	private final byte[] serialize (Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		}
		catch (IOException e) {
			Logger.log (e.toString());
			return null;
		}
	}
	
	public static final Object deserialize (byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		}
		catch (Exception e) {
			Logger.log (e.toString());
			return null;
		}
	}
	
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
