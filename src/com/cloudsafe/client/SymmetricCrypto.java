/*
 * Abstract class for performing symmetric cryptography.
 * This subclasses implement the functions
 */

package com.cloudsafe.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;

import com.cloudsafe.shared.ImmutableBytes;
import com.cloudsafe.shared.Logger;

public abstract class SymmetricCrypto {
	
	public static final byte[] TAMPERED = new byte [0];
	public static final String GCM256 = "GCM256";
	
	private final ImmutableBytes key;
	
	public static final SymmetricCrypto getInstance (String alg, byte[] key) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return null;
		}
		if (key == null) {
			Logger.log ("Key was null.");
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
	
	protected SymmetricCrypto (byte[] key) {
		this.key = ImmutableBytes.getInstance (key);
	}
	
	public static final int getKeyLength (String alg) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return 0;
		}
		
		switch (alg) {
		case GCM256:
			return GCM256Crypto.KEY_LENGTH;
		default:
			Logger.log (alg + " is not a valid symmetric encryption algorithm.");
			return 0;
		}
	}
	
	public static final int getBlockLength (String alg) {
		if (alg == null) {
			Logger.log ("Algorithm was null.");
			return 0;
		}
		
		switch (alg) {
		case GCM256:
			return GCM256Crypto.BLOCK_LENGTH;
		default:
			Logger.log (alg + " is not a valid symmetric encryption algorithm.");
			return 0;
		}
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
		
		return endecrypt (Cipher.ENCRYPT_MODE, key.getBytes(), plaintext, IV);
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
		
		return endecrypt (Cipher.DECRYPT_MODE, key.getBytes(), ciphertext, IV);
	}
	
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
		if (data == null) {
			Logger.log ("Data was null.");
			return null;
		}
		
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
	
	protected abstract byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV);
}
