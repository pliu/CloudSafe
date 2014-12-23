package com.cloudsafe.client;

import java.security.SecureRandom;

import com.cloudsafe.shared.Logger;

public final class RandomByteGen {
	
	public static final byte[] getRandomBytes (int keyLength) {
		if (keyLength < 1) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be at least 1).");
			return null;
		}
		if (keyLength % 8 != 0) {
			Logger.log ("Key length was " + keyLength + " bits (key length must be a multiple of 8).");
			return null;
		}
		
		return generateBytes (keyLength);
	}
	
	private static final byte[] generateBytes (int keyLength) {
		SecureRandom rand = new SecureRandom();
		byte[] randBytes = new byte [keyLength/8];
		rand.nextBytes (randBytes);
		return randBytes;
	}
}
