package com.cloudsafe.client;

import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.cloudsafe.utility.Logger;

public final class GCM256Crypto extends SymmetricCrypto {
	
	protected static final int KEY_LENGTH = 256; // in bits
	protected static final int BLOCK_LENGTH = 128; // in bits
	
	public static final GCM256Crypto getInstance (byte[] key) {
		if (key.length != KEY_LENGTH/8) {
			Logger.log ("Key length was " + key.length*8 + "bits (key length must be "
					+ KEY_LENGTH + ").");
			return null;
		}
		
		return new GCM256Crypto (key);
	}
	
	private GCM256Crypto (byte[] key) {
		super (key);
	}
	
	protected final byte[] endecrypt (int mode, byte[] key, byte[] target, byte[] IV) {
		if (IV.length != BLOCK_LENGTH/8) {
			Logger.log ("IV length was " + IV.length*8 + " bits (IV length must be " + BLOCK_LENGTH
					+ ").");
			return null;
		}
		
		Security.addProvider (new BouncyCastleProvider());
		
		SecretKey fileKey = new SecretKeySpec (key, "AES");
		try {
			Cipher cipher = Cipher.getInstance ("AES/GCM/NoPadding", "BC");
			cipher.init(mode, fileKey, new IvParameterSpec (IV));
			return cipher.doFinal (target);
		}
		catch (BadPaddingException e) {
			Logger.log (e.toString());
			return TAMPERED;
		}
		catch (Exception e) {
			Logger.log (e.toString());
			return null;
		}
	}
}
